package com.mygdx.game;

import java.util.Arrays;

import Model.Function2d;
import Model.Vector2d;

public class CourseShaper implements Function2d {
	
	public String[] help;
	
    public CourseShaper(String function2d){

        //TODO: okay seriously? a function that returns a function?
    	help = new String[7];
    	this.help = function2d.split(" ");
    }

    @Override
    public double evaluate(Vector2d p) {

    	float x = (float)p.getX();
    	float y = (float)p.getY();
    	
    	float holdx;
		float holdy;
		
		if (help[1].equals("+") || help[1].equals("-")) {
			if (help[1].contentEquals("+")) {
				if (help[0].equalsIgnoreCase("x")) {
					holdx = x + Float.parseFloat(help[2]);
				}
				else {
					holdx = x + Float.parseFloat(help[0]);
				}
			}
			else {
				if (help[0].equalsIgnoreCase("x")) {
					holdx = x - Float.parseFloat(help[2]);
				}
				else {
					holdx = -x + Float.parseFloat(help[0]);
				}
			}
		}
		else if (help[1].equals("*") || help[1].equals("/")) {
			if (help[1].contentEquals("*")) {
				if (help[0].equalsIgnoreCase("x")) {
					holdx = x * Float.parseFloat(help[2]);
				}
				else {
					holdx = x * Float.parseFloat(help[0]);
				}
			}
			else {
				if (help[0].equalsIgnoreCase("x")) {
					holdx = x * 1/Float.parseFloat(help[2]);
				}
				else {
					holdx = 1/x * Float.parseFloat(help[0]);
				}
			}
		}
		else if(help[1].equals("cos") || help[1].equals("sin")){
			if (help[1].contentEquals("cos")) {
				if (help[0].equalsIgnoreCase("x")) {
					holdx = (float)(x * Math.cos(Float.parseFloat(help[2])));
				}
				else {
					holdx = (float)(Math.cos(x) * Float.parseFloat(help[0]));
				}
			}
			else {
				if (help[0].equalsIgnoreCase("x")) {
					holdx = (float)(x * Math.sin(Float.parseFloat(help[2])));
				}
				else {
					holdx = (float)(Math.sin(x) * Float.parseFloat(help[0]));
				}
			}
		}
		else {
			if (help[0].equalsIgnoreCase("x")) {
				holdx = (float)Math.pow((double)x, Double.parseDouble(help[2]));
			}
			else {
				holdx = (float)Math.pow(Double.parseDouble(help[0]), (double)x);
			}
		}
		
		if (help[5].equals("+") || help[5].equals("-")) {
			if (help[5].contentEquals("+")) {
				if (help[4].equalsIgnoreCase("y")) {
					holdy = y + Float.parseFloat(help[6]);
				}
				else {
					holdy = y + Float.parseFloat(help[5]);
				}
			}
			else {
				if (help[4].equalsIgnoreCase("y")) {
					holdy = y - Float.parseFloat(help[6]);
				}
				else {
					holdy = -y + Float.parseFloat(help[4]);
				}
			}
		}
		else if (help[5].equals("*") || help[5].equals("/")) {
			if (help[5].contentEquals("*")) {
				if (help[4].equalsIgnoreCase("y")) {
					holdy = y * Float.parseFloat(help[6]);
				}
				else {
					holdy = y * Float.parseFloat(help[4]);
				}
			}
			else {
				if (help[4].equalsIgnoreCase("y")) {
					holdy = y * 1/Float.parseFloat(help[6]);
				}
				else {
					holdy = 1/y * Float.parseFloat(help[4]);
				}
			}
		}
		else if(help[5].equals("cos") || help[5].equals("sin")){
			if (help[5].contentEquals("cos")) {
				if (help[4].equalsIgnoreCase("x")) {
					holdy = (float)(y * Math.cos(Float.parseFloat(help[6])));
				}
				else {
					holdy = (float)(Math.cos(y) * Float.parseFloat(help[4]));
				}
			}
			else {
				if (help[4].equalsIgnoreCase("x")) {
					holdy = (float)(y * Math.sin(Float.parseFloat(help[6])));
				}
				else {
					holdy = (float)(Math.sin(y) * Float.parseFloat(help[4]));
				}
			}
		}
		else {
			if (help[4].equalsIgnoreCase("y")) {
				holdy = (float)Math.pow((double)y, Double.parseDouble(help[6]));

			}
			else {
				holdy = (float)Math.pow(Double.parseDouble(help[4]), (double)y);
			}
		}

		if (help[3].equals("+"))
		return holdx + holdy;
		else if (help[3].equals("-"))
		return holdx - holdy;
		else if (help[3].equals("*"))
		return holdx * holdy;
		else if (help[3].equals("/"))
		return holdx * 1.0f/holdy;
		else if (help[3].equals("^"))
		return (float)Math.pow(holdx,  holdy);	
		else if (help[3].equals("sin"))
		return holdx*(float)Math.sin(holdy);
		else
		return holdx*(float)Math.cos(holdy);
			
			
		
		//return (float)Math.sin(x) + y*y;
	}

    @Override
    public Vector2d gradient(Vector2d p) {
        //JD PHYSICS METHOD FOR DzDx and DzDy???

        return null;
    }
}
