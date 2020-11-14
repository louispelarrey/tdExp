package td3;

public class VariableSymboliqueIndicee extends VariableSymbolique implements Comparable {
	private int indice;
	
	public VariableSymboliqueIndicee(String variable, int indice) {
		super(variable);
		this.indice = indice;
	}
	
	public int getIndice() {
		return this.indice;
	}
	
	public int compareTo(Object argument) {
		if(argument instanceof VariableSymboliqueIndicee) {
			VariableSymboliqueIndicee autreVariable = (VariableSymboliqueIndicee) argument;
			
			return Integer.compare(this.indice, autreVariable.getIndice());
		}
		
		else throw new IllegalArgumentException();
	}
}