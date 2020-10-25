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

	@Override
	public String toString() {
		return this.symbole;
	}


	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		if(map.containsKey(this) && map.get(this) != null) {
			return map.get(this);
		}else {
			return this;
		}
	}


	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		if(map.containsKey(this) && map.get(this) != null) {
			return map.get(this).calculer();
		}else {
			throw new MissingValueException("Impossible de calculer : la valeur de la variable symbolique n'est pas définie");
		}
	}
	
}
