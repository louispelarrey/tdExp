package td3;

public class VariableSymbolique extends ExpressionArithmetique{
	private double valeur;
	private String symbole;
	
	public VariableSymbolique(String variable) {
		this.symbole = variable;
	}
	
	
	public double getValeur() {
		return valeur;
	}


	public void setValeur(double valeur) {
		this.valeur = valeur;
	}


	public String getSymbole() {
		return symbole;
	}


	public void setSymbole(String symbole) {
		this.symbole = symbole;
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
		if(this.valeur != 0) {
			return valeur;
		}else {
			return 0;
		}
	}
	
	
	public String toString() {
		if(this.valeur != 0)
			return "" + this.valeur;
		return this.symbole;
	}
	
}
