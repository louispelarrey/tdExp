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
		if(numerateur == denominateur) {
			return new ConstEntiere(1);
		}
		else if(denominateur == 1) {
			return new ConstEntiere(numerateur);
		}
		else{
			int pgcd = gcd(this.numerateur, this.denominateur);
			return new ConstRationnelle(this.numerateur / pgcd, this.denominateur / pgcd);
		}
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + denominateur;
		result = prime * result + numerateur;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstRationnelle other = (ConstRationnelle) obj;
		if (denominateur != other.denominateur)
			return false;
		if (numerateur != other.numerateur)
			return false;
		return true;
	}

	@Override
	public ExpressionArithmetique deriver() {
		return new ConstEntiere(0);
	}
	
	@Override
	public ExpressionArithmetique clone() {
		return new ConstRationnelle(this.numerateur, denominateur);
	}
}
