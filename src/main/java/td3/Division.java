package td3;

public class Division extends OperationBinaire {

	public Division(ExpressionArithmetique eaLeft, ExpressionArithmetique eaRight) {
		super(eaLeft, eaRight);

	}

	@Override
	public double calculer() {
		return this.eaLeft.calculer() / this.eaRight.calculer();
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
	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, ExpressionArithmetique droite) {
		return this;
	}
	

	@Override
	protected ExpressionArithmetique simplifie(VariableSymbolique gauche, VariableSymbolique droite) {
		return new ConstRationnelle((int)gauche.getValeur(), (int)droite.getValeur()).simplifier();
	}
	
	@Override
	protected ExpressionArithmetique simplifie(VariableSymbolique gauche, ConstEntiere droite) {
		return new ConstRationnelle((int)gauche.getValeur(), droite.getEntier()).simplifier();
	}
	
	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, VariableSymbolique droite) {
		return new ConstRationnelle((int)droite.getValeur(), gauche.getEntier()).simplifier();
	}
	
	@Override
	protected ExpressionArithmetique simplifie(ConstSymbolique gauche, VariableSymbolique droite) {
		return new ConstEntiere((int)gauche.calculer() / (int)droite.getValeur());
	}
	
	@Override
	protected ExpressionArithmetique simplifie(VariableSymbolique gauche, ConstSymbolique droite) {
		return new ConstEntiere((int)gauche.getValeur() / (int)droite.calculer());
	}
}
