package com.mygdx.game;

import java.util.*;
import java.util.regex.Pattern;

public class FunctionMaker{

    /**
     *	op1: addition soustraction (lowest priority)
     *	op2: multiplication division (higher priority)
     *	op3: power (highest priority)
     *	sp: cos, acos, log...: treat as function whatever is on the right?
     */
    private boolean DEBUG = false;

    private String function;
    public LinkedList<String> arguments;
    private LinkedList<String> type;
    private static HashMap<String, Function> map=null;
    private static final String decimalPattern = "([0-9]*)\\.([0-9]*)";
    private static final String naturalPattern = "([0-9]*)";
    private static final String semiDecimalPattern = "([0-9]*)\\.";
    private static final double step = 0.0000001;

    private FunctionMaker[] functions;
    private int funcounter;
    private double[] temp;
    private boolean[] computed;

    private LinkedList<Integer> first;
    private LinkedList<Integer> second;
    private LinkedList<Integer> third;

    public FunctionMaker(){
        // make a Hashmap with all functions
        map = new HashMap<String, Function>();

        // Populate commands map
        map.put("+", new Function() {
            public double compute(double a, double b) { return a+b; }
        });
        map.put("-", new Function() {
            public double compute(double a, double b) { return a-b; }
        });
        map.put("/", new Function() {
            public double compute(double a, double b) { return a/b; }
        });
        map.put("*", new Function() {
            public double compute(double a, double b) { return a*b; }
        });
        map.put("^", new Function() {
            public double compute(double a, double b) { return Math.pow(a,b); }
        });

        map.put("sin", new Function() {
            public double compute(double a, double b) { return Math.sin(a); }
        });
        map.put("cos", new Function() {
            public double compute(double a, double b) { return Math.cos(a); }
        });
        map.put("tan", new Function() {
            public double compute(double a, double b) { return Math.tan(a); }
        });
        map.put("asin", new Function() {
            public double compute(double a, double b) { return Math.asin(a); }
        });
        map.put("acos", new Function() {
            public double compute(double a, double b) { return Math.acos(a); }
        });
        map.put("atan", new Function() {
            public double compute(double a, double b) { return Math.atan(a); }
        });
        map.put("log", new Function() {
            public double compute(double a, double b) { return Math.log(a); }
        });
        map.put("sqrt", new Function(){
            public double compute(double a, double b) { return Math.sqrt(a); }
        });
    }
    public FunctionMaker(String function){
        if(map == null) new FunctionMaker();
        this.function = function.toLowerCase().replace(" ","");
        if(DEBUG) System.out.println(this.function);
        transfer();
        temp = new double[arguments.size()];
        computed = new boolean[arguments.size()];
        int c=0;
        for(int i=0;i<type.size();i++) if(type.get(i).equals("FUN")) c++;
        functions = new FunctionMaker[c];
        c=0;
        for(int i=0;i<type.size();i++){
            if(type.get(i).equals("FUN")) functions[c++] = new FunctionMaker(arguments.get(i).substring(1, arguments.get(i).length()-1));
        }
    }

