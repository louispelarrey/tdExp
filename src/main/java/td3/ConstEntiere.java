package td3;

import java.util.Map;

public final class ConstEntiere extends ExpressionArithmetique implements ConstReelle {
	private final int entier;
	
	public ConstEntiere(int value) {
		this.entier = value;
	}

	public int getEntier() {
		return entier;
	}

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this;
	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this.getEntier();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + entier;
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
		ConstEntiere other = (ConstEntiere) obj;
		if (entier != other.entier)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Integer.toString(entier);
	}
	
	@Override
	public ExpressionArithmetique deriver() {
		return new ConstEntiere(0);
	}
	
	@Override
	public ExpressionArithmetique clone() {
		return new ConstEntiere(this.entier);
	}
}
