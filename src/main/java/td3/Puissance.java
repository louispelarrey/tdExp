package td3;

import java.util.Map;

public class Puissance extends OperationBinaire{

	public Puissance(ExpressionArithmetique eaLeft, ExpressionArithmetique eaRight) {
		super(eaLeft, eaRight);
	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return Math.pow(this.eaLeft.calculer(map), this.eaRight.calculer(map));
	}
	
	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstEntiere droite) {
		return new ConstRationnelle(droite.getEntier() ^ gauche.getNumerateur(), gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return this;
	}


	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {
		return this.simplifie(droite, gauche).simplifier();
	}
	
	@Override
	public double calculer() {
		return Math.pow(this.eaLeft.calculer(), this.eaRight.calculer());
	}
	
	@Override
	public String toString() {
		return eaLeft.toString() + "^" + eaRight.toString();
	}
	
}
