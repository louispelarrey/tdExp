package td3;

import java.util.Map;

public final class ConstEntiere extends ExpressionArithmetique {
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
	public String toString() {
		return Integer.toString(entier);
	}
}
