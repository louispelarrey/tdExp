package td3;

import java.util.Map;

public class Sin extends OperationUnaire{

	public Sin(ExpressionArithmetique membre) {
		super(membre);
	}

	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return Math.sin(this.membre.calculer());
	}

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this;
	}
}
