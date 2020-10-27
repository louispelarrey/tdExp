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
	
	public abstract ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map);
	public abstract double calculer(Map<VariableSymbolique, ExpressionArithmetique> map);
	
	
	
	
}
