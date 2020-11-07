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
		return droite.getEntier() == 0 ? new ConstEntiere(0) :
				new ConstRationnelle(droite.getEntier() * gauche.getNumerateur(), gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return new ConstRationnelle(gauche.getNumerateur() * droite.getNumerateur(),
				droite.getDenominateur() * gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstEntiere droite) {
		return droite.getEntier() == 0 || gauche.getEntier() == 0 ? new ConstEntiere(0) :
			new ConstEntiere(gauche.getEntier() * droite.getEntier()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {
		return gauche.getEntier() == 0 ? new ConstEntiere(0) :
			this.simplifie(droite, gauche).simplifier();
	}
	
	@Override
	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, ConstEntiere droite) {
		if(droite.getEntier() == 1) {
			return gauche.simplifier();
		}else if(droite.getEntier() == 0) {
			return new ConstEntiere(0);
		}
		return this.simplifie(droite, gauche);
	}
	
	@Override
	public String toString() {
		return eaLeft.toString() + "*" + eaRight.toString();
	}
	
	@Override
	public ExpressionArithmetique deriver() {
		return new Addition(new Multiplication(this.eaLeft.deriver(), this.eaRight), new Multiplication(this.eaLeft, this.eaRight.deriver())).simplifier();
	}
	
	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique simplified = super.simplifier(map);
		if (simplified instanceof Multiplication) {
			Multiplication simplifiedMult = (Multiplication) simplified;
			if (simplifiedMult.eaLeft.equals(new ConstEntiere(0)) || simplifiedMult.eaRight.equals(new ConstEntiere(0))) {
				return new ConstEntiere(0);
			}else if(simplifiedMult.eaLeft.equals(new ConstEntiere(1))){
				return simplifiedMult.eaRight;
			}else if(simplifiedMult.eaRight.equals(new ConstEntiere(1))){
				return simplifiedMult.eaLeft;
			}
		}
		return simplified;
	}
}
