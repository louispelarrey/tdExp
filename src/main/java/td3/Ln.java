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
		return this.membre.equals(new ConstEntiere(1)) ? new ConstEntiere(0) : new Ln(membre.simplifier());
	}
	
	@Override
	public String toString() {
		return "ln(" + membre.toString() + ")";
	}
	
	@Override
	public ExpressionArithmetique deriver() {
		return new Division(new ConstEntiere((int)this.membre.calculer()).deriver(), new ConstEntiere((int)this.membre.calculer()));
	}
}
