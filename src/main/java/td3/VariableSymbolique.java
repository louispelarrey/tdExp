package td3;

public class VariableSymbolique implements ExpressionArithmetique{
	private double valeur;
	private String symbole;
	
	public VariableSymbolique(String variable) {
		this.symbole = variable;
	}
	
	
	public VariableSymbolique(String variable, double valeur) {
		this.symbole = variable;
		this.valeur = valeur;
	}


	@Override
	public ExpressionArithmetique simplifier() {
		return this;
	}


	@Override
	public double calculer() {
		return 0;
	}
	
	
	public String toString() {
		return this.symbole;
	}
	
}
