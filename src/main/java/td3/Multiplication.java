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
	
	@Override
	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, ConstEntiere droite) {
		return this.simplifie(droite, gauche);
		
	}
	
	//Distributivite
	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, Addition droite) {
		ExpressionArithmetique mLeft = new Multiplication(gauche, droite.eaLeft).simplifier();
		ExpressionArithmetique mRight = new Multiplication(gauche, droite.eaRight).simplifier();
		return new Addition(mLeft, mRight).simplifier();
	}
	

	protected ExpressionArithmetique simplifie(Addition gauche, ExpressionArithmetique droite) {
		ExpressionArithmetique mLeft = new Multiplication(gauche.eaLeft, droite).simplifier();
		ExpressionArithmetique mRight = new Multiplication(gauche.eaRight, droite).simplifier();
		return new Addition(mLeft, mRight).simplifier();
	}
	

	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, Soustraction droite) {
		ExpressionArithmetique mLeft = new Multiplication(gauche, droite.eaLeft).simplifier();
		ExpressionArithmetique mRight = new Multiplication(gauche, droite.eaRight).simplifier();
		return new Soustraction(mLeft, mRight).simplifier();
	}
	

	protected ExpressionArithmetique simplifie(Soustraction gauche, ExpressionArithmetique droite) {
		ExpressionArithmetique mLeft = new Multiplication(gauche.eaLeft, droite).simplifier();
		ExpressionArithmetique mRight = new Multiplication(gauche.eaRight, droite).simplifier();
		return new Soustraction(mLeft, mRight).simplifier();
	}
	
	
	
	private Multiplication associativite() {

		ExpressionArithmetique eaLeftSimp = this.eaLeft.simplifier();
		ExpressionArithmetique eaRightSimp = this.eaRight.simplifier();
		Multiplication eaCast;
		int action = 0;
		
		/* Pour chaque, on vérifie si on a bien une multiplication à gauche ou à droite avec un paramètre isolé, puis si on doit inverser pour 
		 * simplifier en ConstReelle * un symbole. La variable action permet de raccourcir car certaines "actions" sont les mêmes.
		 */

		if(eaLeftSimp instanceof Multiplication && eaRightSimp instanceof ConstReelle) {
			eaCast = (Multiplication) eaLeftSimp;
			
			if(eaCast.getEaLeft() instanceof Symbolique && eaCast.getEaRight() instanceof ConstReelle) action = 1;
			else if(eaCast.getEaRight() instanceof Symbolique && eaCast.getEaLeft() instanceof ConstReelle) action = 2;
		}
		
		else if(eaRightSimp instanceof Multiplication && eaLeftSimp instanceof ConstReelle) {
			eaCast = (Multiplication) eaRightSimp;
			
			if(eaCast.getEaLeft() instanceof Symbolique && eaCast.getEaRight() instanceof ConstReelle) action = 3;
			else if(eaCast.getEaRight() instanceof Symbolique && eaCast.getEaLeft() instanceof ConstReelle) action = 4;
		}
		
		else if(eaLeftSimp instanceof Multiplication && eaRightSimp instanceof Symbolique) {
			eaCast = (Multiplication) eaLeftSimp;
			
			if(eaCast.getEaLeft() instanceof Symbolique && eaCast.getEaRight() instanceof ConstReelle) action = 2;
			else if(eaCast.getEaRight() instanceof Symbolique && eaCast.getEaLeft() instanceof ConstReelle) action = 1;
		}
		
		else if(eaRightSimp instanceof Multiplication && eaLeftSimp instanceof Symbolique) {
			eaCast = (Multiplication) eaRightSimp;
			
			if(eaCast.getEaLeft() instanceof Symbolique && eaCast.getEaRight() instanceof ConstReelle) action = 4;
			else if(eaCast.getEaRight() instanceof Symbolique && eaCast.getEaLeft() instanceof ConstReelle) action = 3;
		}
		
		else return this;
		
		ExpressionArithmetique newEaLeft;
		ExpressionArithmetique newEaRight;
		
		if(action == 1) {
			newEaLeft = new Multiplication(eaCast.getEaRight(), eaRightSimp).simplifier();
			newEaRight = eaCast.getEaLeft();
		} else if(action == 2) {
			newEaLeft = new Multiplication(eaCast.getEaLeft(), eaRightSimp).simplifier();
			newEaRight = eaCast.getEaRight();
		} else if(action == 3) {
			newEaLeft = new Multiplication(eaCast.getEaRight(), eaLeftSimp).simplifier();
			newEaRight = eaCast.getEaLeft();
		} else if(action == 4) {
			newEaLeft = new Multiplication(eaCast.getEaLeft(), eaLeftSimp).simplifier();
			newEaRight = eaCast.getEaRight();
		}
		else return this;
		
		return new Multiplication(newEaLeft, newEaRight);
	}
	
	@Override
	public String toString() {
		String eaLeftString = (eaLeft instanceof OperationBinaire) ? "(" + eaLeft.toString() + ")" : eaLeft.toString();
		String eaRightString = (eaRight instanceof OperationBinaire) ? "(" + eaRight.toString() + ")" : eaRight.toString();
		
		return eaLeftString + "*" + eaRightString;
	}
	
	@Override
	public ExpressionArithmetique deriver() {
		return new Addition(new Multiplication(this.eaLeft.deriver(), this.eaRight), new Multiplication(this.eaLeft, this.eaRight.deriver())).simplifier();
	}
	
	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique simplified = super.simplifier(map);
		if (simplified instanceof Multiplication) {
			Multiplication simplifiedMult = ((Multiplication) simplified).associativite();
			if (simplifiedMult.eaLeft.equals(new ConstEntiere(0)) || simplifiedMult.eaRight.equals(new ConstEntiere(0))) {
				return new ConstEntiere(0);
			}
			else if(simplifiedMult.eaLeft.equals(new ConstEntiere(1))){
				return simplifiedMult.eaRight;
			}
			else if(simplifiedMult.eaRight.equals(new ConstEntiere(1))){
				return simplifiedMult.eaLeft;
			}
			else if (simplifiedMult.eaRight instanceof Addition) { //distributivité addition
				Addition droite = (Addition) simplifiedMult.eaRight;

				 return simplifie(simplifiedMult.eaLeft, droite);
			}
			else if (simplifiedMult.eaLeft instanceof Addition) { 		
				Addition gauche = (Addition) simplifiedMult.eaLeft;
				

				return simplifie(gauche, simplifiedMult.eaRight);
			}
			else if (simplifiedMult.eaRight instanceof Soustraction) {//distributivité soustraction
				Soustraction droite = (Soustraction) simplifiedMult.eaRight;

				return simplifie(simplifiedMult.eaLeft, droite);
			}
			else if (simplifiedMult.eaLeft instanceof Soustraction) { 		
				Soustraction gauche = (Soustraction) simplifiedMult.eaLeft;

				return simplifie(gauche, simplifiedMult.eaRight);
			}
			else {
				return simplifiedMult;
			}
		}	
		return simplified;
	}

}
