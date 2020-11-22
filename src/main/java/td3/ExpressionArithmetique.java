package td3;

import java.util.Collections;
import java.util.Map;

public abstract class ExpressionArithmetique {
	
	@SuppressWarnings("unchecked")
	public ExpressionArithmetique simplifier() {
		return simplifier(Collections.EMPTY_MAP);
	}
	
	@SuppressWarnings("unchecked")
	public double calculer() {
		return calculer(Collections.EMPTY_MAP);
	}
	
	@SuppressWarnings("unchecked")
	public ExpressionArithmetique deriver() {
		return deriver();
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
			
			Division milieu = new Division(new Addition(Xi, Xiplus1), new ConstEntiere(2));
			double calculFonc = this.calculer(Collections.singletonMap(var, milieu));
			
			somme += calculFonc;
		}
		
		return somme * delta.calculer();
	}
	
	public abstract ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map);
	public abstract double calculer(Map<VariableSymbolique, ExpressionArithmetique> map);

}
