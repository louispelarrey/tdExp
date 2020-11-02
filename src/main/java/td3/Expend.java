package td3;

import java.util.Map;

public abstract class Expend extends ExpressionArithmetique{

	@Override
	public abstract ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map);

	@Override
	public abstract double calculer(Map<VariableSymbolique, ExpressionArithmetique> map);
}
