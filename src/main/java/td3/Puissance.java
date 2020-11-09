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
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstEntiere droite) {
		return droite.getEntier() == 0 ? new ConstEntiere(1) :
				new ConstEntiere(gauche.getEntier() ^ droite.getEntier()).simplifier();
	}
	
	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstEntiere droite) {
		return droite.getEntier() == 0 ? new ConstEntiere(1) :
				new ConstRationnelle(droite.getEntier() ^ gauche.getNumerateur(), gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return this;
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {	
		return this;
	}
	
	@Override
	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, ConstEntiere droite) {
		if(droite.getEntier() == 1) {
			return gauche.simplifier();
		}else if(droite.getEntier() == 0) {
			return new ConstEntiere(1);
		}
		return this;
	}
	
	@Override
	public double calculer() {
		return Math.pow(this.eaLeft.calculer(), this.eaRight.calculer());
	}
	
	@Override
	public ExpressionArithmetique deriver() {
		return new Multiplication(new Multiplication(this.eaRight, new Puissance(this.eaLeft, new Soustraction(this.eaRight, new ConstEntiere(1)))),
				this.eaLeft.deriver()).simplifier();
	}
	
	@Override
	public String toString() {
		if(eaLeft instanceof OperationBinaire) {
			return "(" + eaLeft.toString() + ")" + "^" + eaRight.toString();
		}
		else {
			return eaLeft.toString() + "^" + eaRight.toString();
		}
	}
	
	/*@Override
	protected boolean isNeutre(ExpressionArithmetique ea) {
		return (ea instanceof ConstEntiere && ((ConstEntiere)ea).getEntier() == 1);
	}*/
	
}
