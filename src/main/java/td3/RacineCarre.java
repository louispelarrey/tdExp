package td3;

public class RacineCarre extends OperationUnaire{

	public RacineCarre(ExpressionArithmetique membre) {
		super(membre);
	}

	public double calculer() {
		return Math.sqrt(this.membre.calculer());
	}

	@Override
	public ExpressionArithmetique simplifier() {
		// TODO Auto-generated method stub
		return null;
	}

}
