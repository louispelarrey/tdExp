package td3;

public abstract class ExpressionArithmetique{
	public abstract ExpressionArithmetique simplifier();
	public abstract double calculer();
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpressionArithmetique other = (ExpressionArithmetique) obj;
		return !(other.calculer() == this.calculer() || other.simplifier() == this.simplifier());
	}
}
