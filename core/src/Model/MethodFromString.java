package Model;


public class MethodFromString {
	
	public static String[] killme = new String[7];
	
	public static void main(String[] args) {
		String hold = "x cos 6 - y ^ 1.5";
		killme = hold.split(" ");
		System.out.println(calc(killme, 1, 1));
	}
	
	public static float calc(String[] help, float x, float y) {
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
					holdx = (float)(Math.cos(x) * Float.parseFloat(help[2]));
				}
			}
			else {
				if (help[0].equalsIgnoreCase("x")) {
					holdx = (float)(x * Math.sin(Float.parseFloat(help[2])));
				}
				else {
					holdx = (float)(Math.sin(x) * Float.parseFloat(help[2]));
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
					holdy = (float)(x * Math.cos(Float.parseFloat(help[6])));
				}
				else {
					holdy = (float)(Math.cos(x) * Float.parseFloat(help[6]));
				}
			}
			else {
				if (help[4].equalsIgnoreCase("x")) {
					holdy = (float)(x * Math.sin(Float.parseFloat(help[6])));
				}
				else {
					holdy = (float)(Math.sin(x) * Float.parseFloat(help[6]));
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
		
		return holdx + holdy;
	}
	
}
