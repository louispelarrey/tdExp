package td3;

import java.util.Map;

public class ConstSymbolique extends ExpressionArithmetique{
	private String symbole;
	
	public ConstSymbolique(String symbole) {
		this.symbole = symbole;
	}
	
	public ConstSymbolique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this;
	}
	
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		double valeurApprox = 0;
		if (this.symbole == "π" || this.symbole == "pi") {
			valeurApprox = Math.PI;
		}else if(this.symbole == "e") {
			valeurApprox = Math.exp(1.0);
		}
		return valeurApprox;
	}

}
