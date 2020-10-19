package td3;

public class Sin extends OperationUnaire{

	public Sin(ExpressionArithmetique membre) {
		super(membre);
	}

	public double calculer() {
		return Math.sin(this.membre.calculer());
	}

	@Override
	public ExpressionArithmetique simplifier() {
		// TODO Auto-generated method stub
		return null;
	}
}