    public void transfer(){
        arguments = new LinkedList<>();
        type = new LinkedList<>();
        int counter = 0;
        int end = counter+1;
        while(counter < this.function.length()){
            //IF NUMBER
            if(Pattern.matches(naturalPattern,this.function.substring(counter, counter+1))){
                end=counter+1;
                while(Pattern.matches(naturalPattern,this.function.substring(counter, end))||
                        Pattern.matches(decimalPattern,this.function.substring(counter, end))||
                        Pattern.matches(semiDecimalPattern,this.function.substring(counter, end)))
                    end++;
                if(DEBUG) System.out.println("counter: "+counter+", end: "+end+" => "+ this.function.substring(counter, end));
                arguments.add(this.function.substring(counter, end-1));
                type.add("NUM");
                if(DEBUG) System.out.println("end-1:" + (end-1) + " counter ++ : "+ (counter+1));
                counter = Math.max(end-1, counter+1);
                //IF OPERATION
            }else if(Pattern.matches("[*/+-^]", this.function.substring(counter, counter+1))){
                end = counter+1;
                arguments.add(this.function.substring(counter, counter+1));
                if(Pattern.matches("[+-]", this.function.substring(counter, counter+1))){
                    if(this.function.substring(counter, end).equals("+")){
                        type.add("OP1");
                    }else{
                        if(counter-1<0 || this.function.charAt(counter-1)=='('){
                            arguments.removeLast();
                            arguments.add("(-1)");
                            type.add("MIN");
                            counter = end-1;
                        }else{
                            type.add("OP1");
                        }
                    }
                }
                else if(Pattern.matches("[*/]", this.function.substring(counter, counter+1))) type.add("OP2");
                else type.add("OP3");
                counter++;
                // IF SPECIAL OPERATION
            }else if(Pattern.matches("[asclt]", this.function.substring(counter, counter+1))){
                end = counter+1;
                while(Pattern.matches("[a-w]*", this.function.substring(counter, end)))end++;
                if(DEBUG) System.out.println("counter: "+counter+", end: "+end+" => "+ this.function.substring(counter, end));
                arguments.add(this.function.substring(counter, end-1));
                type.add("SP");
                counter = end-1;

                // verify the next thing is a function
            }else if(this.function.charAt(counter) =='('){
                int n =0;
                end = counter+1;
                while(this.function.charAt(end)!=')' || n!=0){
                    if(this.function.charAt(end) == '(') n ++;
                    else if(n>0 && function.charAt(end) == ')') n--;
                    end++;
                }
                if(DEBUG) System.out.println("counter: "+counter+", end: "+end+" => "+ this.function.substring(counter, end));
                arguments.add(this.function.substring(counter, end+1));
                type.add("FUN");

                counter = end+1;
                //IF VARIABLE
            }else if(this.function.charAt(counter)=='x'||this.function.charAt(counter)=='y'){
                if(type.size()>0 && ( type.get(type.size()-1).equals("VAR") ||
                        type.get(type.size()-1).equals("FUN") || type.get(type.size()-1).equals("NUM")) ){
                    arguments.add("*");
                    type.add("OP2");
                }
                arguments.add(this.function.substring(counter, counter+1));
                type.add("VAR");
                if(DEBUG) System.out.println("VAR");
                counter++;
            }else{
                //System.out.println("WARNING ! There was a problem reading the formula");
            }
            if(DEBUG&&counter<this.function.length()) System.out.print(function.charAt(counter));
        }
    }

    public double get_height(double x, double y){
        //new calc -> no functions was used yet and nothing is computed
        funcounter=0;
        boolean[] comput = new boolean[arguments.size()];
        //find first argument
        double temp = helper(comput,0, x,y);
        int index = next(comput);
        //while there is something left to compute
        while(index!=-1){
            temp = helper(temp, comput, index,x,y);
            index = next(comput);
        }
        return temp;

    }

    @Override
    public Vector2d gradient(Vector2d p) {
        return new Vector2d((evaluate(p.getX()+step, p.getY()) - evaluate(p.getX()-step, p.getY()))/(2*step), (evaluate(p.getX(), p.getY()+step) - evaluate(p.getX(), p.getY()-step))/(2*step));
    }


