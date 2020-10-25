package td3;

import java.util.Map;

public final class ConstRationnelle extends ExpressionArithmetique {

	private final int numerateur;
	private final int denominateur;

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
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
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
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return (double)this.numerateur / (double)this.denominateur;
	}
	
	@Override
	public String toString() {
		return Integer.toString(numerateur) + "/" + Integer.toString(denominateur);
	}

}
