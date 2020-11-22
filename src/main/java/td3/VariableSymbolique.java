package td3;

import java.util.Map;

public class VariableSymbolique extends ExpressionArithmetique implements Symbolique {
	protected final String symbole;
	
	public VariableSymbolique(String variable) {
		this.symbole = variable;
	}


	public String getSymbole() {
		return symbole;
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


	@Override
	public ExpressionArithmetique deriver() {
		return new ConstEntiere(1);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbole == null) ? 0 : symbole.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VariableSymbolique other = (VariableSymbolique) obj;
		if (symbole == null) {
			if (other.symbole != null)
				return false;
		} else if (!symbole.equals(other.symbole))
			return false;
		return true;
	}
	
	@Override
	public ExpressionArithmetique clone() {
		return new VariableSymbolique(this.symbole);
	}
	
}
