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
		return new ConstEntiere((int) Math.pow(gauche.getEntier(), droite.getEntier())).simplifier();
	}
	
	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstEntiere droite) {
		return new ConstRationnelle(droite.getEntier() ^ gauche.getNumerateur(), gauche.getDenominateur()).simplifier(); //TODO a verifier ABSOLUMENT !!
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return this; //TODO a faire ou inutile
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {	
		return this;//TODO a faire ou inutile
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
	
	@Override
	protected boolean isNeutre(ExpressionArithmetique ea) {
		return (ea.equals(this.eaRight) && ea.equals(new ConstEntiere(1)));//si 1 est à droite
	}
	
	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique simplified = super.simplifier(map);
		if (simplified instanceof Puissance) {
			Puissance simplifiedPui = (Puissance) simplified;
			
			if (simplifiedPui.eaLeft.equals(new ConstEntiere(0))) {
				return new ConstEntiere(0);
			}
			else if(simplifiedPui.eaRight.equals(new ConstEntiere(0))){
				return new ConstEntiere(1);
			}
			else {
				return simplifiedPui;
			}		
		}
		return simplified;
	}

	@Override
	public ExpressionArithmetique clone() {
		return new Puissance(eaLeft.clone(), eaRight.clone());
	}
}
