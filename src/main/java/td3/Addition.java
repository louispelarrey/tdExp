
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
	
	protected ExpressionArithmetique simplifie(Matrice gauche, Matrice droite) {
		if(gauche.getColumn() != droite.getColumn() || gauche.getRow() != droite.getRow()) {
            return this;
        } 
        else {
        	return gauche.somme(droite);
        }
	}

	@Override
	public ExpressionArithmetique deriver() {
		return new Addition(this.eaLeft.deriver(), this.eaRight.deriver()).simplifier();
	}
	
	@Override
	protected boolean isNeutre(ExpressionArithmetique ea) {
		return (ea.equals(new ConstEntiere(0)));
	}
	
	private ExpressionArithmetique associativite() {

		ExpressionArithmetique eaLeftSimp = this.eaLeft.simplifier();
		ExpressionArithmetique eaRightSimp = this.eaRight.simplifier();
		Addition eaCast;
		int action = 0;
		
		/* Pour chaque, on vérifie si on a bien une Addition à gauche ou à droite avec un paramètre isolé, puis si on doit inverser pour 
		 * simplifier en ConstReelle * un symbole. La variable action permet de raccourcir car certaines "actions" sont les mêmes.
		 */

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
		
		ExpressionArithmetique newEaLeft;
		ExpressionArithmetique newEaRight;
		
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
		
		return (new Addition(newEaLeft, newEaRight)).simplifier();
	}
	
	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		ExpressionArithmetique simplified = super.simplifier(map);
		if (simplified instanceof Addition) {
			Addition simplifiedAdd = (Addition) simplified;	
			if(simplifiedAdd.eaLeft instanceof Addition || simplifiedAdd.eaRight instanceof Addition) {
				return simplifiedAdd.associativite();
			}
			else if(simplifiedAdd.eaLeft instanceof Matrice && simplifiedAdd.eaRight instanceof Matrice) {
				Matrice gauche = (Matrice) this.eaLeft;
				Matrice droite = (Matrice) this.eaRight;

				return simplifie(gauche, droite);
			}
		}
		return simplified;
	}
	
	public ExpressionArithmetique idRemarquable() {
		ExpressionArithmetique idRemarquable;
		ExpressionArithmetique part1; 
		ExpressionArithmetique part2;
		ExpressionArithmetique part3;
		
		// On vérifie d'abord ou se trouve l'objet Addition, à droite ou à gauche pour isoler chacune des 3 parties de la potentielle identité
		
		if(this.eaLeft instanceof Addition) {
			part1 = ((Addition) this.eaLeft).getEaLeft();
			part2 = ((Addition) this.eaLeft).getEaRight();
			part3 = this.eaRight;
		}
		
		else if(this.eaRight instanceof Addition) {
			part1 = this.eaLeft;
			part2 = ((Addition) this.eaRight).getEaLeft();
			part3 = ((Addition) this.eaRight).getEaRight();
		}
		
		else return this;
		
		// Si la partie 2 n'est pas une Multiplication alors pas une identité remaruqable
		
		if(part2 instanceof Multiplication) {
			ExpressionArithmetique constanteIdentite = new ConstEntiere(1);
			ExpressionArithmetique constanteMilieu = new ConstEntiere(2);
			
			boolean milieuNeg = false;
			Puissance part1NoConst;
			Puissance part3NoConst;
			Multiplication part2NoConst;
			
			/* 
			 * Cas n°1 : chaque partie est multipliée par une constante (ou par son double pour la partie du milieu), on vérifie l'égalité à chaque fois
			 * On vérifie également que ces constantes sont bien multipliées par des puissances
			 */
			
			if(part1 instanceof Multiplication && ((Multiplication) part1).getEaLeft() instanceof ConstReelle && 
					((Multiplication) part1).getEaRight() instanceof Puissance && ((Multiplication) part3).getEaRight() instanceof Puissance)  {
				constanteIdentite = ((Multiplication) part1).getEaLeft();
				constanteMilieu = new Multiplication(constanteIdentite, new ConstEntiere(2)).simplifier();
				
				if(!(part3 instanceof Multiplication) || !constanteIdentite.equals(((Multiplication) part3).getEaLeft())) return this;
				
				else {
					if(((Multiplication) part2).getEaLeft().equals(constanteMilieu)) milieuNeg = false;
					
					else if(((Multiplication) part2).getEaLeft().equals(new Multiplication(constanteMilieu, new ConstEntiere(-1)).simplifier())) milieuNeg = true;
					
					else return this;
					
					part1NoConst = (Puissance) ((Multiplication) part1).getEaRight();
					part2NoConst = (Multiplication) ((Multiplication) part2).getEaRight();
					part3NoConst = (Puissance) ((Multiplication) part3).getEaRight();
					
				}
			}
			
			// Cas n°2 : pas de puissance devant
			
			else {
				part1NoConst = (Puissance) part1;
				part3NoConst = (Puissance) part3;
				
				if(((Multiplication) part2).getEaLeft().equals(constanteMilieu)) milieuNeg = false;
				
				else if(((Multiplication) part2).getEaLeft().equals(new Multiplication(constanteMilieu, new ConstEntiere(-1)).simplifier())) milieuNeg = true;
						
				else return this;
				
				part2NoConst = (Multiplication) part2;
			}
			
			// On vérifie bien qu'il s'agit de puissances de VariablesSymboliques au degré 2
			
			if(part1NoConst instanceof Puissance && part1NoConst.getEaLeft() instanceof VariableSymbolique && part1NoConst.getEaRight().equals(new ConstEntiere(2))
					&& part3NoConst instanceof Puissance && part3NoConst.getEaLeft() instanceof VariableSymbolique && part3NoConst.getEaRight().equals(new ConstEntiere(2))
					&& part2NoConst instanceof Multiplication && part2NoConst.equals(new Multiplication(part1NoConst.getEaLeft(), part3NoConst.getEaLeft()))) {
				
				VariableSymbolique varA = (VariableSymbolique) part1NoConst.getEaLeft();
				VariableSymbolique varB = (VariableSymbolique) part3NoConst.getEaLeft();
				
				OperationBinaire operation1 = !milieuNeg ? new Addition(varA, varB) : new Soustraction(varA, varB);
				Puissance operation2 = new Puissance(operation1, new ConstEntiere(2));
				idRemarquable = !constanteIdentite.equals(new ConstEntiere(1)) ? new Multiplication(constanteIdentite, operation2) : operation2;
			}
			
			else return this;
		}
		
		else return this;
		
		return idRemarquable;
	}
	
	@Override
	public String toString() {
		return eaLeft.toString() + "+" + eaRight.toString();
	}

	@Override
	public ExpressionArithmetique clone() {
		return new Addition(eaLeft.clone(), eaRight.clone());
	}

}