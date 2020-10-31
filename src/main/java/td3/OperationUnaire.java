package td3;

public abstract class OperationUnaire extends ExpressionArithmetique{
	protected ExpressionArithmetique membre;
	
	public OperationUnaire(ExpressionArithmetique membre) {
		this.membre = membre;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationUnaire other = (OperationUnaire) obj;
		return (other.membre.simplifier().equals(this.membre.simplifier()));
	}
}
