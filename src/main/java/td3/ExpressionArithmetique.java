package td3;

import java.util.Collections;
import java.util.Map;

public abstract class ExpressionArithmetique{
	
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
	
	

}