    /**
     * Helper to compute the output
     * @param computed boolean array to keep track of done computations
     * @param index to keep track of where we are in the computation
     * @param x : x coordinate
     * @param y : y coordinate
     * @return first argument
     */
    public double helper(boolean[] computed, int index, double x, double y){
        double arg1=0;

        if(type.get(index).equals("VAR")){
            if(arguments.get(index).equals("x")) arg1 =x;
            else arg1=y;
            computed[index]=true;
        }else if(type.get(index).equals("NUM")){
            arg1 = Double.parseDouble(arguments.get(index-1));
            computed[index]=true;
        }else if(type.get(index).equals("SP")){
            if(type.get(index+1).equals("FUN")){

                arg1=map.get(arguments.get(index)).compute(functions[funcounter++].get_height(x,y),1.0);
                computed[index]=true;
                computed[index+1]=true;

            }//else{ we have sinx }

        }else if(type.get(index).equals("MIN")){
            computed[index]=true;
            if(type.get(index+1).equals("VAR")){
                if(arguments.get(index).equals("x")) arg1 =-x;
                else arg1=-y;
                computed[index+1]=true;
            }else if(type.get(index+1).equals("NUM")){
                arg1 = (-1)*Double.parseDouble(arguments.get(index-1));
                computed[index+1]=true;
            }else if(type.get(index+1).equals("SP")){
                if(type.get(index+2).equals("FUN")){
                    arg1=(-1)*map.get(arguments.get(index)).compute(functions[funcounter++].get_height(x,y),1.0);
                    computed[index+1]=true;
                    computed[index+2]=true;

                }//else{ we have sinx }
            }
        }
        return arg1;
    }
    public double helper(double t,boolean[] computed, int index, double x, double y){
        double arg=0;
        //if still in boundaries to see one ahead
        if(index<computed.length-1){
            //if index point to an operation -> get what's next to it and put it in arg
            if(type.get(index).startsWith("OP")) index++;
            if(type.get(index).equals("VAR")){
                if(arguments.get(index).equals("x")) arg =x;
                else arg=y;
                computed[index]=true;
            }else if(type.get(index).equals("NUM")){
                arg = Double.parseDouble(arguments.get(index));
                computed[index]=true;
            }else if(type.get(index).equals("SP")){
                if(type.get(index+1).equals("FUN")){
                    arg=map.get(arguments.get(index)).compute(functions[funcounter++].get_height(x,y),1.0);
                    computed[index]=true;
                    computed[index+1]=true;

                }//else{ we have sinx }

            }else if(type.get(index).equals("MIN")){
                computed[index]=true;
                if(type.get(index+1).equals("VAR")){
                    if(arguments.get(index).equals("x")) arg =-x;
                    else arg=-y;
                    computed[index+1]=true;
                }else if(type.get(index+1).equals("NUM")){
                    arg = (-1)*Double.parseDouble(arguments.get(index-1));
                    computed[index+1]=true;
                }else if(type.get(index+1).equals("SP")){
                    if(type.get(index+2).equals("FUN")){
                        arg=(-1)*map.get(arguments.get(index)).compute(functions[funcounter++].get_height(x,y),1.0);
                        computed[index+1]=true;
                        computed[index+2]=true;

                    }//else{ we have sinx }
                }
            }else if(type.get(index).equals("FUN")){
                arg = functions[funcounter++].get_height(x,y);
                computed[index]=true;
            }///////////////////////COMPARE TO NEXT NON COMPUTED OP//////////////////////////////


            if (index+1>=computed.length || priority(type.get(index-1), type.get(index+1))){
                if(!computed[index-1]) computed[index-1]=true;
                else computed[index]=true;
            }else{
                int next = nextFrom(computed, index+1);
//				System.out.println(arguments.get(index)+"comes before");
                arg = helper(arg, computed, index+1, x,y);
                //because ealier if !index -> OP : index++
                if(!computed[index-1]) computed[index-1]=true;
                else computed[index]=true;
                //next operation
                int nex = nextFrom(computed, index+1);
                while(nex!=-1&&!priority(type.get(index-1), type.get(nex))){
                    arg=helper(arg, computed, nex, x,y);
                    nex=nextFrom(computed, nex+1);
                }
            }
            /////////////////////////////////
            //System.out.println("\n"+index+" "+arguments.get(index-1)+" " +t+" "+arg);
            return map.get(arguments.get(index-1)).compute(t, arg);
        }else if(index<computed.length){
            if(type.get(index).equals("VAR")){
                if(arguments.get(index).equals("x")) arg =x;
                else arg=y;
                computed[index]=true;
            }else if(type.get(index).equals("NUM")){
                arg = Double.parseDouble(arguments.get(index-1));
                computed[index]=true;
            }
           // System.out.println(t+" "+arg+" index:"+index );
            return arg;
        }
       // System.out.println(index+" "+ t + " " + arg);
        //System.out.println("error here");
        return t;
    }

    public static void main(String[] args){
        FunctionMaker f = new FunctionMaker("sin(x)+y");
        //for(String s : f.arguments) System.out.print(s);
        //System.out.println("HERE"+f.get_height(Math.PI/2,1));
        //System.out.println("HERE"+f.get_height(1,2));
    }


    public static boolean priority(String a, String b){
        if(a.startsWith("OP") && b.startsWith("OP")) return a.charAt(2)>=b.charAt(2);
        //System.out.println("bug in priority");
       // System.out.println("op1: "+a+" / op2: "+b);
        return false;
    }
    public static int next(boolean[] compute){
        for(int i=0; i< compute.length; i++) if(!compute[i]) return i;
        return -1;
    }
    public static int nextFrom(boolean[] computed, int i){
        if(i<computed.length) {
            while(i<computed.length&&computed[i]) i++;
            if(i<computed.length) return i;
        }return -1;
    }
}


interface Function{
    public double compute(double a, double b);
}