package td3;

import java.util.Map;

public class Multiplication extends OperationBinaire {

	public Multiplication(ExpressionArithmetique eaLeft, ExpressionArithmetique eaRight) {
		super(eaLeft, eaRight);

	}
	
	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this.eaLeft.calculer(map) * this.eaRight.calculer(map);
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstEntiere droite) {
		return new ConstRationnelle(droite.getEntier() * gauche.getNumerateur(), gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return new ConstRationnelle(gauche.getNumerateur() * droite.getNumerateur(),
				droite.getDenominateur() * gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstEntiere droite) {
		return new ConstEntiere(gauche.getEntier() * droite.getEntier()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {
		return this.simplifie(droite, gauche).simplifier();
	}
	
	//Distributivite
	@Override
	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, Addition droite) {
		ExpressionArithmetique mLeft = new Multiplication(gauche, droite.eaLeft).simplifier();
		ExpressionArithmetique mRight = new Multiplication(gauche, droite.eaRight).simplifier();
		return new Addition(mLeft, mRight).simplifier();
	}
	
	@Override
	protected ExpressionArithmetique simplifie(Addition gauche, ExpressionArithmetique droite) {
		ExpressionArithmetique mLeft = new Multiplication(gauche.eaLeft, droite).simplifier();
		ExpressionArithmetique mRight = new Multiplication(gauche.eaRight, droite).simplifier();
		return new Addition(mLeft, mRight).simplifier();
	}
	
	@Override
	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, Soustraction droite) {
		ExpressionArithmetique mLeft = new Multiplication(gauche, droite.eaLeft).simplifier();
		ExpressionArithmetique mRight = new Multiplication(gauche, droite.eaRight).simplifier();
		return new Soustraction(mLeft, mRight).simplifier();
	}
	
	@Override
	protected ExpressionArithmetique simplifie(Soustraction gauche, ExpressionArithmetique droite) {
		ExpressionArithmetique mLeft = new Multiplication(gauche.eaLeft, droite).simplifier();
		ExpressionArithmetique mRight = new Multiplication(gauche.eaRight, droite).simplifier();
		return new Soustraction(mLeft, mRight).simplifier();
	}
	
	@Override
	public String toString() {
		return eaLeft.toString() + "*" + eaRight.toString();
	}

	@Override
	protected boolean isNeutre(ExpressionArithmetique ea) {
		return (ea instanceof ConstEntiere && ((ConstEntiere)ea).getEntier() == 1);
	}
	
}
