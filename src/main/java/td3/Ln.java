package td3;

import java.util.Map;

public class Ln extends OperationUnaire{

	public Ln(ExpressionArithmetique membre) {
		super(membre);
	}
	
	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return Math.log(this.membre.calculer());
	}

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return new Ln(membre.simplifier(map));
	}
	
	@Override
	public String toString() {
		return "ln(" + membre.toString() + ")";
	}
	
}
