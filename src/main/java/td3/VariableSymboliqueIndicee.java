package td3;

import java.util.Map;

public class VariableSymboliqueIndicee extends VariableSymbolique {
	private ExpressionArithmetique indice;
	
	public VariableSymboliqueIndicee(String variable, ExpressionArithmetique indice) {
		super(variable);
		this.indice = indice;
	}
	
	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique e = super.simplifier(map);
			if(e.equals(this)) {
				e = new VariableSymboliqueIndicee(this.symbole, this.indice.simplifier(map));
			}
			else {
				e = e.clone().simplifier(map);
			}
			return e;
	}
	
	public ExpressionArithmetique getIndice() {
		return this.indice;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		VariableSymboliqueIndicee other = (VariableSymboliqueIndicee) obj;
		return (this.symbole.equals(other.symbole) && this.indice.equals(other.indice));
	}

	
	@Override
	public ExpressionArithmetique clone() {
		return new VariableSymboliqueIndicee(this.symbole, this.indice.clone());
	}

	@Override
	public String toString() {
		return symbole + indice.toString();
	}
	

	
	
	
}