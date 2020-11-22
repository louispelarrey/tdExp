package td3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class ExpressionArithmetique implements Derivable{
	
	public ExpressionArithmetique simplifier() {
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); //map vide
		return simplifier(map);
	}
	
	public double calculer() {
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); //map vide
		return calculer(map);
	}
	
	public ExpressionArithmetique deriver() {
		return deriver(0);
	}
	
	public ExpressionArithmetique deriver(int n) {
		ExpressionArithmetique deriverN = this;
		for(int i=0; i<n; i++) {
			deriverN = deriverN.deriver();
		}
		return deriverN;
	}
	
	public double integrationRiemann(VariableSymbolique var, ConstEntiere precision, ConstEntiere a, ConstEntiere b) {
		Soustraction intervalle = new Soustraction(b, a);
		Division delta = new Division(intervalle, precision);
		
		double somme = 0.0;
		
		for(int i = 0; i < precision.getEntier(); i++) {
			Addition Xi = new Addition(a, new Multiplication(new ConstEntiere(i), delta));
			Addition Xiplus1 = new Addition(a, new Multiplication(new ConstEntiere(i + 1), delta));
			
			ExpressionArithmetique milieu = new Division(new Addition(Xi, Xiplus1), new ConstEntiere(2));
			double calculFonc = this.calculer(Collections.singletonMap(var, milieu));
			
			somme += calculFonc;
		}
		
		return somme * delta.calculer();
	}
	
	public abstract ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map);
	public abstract double calculer(Map<VariableSymbolique, ExpressionArithmetique> map);
	public abstract ExpressionArithmetique clone();
	
	
	

}
