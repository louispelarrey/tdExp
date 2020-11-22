package td3;

import java.util.Map;

public interface Symbolique {
	
	public String getSymbole();
	
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map);
	
}
