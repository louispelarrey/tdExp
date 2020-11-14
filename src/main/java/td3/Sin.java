package td3;

import java.util.Map;

public class Sin extends OperationUnaire{

	public Sin(ExpressionArithmetique membre) {
		super(membre);
	}
	
	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return Math.sin(this.membre.calculer());
	}

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this.membre.equals(new Division(new Pi(), new ConstEntiere(2))) ? new ConstEntiere(1) :
				new Sin(membre.simplifier(map));
	}
	
	@Override
	public String toString() {
		return "sin(" + membre.toString() + ")";
	}
	
	@Override
	public ExpressionArithmetique deriver() {
		return new Multiplication(new ConstEntiere((int)this.membre.calculer()).deriver(), new Cos(this.membre));
	}
}
