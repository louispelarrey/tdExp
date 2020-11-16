
package td3;

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
	public ExpressionArithmetique deriver() {
		return new Addition(this.eaLeft.deriver(), this.eaRight.deriver()).simplifier();
	}


	private Addition associativite() {

		ExpressionArithmetique eaLeftSimp = this.eaLeft.simplifier();
		ExpressionArithmetique eaRightSimp = this.eaRight.simplifier();
		Addition eaCast;
		int action = 0;
		

		if(eaLeftSimp instanceof Addition && eaRightSimp instanceof ConstReelle) {
			eaCast = (Addition) eaLeftSimp;
			
			if(eaCast.getEaLeft() instanceof Symbolique && eaCast.getEaRight() instanceof ConstReelle) action = 1;
			else if(eaCast.getEaRight() instanceof Symbolique && eaCast.getEaLeft() instanceof ConstReelle) action = 2;
		}
		
		else if(eaRightSimp instanceof Addition && eaLeftSimp instanceof ConstReelle) {
			eaCast = (Addition) eaRightSimp;
			
			if(eaCast.getEaLeft() instanceof Symbolique && eaCast.getEaRight() instanceof ConstReelle) action = 3;
			else if(eaCast.getEaRight() instanceof Symbolique && eaCast.getEaLeft() instanceof ConstReelle) action = 4;
		}
		
		else if(eaLeftSimp instanceof Addition && eaRightSimp instanceof Symbolique) {
			eaCast = (Addition) eaLeftSimp;
			
			if(eaCast.getEaLeft() instanceof Symbolique && eaCast.getEaRight() instanceof ConstReelle) action = 2;
			else if(eaCast.getEaRight() instanceof Symbolique && eaCast.getEaLeft() instanceof ConstReelle) action = 1;
		}
		
		else if(eaRightSimp instanceof Addition && eaLeftSimp instanceof Symbolique) {
			eaCast = (Addition) eaRightSimp;
			
			if(eaCast.getEaLeft() instanceof Symbolique && eaCast.getEaRight() instanceof ConstReelle) action = 4;
			else if(eaCast.getEaRight() instanceof Symbolique && eaCast.getEaLeft() instanceof ConstReelle) action = 3;
		}
		
		else return this;
		
		ExpressionArithmetique newEaLeft, newEaRight;
		
		if(action == 1) {
			newEaLeft = new Addition(eaCast.getEaRight(), eaRightSimp).simplifier();
			newEaRight = eaCast.getEaLeft();
		} else if(action == 2) {
			newEaLeft = new Addition(eaCast.getEaLeft(), eaRightSimp).simplifier();
			newEaRight = eaCast.getEaRight();
		} else if(action == 3) {
			newEaLeft = new Addition(eaCast.getEaRight(), eaLeftSimp).simplifier();
			newEaRight = eaCast.getEaLeft();
		} else if(action == 4) {
			newEaLeft = new Addition(eaCast.getEaLeft(), eaLeftSimp).simplifier();
			newEaRight = eaCast.getEaRight();
		}
		else return this;
		
		return new Addition(newEaLeft, newEaRight);
	}
	
	public ExpressionArithmetique idRemarquable() {
		boolean isIdRemarquable = true;
		
		ExpressionArithmetique part1 = null, part2 = null, part3 = null, idRemarquable = null;
		
		/* Pour chaque, on vérifie si on a bien une addition à gauche ou à droite avec un paramètre isolé, puis si on doit inverser pour 
		 * simplifier en ConstReelle + un symbole. La variable action permet de raccourcir car certaines "actions" sont les mêmes.
		 */
		
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
			ExpressionArithmetique constanteIdentite = new ConstEntiere(1);
			ExpressionArithmetique constanteMilieu = new ConstEntiere(2);
			ExpressionArithmetique constanteMilieuNeg = new Multiplication(constanteMilieu, new ConstEntiere(-1)).simplifier();
			
			boolean milieuNeg;
			
			Puissance part1NoConst, part3NoConst;
			Multiplication part2NoConst;
			
			if(part1 instanceof Multiplication && ((Multiplication) part1).getEaLeft() instanceof ConstReelle && 
					((Multiplication) part1).getEaRight() instanceof Puissance && ((Multiplication) part3).getEaRight() instanceof Puissance)  {
				constanteIdentite = ((Multiplication) part1).getEaLeft();
				constanteMilieu = new Multiplication(constanteIdentite, new ConstEntiere(2)).simplifier();
				constanteMilieuNeg = new Multiplication(constanteMilieu, new ConstEntiere(-1)).simplifier();
				
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

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique simplified = super.simplifier(map);
		if (simplified instanceof Addition) {
			Addition simplifiedAdd = ((Addition) simplified).associativite();
			if (simplifiedAdd.eaLeft.equals(new ConstEntiere(0))) {
				return this.eaRight;
			} else if (simplifiedAdd.eaRight.equals(new ConstEntiere(0))) {
				return this.eaLeft;
			} else {
				return simplifiedAdd;
			}
		}
		return simplified;
	}
}