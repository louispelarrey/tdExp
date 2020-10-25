package td3;

import java.util.Map;

public class RacineCarre extends OperationUnaire{

	public RacineCarre(ExpressionArithmetique membre) {
		super(membre);
	}
	
	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return Math.sqrt(this.membre.calculer());
	}

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return null;
	}
	
	@Override
	public String toString() {
		return "√" + membre.toString();
	}

}
