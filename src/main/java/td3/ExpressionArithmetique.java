package td3;

import java.util.Collections;
import java.util.Map;

public abstract class ExpressionArithmetique{
	
	@SuppressWarnings("unchecked")
	public ExpressionArithmetique simplifier() {
		return simplifier(Collections.EMPTY_MAP);
	}
	
	@SuppressWarnings("unchecked")
	public double calculer() {
		return calculer(Collections.EMPTY_MAP);
	}
	
	public abstract ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map);
	public abstract double calculer(Map<VariableSymbolique, ExpressionArithmetique> map);
	@Override public abstract String toString(); //demander pour override
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExpressionArithmetique other = (ExpressionArithmetique) obj;
		return !(other.calculer() == this.calculer() || other.simplifier() == this.simplifier());
	}
}
