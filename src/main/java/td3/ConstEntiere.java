package td3;

import java.util.Map;

public final class ConstEntiere extends ExpressionArithmetique {

	public ConstEntiere(int value) {
		this.entier = value;
	}

	public int getEntier() {
		return entier;
	}

	private final int entier;

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this;
	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this.getEntier();
	}
}
