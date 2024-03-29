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
		return this.membre.equals(new ConstEntiere(4)) ? new ConstEntiere(2) :
			new RacineCarre(membre.simplifier(map));
	}
	
	@Override
	public String toString() {
		return "√" + membre.toString();
	}

	@Override
	public ExpressionArithmetique deriver() {
		return new Division(new ConstEntiere((int)this.membre.calculer()).deriver(), new Multiplication(new ConstEntiere(2),new RacineCarre(new ConstEntiere((int)this.membre.calculer()))));
	}

	@Override
	public ExpressionArithmetique clone() {
		return new RacineCarre(membre.clone());
	}
}
