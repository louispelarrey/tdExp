package td3;

import java.util.HashMap;
import java.util.Map;

public abstract class ExpressionArithmetique{
	
	public ExpressionArithmetique simplifier() {
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); //map vide
		return simplifier(map);
	}
	
	public double calculer() {
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); //map vide
		return calculer(map);
	}
	
	public ExpressionArithmetique deriver() {
		return deriver();//TODO METTRE un int dans parenthèse ??
	}
	
	public ExpressionArithmetique deriver(int n) {
		ExpressionArithmetique deriverN = this;
		for(int i=0; i<n; i++) {
			deriverN = deriverN.deriver();
		}
		return deriverN;
	}
	
	public abstract ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map);
	public abstract double calculer(Map<VariableSymbolique, ExpressionArithmetique> map);
	public abstract ExpressionArithmetique clone();
	

}
