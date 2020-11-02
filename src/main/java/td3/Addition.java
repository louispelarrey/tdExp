
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
		// La partie suivante gère l'associativité
		
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
	
	public ExpressionArithmetique idRemarquable() {
		boolean isIdRemarquable = true;
		
		ExpressionArithmetique part1 = null, part2 = null, part3 = null, idRemarquable = null;
		
		if(this.eaLeft instanceof Addition) {
			Addition castEaLeft = (Addition) this.eaLeft;
			part1 = castEaLeft.getEaLeft();
			part2 = castEaLeft.getEaRight();
			part3 = this.eaRight;
		}
		
		else if(this.eaRight instanceof Addition) {
			Addition castEaRight = (Addition) this.eaRight;
			part1 = this.eaLeft;
			part2 = castEaRight.getEaLeft();
			part3 = castEaRight.getEaRight();
		}
		
		else {
			isIdRemarquable = false;
		}
		
		globalCond:	
		if(part2 instanceof Multiplication && isIdRemarquable) {
			ConstEntiere constanteIdentite = new ConstEntiere(1);
			ConstEntiere constanteMilieu = new ConstEntiere(2);
			ConstEntiere constanteMilieuNeg = (ConstEntiere) new Multiplication(constanteMilieu, new ConstEntiere(-1)).simplifier();
			
			boolean milieuNeg;
			
			Puissance part1NoConst, part3NoConst;
			Multiplication part2NoConst;
			
			if(part1 instanceof Multiplication && ((Multiplication) part1).getEaLeft() instanceof ConstEntiere && 
					((Multiplication) part1).getEaRight() instanceof Puissance && ((Multiplication) part3).getEaRight() instanceof Puissance)  {
				constanteIdentite = (ConstEntiere) ((Multiplication) part1).getEaLeft();
				constanteMilieu = (ConstEntiere) new Multiplication(constanteIdentite, new ConstEntiere(2)).simplifier();
				constanteMilieuNeg = (ConstEntiere) new Multiplication(constanteMilieu, new ConstEntiere(-1)).simplifier();
				
				if(!(part3 instanceof Multiplication) || !constanteIdentite.equals(((Multiplication) part3).getEaLeft())) {
					isIdRemarquable = false;
					break globalCond;
				}
				
				else {
					if(((Multiplication) part2).getEaLeft().equals(constanteMilieu)) {
						milieuNeg = false;
					}
					
					else if(((Multiplication) part2).getEaLeft().equals(constanteMilieuNeg)) {
						milieuNeg = true;
						constanteMilieu = constanteMilieuNeg;
					}
					
					else {
						isIdRemarquable = false;
						break globalCond;
					}
					
					part1NoConst = (Puissance) ((Multiplication) part1).getEaRight();
					part2NoConst = (Multiplication) ((Multiplication) part2).getEaRight();
					part3NoConst = (Puissance) ((Multiplication) part3).getEaRight();
					

				}
			}
			
			else {
				part1NoConst = (Puissance) part1;
				part3NoConst = (Puissance) part3;
				
				if(((Multiplication) part2).getEaLeft().equals(constanteMilieu)) {
					milieuNeg = false;
					part2NoConst = (Multiplication) part2;
				}
				
				else if(((Multiplication) part2).getEaLeft().equals(constanteMilieuNeg)) {
					milieuNeg = true;
					part2NoConst = (Multiplication) part2;
				}
						
				else {
					isIdRemarquable = false;
					break globalCond;
				}
			}
			
			if(part1NoConst instanceof Puissance && part1NoConst.getEaLeft() instanceof VariableSymbolique && part1NoConst.getEaRight().equals(new ConstEntiere(2))
					&& part3NoConst instanceof Puissance && part3NoConst.getEaLeft() instanceof VariableSymbolique && part3NoConst.getEaRight().equals(new ConstEntiere(2))
					&& part2NoConst instanceof Multiplication && part2NoConst.equals(new Multiplication(part1NoConst.getEaLeft(), part3NoConst.getEaLeft()))) {
				
				VariableSymbolique varA = (VariableSymbolique) part1NoConst.getEaLeft();
				VariableSymbolique varB = (VariableSymbolique) part3NoConst.getEaLeft();
				
				OperationBinaire operation1;
				Puissance operation2;
				
				if(!milieuNeg) {
					operation1 = new Addition(varA, varB);
				}
				
				else {
					operation1 = new Soustraction(varA, varB);
				}
				
				operation2 = new Puissance(operation1, new ConstEntiere(2));
				
				if(!constanteIdentite.equals(new ConstEntiere(1))) {
					idRemarquable = new Multiplication(constanteIdentite, operation2);
				}
				
				else {
					idRemarquable = operation2;
				}
			}
			
			else {
				isIdRemarquable = false;
				break globalCond;
			}
		}
		
		else {
			isIdRemarquable = false;
		}
		
		return (isIdRemarquable) ? idRemarquable : this;
	}
	
	@Override
	public String toString() {
		return eaLeft.toString() + "+" + eaRight.toString();
	}
}