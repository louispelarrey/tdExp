package td3;

public abstract class OperationUnaire extends ExpressionArithmetique{
	protected ExpressionArithmetique membre;
	
	public OperationUnaire(ExpressionArithmetique membre) {
		this.membre = membre;
	}
}
