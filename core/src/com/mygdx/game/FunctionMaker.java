package com.mygdx.game;

import java.util.*;
import java.util.regex.Pattern;

public class FunctionMaker{

    /**
     * 	The idea is to put the funcion in an array to iterate through
     * 	if the type is FUN -> make another functionMaker to translate
     *	op1: addition soustraction (lowest priority)
     *	op2: multiplication division (higher priority)
     *	op3: power (highest priority)
     *	op4: cos, acos, log...: treat as function whatever is on the right?
     */
    private String function;
    public LinkedList<String> arguments;
    public LinkedList<String> type;
    public static final String decimalPattern = "([0-9]*)\\.([0-9]*)";
    public static final String naturalPattern = "([0-9]*)";
    public static final String semiDecimalPattern = "([0-9]*)\\.";
    private boolean DEBUG = false;


    public FunctionMaker(String function){
        this.function = function.toLowerCase().replace(" ","");
        if(DEBUG) System.out.println(this.function);
        transfer();
    }

    public void transfer(){
        arguments = new LinkedList<>();
        type = new LinkedList<>();
        int counter = 0;
        int end = counter+1;
        while(counter < this.function.length()){
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
            }else if(Pattern.matches("[*/+-^]", this.function.substring(counter, counter+1))){
                arguments.add(this.function.substring(counter, counter+1));
                if(Pattern.matches("[+-]", this.function.substring(counter, counter+1))) type.add("OP1");
                else if(Pattern.matches("[*/]", this.function.substring(counter, counter+1))) type.add("OP2");
                else type.add("OP3");
                counter++;
            }else if(Pattern.matches("[asclt]", this.function.substring(counter, counter+1))){
                end = counter+1;
                while(Pattern.matches("[a-z]*", this.function.substring(counter, end)))end++;
                if(DEBUG) System.out.println("counter: "+counter+", end: "+end+" => "+ this.function.substring(counter, end));
                arguments.add(this.function.substring(counter, end-1));
                type.add("OP4");
                counter = end-1;
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
            }else if(this.function.charAt(counter)=='x'||this.function.charAt(counter)=='y'){
                if(type.size()>0 && ( type.get(type.size()-1).equals("VAR") ||
                        type.get(type.size()-1).equals("FUN") || type.get(type.size()-1).equals("NUM")) ){
                    arguments.add("*");
                    type.add("OP");
                }
                arguments.add(this.function.substring(counter, counter+1));
                type.add("VAR");
                if(DEBUG) System.out.println("VAR");
                counter++;
            }
            if(DEBUG) System.out.print(function.charAt(counter));
        }
    }

    public double get_height(double x, double y){
        return compute(x,y,0);
    }

    public double compute(double x, double y, int count){
        return 0.0;
    }

    public static void main(String[] args){
        FunctionMaker f = new FunctionMaker("cos(x) +log(y)^2 +x^2y");
        for(String s : f.arguments) System.out.println(s);
        for(String s : f.type) System.out.println(s);
    }
}