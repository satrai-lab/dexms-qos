package onoffprob;

public class AnalyticalModel {
	
	public double numMessSys (double lm, double rho, double thOn, double thOff, double z, double mu) {
		double numMess = 0;
		double[] A1Parts = new double[1];
		double[] A2Parts = new double[3];
		double[] A3Parts = new double[3];
		
		double A1 = 0;
		double A2 = 0;
		double A3 = 0;
		
		A1Parts[0] = (thOff*(1-rho)-rho*z*thOn)*(lm*z+thOff)*(thOn+thOff);

		
		A1 = rho/A1Parts[0];
		
		A2Parts[0] = z*(lm+mu+thOn)+thOff;
		A2Parts[1] = (lm*z+thOff)*(1-rho)*(1-rho+rho*z);
		A2Parts[2] = rho*z*(rho*thOn*(1-z)+thOff);
		
		A2 = thOn*A2Parts[0]*(A2Parts[1]+A2Parts[2]);
		
		A3Parts[0] = z*(lm+thOn) + thOff;
		A3Parts[1] = rho*thOn*(lm*z+thOff)*(1-rho+(rho*z));
		A3Parts[2] = (thOff-(rho*z*thOn))*((rho*thOn*(1-z))+thOff);
		
		A3 = A3Parts[0]*(A3Parts[1]+A3Parts[2]);
		
		numMess = A1*(A2+A3);
		
		return numMess;
	}
	
	public double computeEffLam (double lm, double thOn, double thOff, double z) {
		double effLambda = 0;
		
		double p_server_off = thOn / (thOn+thOff);
		effLambda = lm * (1-(p_server_off*(1-z)));
		
		
		return effLambda;
		
		
	}
	
	public double computeCubicA(double lm, double rho, double thOn) {
		double coA = 0;
		coA = (lm * Math.pow(rho, 3) * Math.pow(thOn, 2)) - (Math.pow(rho, 3) * Math.pow(thOn, 3));
		
		return coA;
	}
	
	public double computeCubicB(double lm, double rho, double thOn, double thOff, double e) {
		double coB = 0;
		double[] coBparts = new double[12];
		
		coBparts[0] = Math.pow(lm, 2) * thOn;
		coBparts[1] = 2 * Math.pow(rho, 2) * lm * thOn * thOff; 
		coBparts[2] = lm * rho * Math.pow(thOn, 2);
		coBparts[3] = 2 * lm * Math.pow(rho, 3) * Math.pow(thOn, 2);
		coBparts[4] = Math.pow(rho, 3) * Math.pow(thOn, 2) * thOff;
		coBparts[5] = Math.pow(rho, 3) * Math.pow(thOn, 3);
		coBparts[6] = lm * Math.pow(rho, 2) * Math.pow(thOn, 2);
		coBparts[7] = 2 * Math.pow(rho, 2) * Math.pow(thOn, 2) * thOff;
		coBparts[8] = Math.pow(rho, 3) * Math.pow(thOn, 3);
		
		coBparts[9] = lm * thOff * rho * thOn * e;
		coBparts[10] = lm * Math.pow(rho, 2) * Math.pow(thOn, 2) * e;
		coBparts[11] = lm * Math.pow(rho, 2) * thOn * thOff * e;

		coB = coBparts[0] - coBparts[1] + coBparts[2] - coBparts[3] + coBparts[4] - coBparts[5] - coBparts[6] - coBparts[7]
			- coBparts[8] + coBparts[9] - coBparts[10] - coBparts[11];
		
		return coB;
	}
	
