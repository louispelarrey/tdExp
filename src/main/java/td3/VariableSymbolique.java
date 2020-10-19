package td3;

public class VariableSymbolique implements ExpressionArithmetique {
	private final String symbole;
	//private final double value;
	
	public VariableSymbolique(String symbole) {
		this.symbole = symbole;
	}
	

	public String getSymbole() {
		return symbole;
	}


	@Override
	public ExpressionArithmetique simplifier() {
		return this;
	}


	@Override
	public double calculer() {
		return 0;
	}



	
	
}
