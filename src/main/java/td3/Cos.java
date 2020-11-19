package td3;

import java.util.Map;

public class Cos extends OperationUnaire{

	public Cos(ExpressionArithmetique membre) {
		super(membre);
	}
	
	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return Math.cos(this.membre.calculer(map));
	}

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return new Cos(membre.simplifier(map));
	}
	
	@Override
	public String toString() {
		return "cos(" + membre.toString() + ")";
	}
	
	@Override
	public ExpressionArithmetique deriver() {
		return new Multiplication(new Soustraction(new ConstEntiere(0), new ConstEntiere((int)this.membre.calculer()).deriver()), new Sin(this.membre));
	}
	
	@Override
	public ExpressionArithmetique clone() {
		return new Cos(membre.clone());
	}
}
