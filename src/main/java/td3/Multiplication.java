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
	
	
	
	protected ExpressionArithmetique distributiviteAddition() {
		if (this.eaLeft instanceof Addition) {
			Addition add = (Addition) this.eaLeft;
			ExpressionArithmetique mLeft = new Multiplication(add.eaLeft, eaRight);
			ExpressionArithmetique mRight = new Multiplication(add.eaRight, eaRight);
			return new Addition(mLeft, mRight).simplifier();
		}
		else if (this.eaRight instanceof Addition) {
			Addition add = (Addition) this.eaRight;
			ExpressionArithmetique mLeft = new Multiplication(eaLeft, add.eaLeft);
			ExpressionArithmetique mRight = new Multiplication(eaLeft, add.eaRight);
			return new Addition(mLeft, mRight).simplifier();
		}
		else {
			return this;
		}
		
	}
	
	protected ExpressionArithmetique distributiviteSoustraction() {
		if (this.eaLeft instanceof Soustraction) {
			Soustraction sous = (Soustraction) this.eaLeft;
			ExpressionArithmetique mLeft = new Multiplication(sous.eaLeft, eaRight);
			ExpressionArithmetique mRight = new Multiplication(sous.eaRight, eaRight);
			return new Soustraction(mLeft, mRight).simplifier();
		}
		else if (this.eaRight instanceof Soustraction) {
			Soustraction sous = (Soustraction) this.eaRight;
			ExpressionArithmetique mLeft = new Multiplication(eaLeft, sous.eaLeft);
			ExpressionArithmetique mRight = new Multiplication(eaLeft, sous.eaRight);
			return new Soustraction(mLeft, mRight).simplifier();
		}
		else {
			return this;
		}
		
	}
	
	
	protected ExpressionArithmetique simplifie(Matrice gauche, Matrice droite) {
		if(gauche.getColumn() != droite.getRow())
			return this;
		else
			return gauche.produit(droite);
	}
	
	
	@Override
	protected boolean isNeutre(ExpressionArithmetique ea) {
		return (ea.equals(new ConstEntiere(1)));
	}
	
	private ExpressionArithmetique associativite() {

		ExpressionArithmetique eaLeftSimp = this.eaLeft.simplifier();
		ExpressionArithmetique eaRightSimp = this.eaRight.simplifier();
		Multiplication eaCast;
		int action = 0;
		
		/* Pour chaque, on vérifie si on a bien une multiplication à gauche ou à droite avec un paramètre isolé, puis si on doit inverser pour 
		 * simplifier en ConstReelle * un symbole. La variable action permet de raccourcir car certaines "actions" sont les mêmes.
		 */

		if(eaLeftSimp instanceof Multiplication && eaRightSimp instanceof ConstReelle) {
			eaCast = (Multiplication) eaLeftSimp;
			
			if(eaCast.eaLeft instanceof Symbolique && eaCast.eaRight instanceof ConstReelle) action = 1;
			else if(eaCast.eaRight instanceof Symbolique && eaCast.eaLeft instanceof ConstReelle) action = 2;
		}
		
		else if(eaRightSimp instanceof Multiplication && eaLeftSimp instanceof ConstReelle) {
			eaCast = (Multiplication) eaRightSimp;
			
			if(eaCast.eaLeft instanceof Symbolique && eaCast.eaRight instanceof ConstReelle) action = 3;
			else if(eaCast.eaRight instanceof Symbolique && eaCast.eaLeft instanceof ConstReelle) action = 4;
		}
		
		else if(eaLeftSimp instanceof Multiplication && eaRightSimp instanceof Symbolique) {
			eaCast = (Multiplication) eaLeftSimp;
			
			if(eaCast.eaLeft instanceof Symbolique && eaCast.eaRight instanceof ConstReelle) action = 2;
			else if(eaCast.eaRight instanceof Symbolique && eaCast.eaLeft instanceof ConstReelle) action = 1;
		}
		
		else if(eaRightSimp instanceof Multiplication && eaLeftSimp instanceof Symbolique) {
			eaCast = (Multiplication) eaRightSimp;
			
			if(eaCast.eaLeft instanceof Symbolique && eaCast.eaRight instanceof ConstReelle) action = 4;
			else if(eaCast.eaRight instanceof Symbolique && eaCast.eaLeft instanceof ConstReelle) action = 3;
		}
		
		else return this;
		
		ExpressionArithmetique newEaLeft;
		ExpressionArithmetique newEaRight;
		
		switch (action) {
		case (1):
			newEaLeft = new Multiplication(eaCast.eaRight, eaRightSimp).simplifier();
			newEaRight = eaCast.eaLeft;
			break;
		case (2):
			newEaLeft = new Multiplication(eaCast.eaLeft, eaRightSimp).simplifier();
			newEaRight = eaCast.eaRight;
			break;
		case (3):
			newEaLeft = new Multiplication(eaCast.eaRight, eaLeftSimp).simplifier();
			newEaRight = eaCast.eaRight;
			break;
		case (4):
			newEaLeft = new Multiplication(eaCast.eaLeft, eaLeftSimp).simplifier();
			newEaRight = eaCast.eaRight;
			break;
		default:
			return this;
		}
		
		return (new Multiplication(newEaLeft, newEaRight)).simplifier();
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
			Multiplication simplifiedMult = (Multiplication) simplified;
			if (simplifiedMult.eaLeft.equals(new ConstEntiere(0)) || simplifiedMult.eaRight.equals(new ConstEntiere(0))) {
				return new ConstEntiere(0);
			}
			else if (simplifiedMult.eaLeft instanceof Addition || simplifiedMult.eaRight instanceof Addition) { 		
				
				return simplifiedMult.distributiviteAddition();
			}
			else if (simplifiedMult.eaLeft instanceof Soustraction || simplifiedMult.eaRight instanceof Soustraction) {
				
				return simplifiedMult.distributiviteSoustraction();
			}
			else if (simplifiedMult.eaLeft instanceof Multiplication || simplifiedMult.eaRight instanceof Multiplication) {

				 return simplifiedMult.associativite();
			}
			else if(simplifiedMult.eaLeft instanceof Matrice && simplifiedMult.eaRight instanceof Matrice){
				Matrice gauche = (Matrice) simplifiedMult.eaLeft;
				Matrice droite = (Matrice) simplifiedMult.eaRight;
				
				return simplifie(gauche, droite);
			}
			else if(simplifiedMult.eaLeft instanceof Matrice) {
				Matrice gauche = (Matrice) simplifiedMult.eaLeft;
				
				return gauche.produit(simplifiedMult.eaRight);
			}
			else if(simplifiedMult.eaRight instanceof Matrice) {
				Matrice droite = (Matrice) simplifiedMult.eaRight;
				
				return droite.produit(simplifiedMult.eaLeft);
			}
			else {
				return simplifiedMult;
			}
		}	
		return simplified;
	}
	
	@Override
	public ExpressionArithmetique clone() {
		return new Multiplication(eaLeft.clone(), eaRight.clone());
	}

}
