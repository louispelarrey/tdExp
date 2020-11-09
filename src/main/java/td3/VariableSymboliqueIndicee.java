package td3;

public class VariableSymboliqueIndicee extends VariableSymbolique {
	private int indice;
	
	public VariableSymboliqueIndicee(String variable, int indice) {
		super(variable);
		this.indice = indice;
	}
	
	public int getIndice() {
		return this.indice;
	}
}