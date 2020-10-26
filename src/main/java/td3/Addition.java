
package td3;

import java.util.ArrayList;
import java.util.Map;

public class Addition extends OperationBinaire {

	public Addition(ExpressionArithmetique eaLeft, ExpressionArithmetique eaRight) {
		super(eaLeft, eaRight);
	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this.eaLeft.calculer(map) + this.eaRight.calculer(map);
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstEntiere droite) {
		return new ConstRationnelle(gauche.getNumerateur() * droite.getEntier() + gauche.getDenominateur() * 1,
				1 * gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return new ConstRationnelle(
				gauche.getNumerateur() * droite.getDenominateur() + gauche.getDenominateur() * droite.getNumerateur(),
				droite.getDenominateur() * gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstEntiere droite) {
		return new ConstEntiere(gauche.getEntier() + droite.getEntier()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {
		return simplifie(droite, gauche).simplifier();
	}
	
	@Override
	public ExpressionArithmetique simplifier() {
		ExpressionArithmetique eaLeftSimp = this.eaLeft.simplifier();
		ExpressionArithmetique eaRightSimp = this.eaRight.simplifier();
		
		ExpressionArithmetique newEaLeft = null;
		ExpressionArithmetique newEaRight = null;
		ExpressionArithmetique res = null;

		if(eaLeftSimp instanceof Addition && (eaRightSimp instanceof ConstEntiere || eaRightSimp instanceof ConstRationnelle)) {
			Addition eaLeftCast = (Addition) eaLeftSimp;
			
			if((eaLeftCast.getEaLeft() instanceof VariableSymbolique || eaLeftCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaRight() instanceof ConstEntiere || eaLeftCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = new Addition(eaLeftCast.getEaRight(), eaRightSimp).simplifier();
				newEaRight = eaLeftCast.getEaLeft();
			}
			
			else if((eaLeftCast.getEaRight() instanceof VariableSymbolique || eaLeftCast.getEaRight() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaLeft() instanceof ConstEntiere || eaLeftCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft = new Addition(eaLeftCast.getEaLeft(), eaRightSimp).simplifier();
				newEaRight = eaLeftCast.getEaRight();
			}
			
			res = new Addition(newEaLeft, newEaRight);
		}
		
		else if(eaRightSimp instanceof Addition && (eaLeftSimp instanceof ConstEntiere || eaLeftSimp instanceof ConstRationnelle)) {
			Addition eaRightCast = (Addition) eaRightSimp;
			
			if((eaRightCast.getEaLeft() instanceof VariableSymbolique || eaRightCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaRightCast.getEaRight() instanceof ConstEntiere || eaRightCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = new Addition(eaRightCast.getEaRight(), eaLeftSimp).simplifier();
				newEaRight = eaRightCast.getEaLeft();
			}
			
			else if((eaRightCast.getEaRight() instanceof VariableSymbolique  || eaRightCast.getEaRight() instanceof ConstSymbolique) &&
					(eaRightCast.getEaLeft() instanceof ConstEntiere || eaRightCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft = new Addition(eaRightCast.getEaLeft(), eaLeftSimp).simplifier();
				newEaRight = eaRightCast.getEaRight();
				
			}
			
			res = new Addition(newEaLeft, newEaRight);
		}
		
		else if(eaLeftSimp instanceof Addition && (eaRightSimp instanceof VariableSymbolique || eaRightSimp instanceof ConstSymbolique)) {
			Addition eaLeftCast = (Addition) eaLeftSimp;
			
			if((eaLeftCast.getEaLeft() instanceof VariableSymbolique || eaLeftCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaRight() instanceof ConstEntiere || eaLeftCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = eaLeftCast.getEaRight();
				newEaRight = new Addition(eaLeftCast.getEaLeft(), eaRightSimp).simplifier();
			}
			
			else if((eaLeftCast.getEaRight() instanceof VariableSymbolique || eaLeftCast.getEaRight() instanceof ConstSymbolique) &&
					(eaLeftCast.getEaLeft() instanceof ConstEntiere || eaLeftCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft =  eaLeftCast.getEaLeft();
				newEaRight = new Addition(eaLeftCast.getEaRight(), eaRightSimp).simplifier();
			}
			
			res = new Addition(newEaLeft, newEaRight);
		}
		
		else if(eaRightSimp instanceof Addition && (eaLeftSimp instanceof VariableSymbolique || eaLeftSimp instanceof ConstSymbolique)) {
			Addition eaRightCast = (Addition) eaRightSimp;
			
			if((eaRightCast.getEaLeft() instanceof VariableSymbolique || eaRightCast.getEaLeft() instanceof ConstSymbolique) &&
					(eaRightCast.getEaRight() instanceof ConstEntiere || eaRightCast.getEaRight() instanceof ConstRationnelle)) {
				
				newEaLeft = eaRightCast.getEaRight();
				newEaRight = new Addition(eaRightCast.getEaLeft(), eaLeftSimp).simplifier();
			}
			
			else if((eaRightCast.getEaRight() instanceof VariableSymbolique || eaRightCast.getEaRight() instanceof ConstSymbolique) &&
					(eaRightCast.getEaLeft() instanceof ConstEntiere || eaRightCast.getEaLeft() instanceof ConstRationnelle)) {
				
				newEaLeft = eaRightCast.getEaLeft();
				newEaRight = new Addition(eaRightCast.getEaRight(), eaLeftSimp).simplifier();
			}
			
			res = new Addition(newEaLeft, newEaRight);
		}

		else {
			res = super.simplifier();
		}
		
		return res;
	}
	
	@Override
	public String toString() {
		return eaLeft.toString() + "+" + eaRight.toString();
	}
}