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
	
	protected ExpressionArithmetique simplifie(Matrice gauche, ExpressionArithmetique droite) {
		return gauche.produit(droite);
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
	
	public ExpressionArithmetique associativite() {
		// La partie suivante gère l'associativité
		
		ExpressionArithmetique eaLeftSimp = this.eaLeft; //TODO a modifier
		ExpressionArithmetique eaRightSimp = this.eaRight;
		
		ExpressionArithmetique newEaLeft;
		ExpressionArithmetique newEaRight;
		ExpressionArithmetique res;

		if(eaLeftSimp instanceof Multiplication && (eaRightSimp instanceof ConstEntiere || eaRightSimp instanceof ConstRationnelle)) {
			Multiplication eaLeftCast = (Multiplication) eaLeftSimp;
			
			if((eaLeftCast.getEaLeft() instanceof VariableSymbolique || eaLeftCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaRight() instanceof ConstEntiere || eaLeftCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = new Multiplication(eaLeftCast.getEaRight(), eaRightSimp);
				newEaRight = eaLeftCast.getEaLeft();
				
				res = new Multiplication(newEaLeft, newEaRight).simplifier();
			}
			
			else if((eaLeftCast.getEaRight() instanceof VariableSymbolique || eaLeftCast.getEaRight() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaLeft() instanceof ConstEntiere || eaLeftCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft = new Multiplication(eaLeftCast.getEaLeft(), eaRightSimp);
				newEaRight = eaLeftCast.getEaRight();
				
				res = new Multiplication(newEaLeft, newEaRight).simplifier();
			}
			
			else res = this;
		}
		
		else if(eaRightSimp instanceof Multiplication && (eaLeftSimp instanceof ConstEntiere || eaLeftSimp instanceof ConstRationnelle)) {
			Multiplication eaRightCast = (Multiplication) eaRightSimp;
			
			if((eaRightCast.getEaLeft() instanceof VariableSymbolique || eaRightCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaRightCast.getEaRight() instanceof ConstEntiere || eaRightCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = new Multiplication(eaRightCast.getEaRight(), eaLeftSimp);
				newEaRight = eaRightCast.getEaLeft();
				
				res = new Multiplication(newEaLeft, newEaRight).simplifier();
			}
			
			else if((eaRightCast.getEaRight() instanceof VariableSymbolique  || eaRightCast.getEaRight() instanceof ConstSymbolique) &&
					(eaRightCast.getEaLeft() instanceof ConstEntiere || eaRightCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft = new Multiplication(eaRightCast.getEaLeft(), eaLeftSimp);
				newEaRight = eaRightCast.getEaRight();
				
				res = new Multiplication(newEaLeft, newEaRight).simplifier();
			}
			
			else res = this;
		}
		
		else if(eaLeftSimp instanceof Multiplication && (eaRightSimp instanceof VariableSymbolique || eaRightSimp instanceof ConstSymbolique)) {
			Multiplication eaLeftCast = (Multiplication) eaLeftSimp;
			
			if((eaLeftCast.getEaLeft() instanceof VariableSymbolique || eaLeftCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaRight() instanceof ConstEntiere || eaLeftCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = eaLeftCast.getEaRight();
				newEaRight = new Multiplication(eaLeftCast.getEaLeft(), eaRightSimp);
				
				res = new Multiplication(newEaLeft, newEaRight).simplifier();
			}
			
			else if((eaLeftCast.getEaRight() instanceof VariableSymbolique || eaLeftCast.getEaRight() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaLeft() instanceof ConstEntiere || eaLeftCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft =  eaLeftCast.getEaLeft();
				newEaRight = new Multiplication(eaLeftCast.getEaRight(), eaRightSimp);
				
				res = new Multiplication(newEaLeft, newEaRight).simplifier();
			}
			
			else res = this;
		}
		
		else if(eaRightSimp instanceof Multiplication && (eaLeftSimp instanceof VariableSymbolique || eaLeftSimp instanceof ConstSymbolique)) {
			Multiplication eaRightCast = (Multiplication) eaRightSimp;
			
			if((eaRightCast.getEaLeft() instanceof VariableSymbolique || eaRightCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaRightCast.getEaRight() instanceof ConstEntiere || eaRightCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = eaRightCast.getEaRight();
				newEaRight = new Multiplication(eaRightCast.getEaLeft(), eaLeftSimp);
				
				res = new Multiplication(newEaLeft, newEaRight).simplifier();
			}
			
			else if((eaRightCast.getEaRight() instanceof VariableSymbolique || eaRightCast.getEaRight() instanceof ConstSymbolique) &&
					(eaRightCast.getEaLeft() instanceof ConstEntiere || eaRightCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft = eaRightCast.getEaLeft();
				newEaRight = new Multiplication(eaRightCast.getEaRight(), eaLeftSimp);
				
				res = new Multiplication(newEaLeft, newEaRight).simplifier();
			}
			
			else res = this;
			
		}

		else {
			res = this;
		}
		
		return res;
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
			else if (simplifiedMult.eaRight instanceof Multiplication) { //TODO associativité a verifier

				 return associativite();
			}
			else if(simplifiedMult.eaLeft instanceof Matrice && simplifiedMult.eaRight instanceof Matrice){
				Matrice gauche = (Matrice) simplifiedMult.eaLeft;
				Matrice droite = (Matrice) simplifiedMult.eaRight;
				
				return simplifie(gauche, droite);
			}
			else if(simplifiedMult.eaLeft instanceof Matrice) {
				Matrice gauche = (Matrice) simplifiedMult.eaLeft;
				
				return simplifie(gauche, simplifiedMult.eaRight);
			}
			else if(simplifiedMult.eaRight instanceof Matrice) {
				Matrice droite = (Matrice) simplifiedMult.eaRight;
				
				return simplifie(droite, simplifiedMult.eaLeft);
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
