package td3;

import java.util.Map;

public class ConstSymbolique extends ExpressionArithmetique{
	private final String symbole;
	private final double value;
	
	public ConstSymbolique(String symbole, double value) {
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

}
