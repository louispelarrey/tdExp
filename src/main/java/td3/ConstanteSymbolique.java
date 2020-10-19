package td3;

public class ConstanteSymbolique implements ExpressionArithmetique {

	private final String symbole;
	private final double value;
	
	public ConstanteSymbolique(String symbole, double value) {
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
	public ExpressionArithmetique simplifier() {
		return this;
	}

	
	@Override
	public double calculer() {
		return value;
	}
	
}
