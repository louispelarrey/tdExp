package td3;

public class ConstSymbolique implements ExpressionArithmetique{
	private String symbole;
	
	public ConstSymbolique(String symbole) {
		this.symbole = symbole;
	}
	
	public ConstSymbolique simplifier() {
		return this;
	}
	
	public double calculer() {
		double valeurApprox = 0;
		if (this.symbole == "π" || this.symbole == "pi") {
			valeurApprox = Math.PI;
		}else if(this.symbole == "e") {
			valeurApprox = Math.exp(1.0);
		}
		return valeurApprox;
	}
}
