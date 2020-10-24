package td3;

import java.util.Map;

public class VariableSymbolique extends ExpressionArithmetique{
	private String symbole;
	
	public VariableSymbolique(String variable) {
		this.symbole = variable;
	}


	public String getSymbole() {
		return symbole;
	}


	public void setSymbole(String symbole) {
		this.symbole = symbole;
	}

	
	public String toString() {
		return this.symbole;
	}


	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique ea = map.get(this);
		if(ea == null) {
			return this;
		}else {
			return ea;
		}
	}


	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique ea = map.get(this);
		if(ea == null) {
			throw new MissingValueException("Impossible de calculer : la valeur de la variable symbolique n'est pas définie");
		}else {
			return ea.calculer();
		}
	}
	
}