	public double computeCubicC(double lm, double rho, double thOn, double thOff, double e) {
		double coC = 0;
		double[] coCparts = new double[24];
		
		coCparts[0] = lm * rho * Math.pow(thOff, 2);
		coCparts[1] = lm * thOn * thOff;
		coCparts[2] = 2 * lm * rho * thOn * thOff;
		coCparts[3] = rho * thOn * Math.pow(thOff, 2);
		coCparts[4] = 2 * Math.pow(rho, 2) * lm * thOn * thOff;
		coCparts[5] = 2 * Math.pow(rho, 2) * thOn * Math.pow(thOff, 2);
		coCparts[6] = 2 * Math.pow(rho, 3) * Math.pow(thOn, 2);
		coCparts[7] = rho * Math.pow(thOn, 2) * thOff;
		
		coCparts[8] = 2 * Math.pow(rho, 3) * Math.pow(thOn, 2) * thOff;
		coCparts[9] = lm * Math.pow(rho, 2) * Math.pow(thOn, 2);
		coCparts[10] = 2 * thOff * Math.pow(rho, 2) * Math.pow(thOn, 2);
		coCparts[11] = Math.pow(rho, 3) * Math.pow(thOn, 3);
		coCparts[12] = lm * thOn * thOff * e;
		coCparts[13] = lm * rho * Math.pow(thOn, 2) * e;
		coCparts[14] = lm * rho * thOn * thOff * e;
		
		coCparts[15] = lm * Math.pow(thOff, 2) * e;
		coCparts[16] = lm * rho * thOn * thOff * e;
		coCparts[17] = lm * rho * Math.pow(thOff, 2) * e;
		coCparts[18] = lm * thOff * rho * thOn * e;
		coCparts[19] = lm * Math.pow(rho, 2) * Math.pow(thOn, 2) * e;
		
		coCparts[20] = 2 * Math.pow(rho, 2) * thOn * thOff * e;
		coCparts[21] = rho * thOn * Math.pow(thOff, 2) * e;
		coCparts[22] = Math.pow(rho, 2) * Math.pow(thOn, 2) * thOff * e;
		coCparts[23] = Math.pow(rho, 2) * thOn * Math.pow(thOff, 2) * e;
		
		coC = coCparts[0] + coCparts[1] + coCparts[2] + coCparts[3] + coCparts[4] - coCparts[5] + coCparts[6] + coCparts[7]
			- coCparts[8] + coCparts[9] + coCparts[10] + coCparts[11] - coCparts[12] + coCparts[13] + coCparts[14]
			- coCparts[15] + coCparts[16] + coCparts[17] - coCparts[18] + coCparts[19]
			+ coCparts[20] + coCparts[21] - coCparts[22] - coCparts[23];
		
		return coC;
	}
	
	public double computeCubicD(double lm, double rho, double thOn, double thOff, double e) {
		double coD = 0;
		double[] coDparts = new double[13];
		
		coDparts[0] = rho * Math.pow(thOff, 3);
		coDparts[1] = rho * thOn * Math.pow(thOff, 2);
		coDparts[2] = 2 * Math.pow(rho, 2) * thOn * Math.pow(thOff, 2);
		coDparts[3] = Math.pow(rho, 3) * Math.pow(thOn, 2) * thOff;
		coDparts[4] = Math.pow(thOff, 2) * thOn * e;
		coDparts[5] = rho * Math.pow(thOn, 2) * thOff * e;
		coDparts[6] = rho * Math.pow(thOff, 2) * thOn * e;
		
		coDparts[7] = Math.pow(thOff, 3) * e;
		coDparts[8] = rho * thOn * Math.pow(thOff, 2) * e;
		coDparts[9] = rho * Math.pow(thOff, 3) * e;
		coDparts[10] = Math.pow(thOff, 2) * rho * thOn  * e;
		coDparts[11] = Math.pow(rho, 2) * Math.pow(thOn, 2) * thOff * e;
		coDparts[12] = Math.pow(rho, 2) * Math.pow(thOff, 2) * thOn * e;
		
		coD = coDparts[0] + coDparts[1] + coDparts[2] + coDparts[3] - coDparts[4] + coDparts[5] + coDparts[6]
				 - coDparts[7] + coDparts[8] + coDparts[9] - coDparts[10] + coDparts[11] + coDparts[12];
				
		return coD;
	}
	
	public double computeCubicFormula(double a, double b, double c, double d) {
		double x = 0;
		
		double part1 = (-(Math.pow(b, 3))/ (27 * Math.pow(a, 3))) + ((b*c)/(6 * Math.pow(a, 2))) - (d/(2*a));
		double part2 = (c/(3*a)) - (Math.pow(b, 2)/(9 * Math.pow(a, 2)));
		double part3 = (b/(3*a));
		
		x = Math.cbrt(part1 + Math.sqrt(Math.pow(part1, 2) + Math.pow(part2, 3))) 
		  + Math.cbrt(part1 - Math.sqrt(Math.pow(part1, 2) + Math.pow(part2, 3)))
		  - part3;
		
		return x;
	}
	
}
