package td3;

public class Cos extends OperationUnaire{

	public Cos(ExpressionArithmetique membre) {
		super(membre);
	}

	public double calculer() {
		return Math.cos(this.membre.calculer());
	}

	@Override
	public ExpressionArithmetique simplifier() {
		// TODO Auto-generated method stub
		return null;
	}
}
