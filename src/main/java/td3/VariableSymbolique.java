package td3;

import java.util.Map;

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

	
	public String toString() {
		if(this.valeur != 0)
			return "" + this.valeur;
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
			throw new RuntimeException();
		}else {
			return ea.calculer();
		}
	}
	
}
