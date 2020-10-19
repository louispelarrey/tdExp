package td3;

public class Ln extends OperationUnaire{

	public Ln(ExpressionArithmetique membre) {
		super(membre);
	}

	public double calculer() {
		return Math.log(this.membre.calculer());
	}

	@Override
	public ExpressionArithmetique simplifier() {
		return this;
	}
}
