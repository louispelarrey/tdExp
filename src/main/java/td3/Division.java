package td3;

import java.util.Map;

public class Division extends OperationBinaire {

	public Division(ExpressionArithmetique eaLeft, ExpressionArithmetique eaRight) {
		super(eaLeft, eaRight);
	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this.eaLeft.calculer(map) / this.eaRight.calculer(map);
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstEntiere droite) {
		return new ConstRationnelle(gauche.getNumerateur(), gauche.getDenominateur() * droite.getEntier()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {
		return new ConstRationnelle(gauche.getEntier() * droite.getNumerateur(), droite.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return new ConstRationnelle(gauche.getNumerateur() * droite.getDenominateur(),
				gauche.getDenominateur() * droite.getNumerateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstEntiere droite) {
		return new ConstRationnelle(gauche.getEntier(), droite.getEntier()).simplifier();
	}
	
	@Override
	public String toString() {
		return eaLeft.toString() + "/" + eaRight.toString();
	}
	
	@Override
	public ExpressionArithmetique deriver() {
		return new Division(new Soustraction(new Multiplication(this.eaLeft.deriver(), this.eaRight),
				new Multiplication(this.eaLeft, this.eaRight.deriver())), new Puissance(this.eaRight, new ConstEntiere(2))).simplifier();
	}
	
	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique simplified = super.simplifier(map);
		if (simplified instanceof Division) {
			Division simplifiedDiv = (Division) simplified;
			if (simplifiedDiv.eaRight.equals(new ConstEntiere(1))) {
				return simplifiedDiv.eaLeft;
			}
		}
		return simplified;
	}
}
