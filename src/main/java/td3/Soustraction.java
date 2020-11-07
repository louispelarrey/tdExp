package td3;

import java.util.Map;

public class Soustraction extends OperationBinaire {

	public Soustraction(ExpressionArithmetique eaLeft, ExpressionArithmetique eaRight) {
		super(eaLeft, eaRight);

	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this.eaLeft.calculer(map) - this.eaRight.calculer(map);
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstEntiere droite) {
		return new ConstRationnelle(gauche.getNumerateur() * droite.getEntier() - gauche.getDenominateur() * 1,
				1 * gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return new ConstRationnelle(
				gauche.getNumerateur() * droite.getDenominateur() - gauche.getDenominateur() * droite.getNumerateur(),
				droite.getDenominateur() * gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstEntiere droite) {
		return new ConstEntiere(gauche.getEntier() - droite.getEntier()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {
		return new ConstRationnelle(droite.getDenominateur() * gauche.getEntier() - droite.getNumerateur() * 1,
				1 * droite.getDenominateur()).simplifier();
	}
	
	@Override
	public String toString() {
		return eaLeft.toString() + "-" + eaRight.toString();
	}

	@Override
	public ExpressionArithmetique deriver() {
		return new Soustraction(this.eaLeft.deriver(), this.eaRight.deriver()).simplifier();
	}
	
	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique simplified = super.simplifier(map);
		if (simplified instanceof Soustraction) {
			Soustraction simplifiedSous = (Soustraction) simplified;
			if (simplifiedSous.eaRight.equals(new ConstEntiere(0))) {
				return simplifiedSous.eaLeft;
			}else if(simplifiedSous.eaLeft.equals(new ConstEntiere(0))){
				return simplifiedSous.eaRight;
			}
		}
		return simplified;
	}
}
