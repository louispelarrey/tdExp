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
	public ExpressionArithmetique simplifier() {
		// La partie suivante gère l'associativité
		
		ExpressionArithmetique eaLeftSimp = this.eaLeft.simplifier();
		ExpressionArithmetique eaRightSimp = this.eaRight.simplifier();
		
		ExpressionArithmetique newEaLeft = null;
		ExpressionArithmetique newEaRight = null;
		ExpressionArithmetique res = null;

		if(eaLeftSimp instanceof Multiplication && (eaRightSimp instanceof ConstEntiere || eaRightSimp instanceof ConstRationnelle)) {
			Multiplication eaLeftCast = (Multiplication) eaLeftSimp;
			
			if((eaLeftCast.getEaLeft() instanceof VariableSymbolique || eaLeftCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaRight() instanceof ConstEntiere || eaLeftCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = new Multiplication(eaLeftCast.getEaRight(), eaRightSimp).simplifier();
				newEaRight = eaLeftCast.getEaLeft();
			}
			
			else if((eaLeftCast.getEaRight() instanceof VariableSymbolique || eaLeftCast.getEaRight() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaLeft() instanceof ConstEntiere || eaLeftCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft = new Multiplication(eaLeftCast.getEaLeft(), eaRightSimp).simplifier();
				newEaRight = eaLeftCast.getEaRight();
			}
			
			res = new Multiplication(newEaLeft, newEaRight);
		}
		
		else if(eaRightSimp instanceof Multiplication && (eaLeftSimp instanceof ConstEntiere || eaLeftSimp instanceof ConstRationnelle)) {
			Multiplication eaRightCast = (Multiplication) eaRightSimp;
			
			if((eaRightCast.getEaLeft() instanceof VariableSymbolique || eaRightCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaRightCast.getEaRight() instanceof ConstEntiere || eaRightCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = new Multiplication(eaRightCast.getEaRight(), eaLeftSimp).simplifier();
				newEaRight = eaRightCast.getEaLeft();
			}
			
			else if((eaRightCast.getEaRight() instanceof VariableSymbolique  || eaRightCast.getEaRight() instanceof ConstSymbolique) &&
					(eaRightCast.getEaLeft() instanceof ConstEntiere || eaRightCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft = new Multiplication(eaRightCast.getEaLeft(), eaLeftSimp).simplifier();
				newEaRight = eaRightCast.getEaRight();
				
			}
			
			res = new Multiplication(newEaLeft, newEaRight);
		}
		
		else if(eaLeftSimp instanceof Multiplication && (eaRightSimp instanceof VariableSymbolique || eaRightSimp instanceof ConstSymbolique)) {
			Multiplication eaLeftCast = (Multiplication) eaLeftSimp;
			
			if((eaLeftCast.getEaLeft() instanceof VariableSymbolique || eaLeftCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaRight() instanceof ConstEntiere || eaLeftCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = eaLeftCast.getEaRight();
				newEaRight = new Multiplication(eaLeftCast.getEaLeft(), eaRightSimp).simplifier();
			}
			
			else if((eaLeftCast.getEaRight() instanceof VariableSymbolique || eaLeftCast.getEaRight() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaLeft() instanceof ConstEntiere || eaLeftCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft =  eaLeftCast.getEaLeft();
				newEaRight = new Multiplication(eaLeftCast.getEaRight(), eaRightSimp).simplifier();
			}
			
			res = new Multiplication(newEaLeft, newEaRight);
		}
		
		else if(eaRightSimp instanceof Multiplication && (eaLeftSimp instanceof VariableSymbolique || eaLeftSimp instanceof ConstSymbolique)) {
			Multiplication eaRightCast = (Multiplication) eaRightSimp;
			
			if((eaRightCast.getEaLeft() instanceof VariableSymbolique || eaRightCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaRightCast.getEaRight() instanceof ConstEntiere || eaRightCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = eaRightCast.getEaRight();
				newEaRight = new Multiplication(eaRightCast.getEaLeft(), eaLeftSimp).simplifier();
			}
			
			else if((eaRightCast.getEaRight() instanceof VariableSymbolique || eaRightCast.getEaRight() instanceof ConstSymbolique) &&
					(eaRightCast.getEaLeft() instanceof ConstEntiere || eaRightCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft = eaRightCast.getEaLeft();
				newEaRight = new Multiplication(eaRightCast.getEaRight(), eaLeftSimp).simplifier();
			}
			
			res = new Multiplication(newEaLeft, newEaRight);
		}

		else {
			res = super.simplifier();
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
	protected boolean isNeutre(ExpressionArithmetique ea) {
		return (ea instanceof ConstEntiere && ((ConstEntiere)ea).getEntier() == 1);
	}
	
}
