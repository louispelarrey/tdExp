package td3;

public final class ConstRationnelle extends ExpressionArithmetique {

	private final int numerateur;
	private final int denominateur;
	
	@Override
	public String toString() {
		if(numerateur == denominateur)
			return "1";
		return numerateur + "/" + denominateur;
	}

	public ConstRationnelle(int num, int denom) {
		this.numerateur = num;
		this.denominateur = denom;
	}

	public int getNumerateur() {
		return numerateur;
	}

	public int getDenominateur() {
		return denominateur;
	}

	@Override
	public ExpressionArithmetique simplifier() {
		int pgcd = gcd(this.numerateur, this.denominateur);
		return new ConstRationnelle(this.numerateur / pgcd, this.denominateur / pgcd);
	}

	private static int gcd(int a, int b) {
		if (b == 0)
			return a;
		else
			return gcd(b, a % b);
	}

	@Override
	public double calculer() {
		return (double)this.numerateur / (double)this.denominateur;
	}

}