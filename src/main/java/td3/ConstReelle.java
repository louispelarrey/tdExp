package td3;

import java.util.Map;

public interface ConstReelle {
	
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map);
	
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map);
	
}
