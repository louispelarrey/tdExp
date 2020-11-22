package td3;

import java.util.Map;

public abstract class ConstSymbolique extends ExpressionArithmetique {
	private final String symbole;
	private final double value;
	
	protected ConstSymbolique(String symbole, double value) {
		this.symbole = symbole;
		this.value = value;
	}

	public String getSymbole() {
		return symbole;
	}

	public double getValue() {
		return value;
	}

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this;
	}
	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return value;
	}
	
	@Override
	public String toString() {
		return symbole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbole == null) ? 0 : symbole.hashCode());
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
		ConstSymbolique other = (ConstSymbolique) obj;
		if (symbole == null) {
			if (other.symbole != null)
				return false;
		} else if (!symbole.equals(other.symbole))
			return false;
		return true;
	}

	@Override
	public ExpressionArithmetique deriver() {
		return new ConstEntiere(0);
	}
	
}
