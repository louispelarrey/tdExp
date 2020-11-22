
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
	
	private ExpressionArithmetique simplifie(Matrice gauche, Matrice droite) {
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
			
			if(eaCast.eaLeft instanceof Symbolique && eaCast.eaRight instanceof ConstReelle) action = 1;
			else if(eaCast.eaRight instanceof Symbolique && eaCast.eaLeft instanceof ConstReelle) action = 2;
		}
		
		else if(eaRightSimp instanceof Addition && eaLeftSimp instanceof ConstReelle) {
			eaCast = (Addition) eaRightSimp;
			
			if(eaCast.eaLeft instanceof Symbolique && eaCast.eaRight instanceof ConstReelle) action = 3;
			else if(eaCast.eaRight instanceof Symbolique && eaCast.eaLeft instanceof ConstReelle) action = 4;
		}
		
		else if(eaLeftSimp instanceof Addition && eaRightSimp instanceof Symbolique) {
			eaCast = (Addition) eaLeftSimp;
			
			if(eaCast.eaLeft instanceof Symbolique && eaCast.eaRight instanceof ConstReelle) action = 2;
			else if(eaCast.eaRight instanceof Symbolique && eaCast.eaLeft instanceof ConstReelle) action = 1;
		}
		
		else if(eaRightSimp instanceof Addition && eaLeftSimp instanceof Symbolique) {
			eaCast = (Addition) eaRightSimp;
			
			if(eaCast.eaLeft instanceof Symbolique && eaCast.eaRight instanceof ConstReelle) action = 4;
			else if(eaCast.eaRight instanceof Symbolique && eaCast.eaLeft instanceof ConstReelle) action = 3;
		}
		
		else return this;
		
		ExpressionArithmetique newEaLeft;
		ExpressionArithmetique newEaRight;
		
		switch (action) {
		case (1):
			newEaLeft = new Addition(eaCast.eaRight, eaRightSimp).simplifier();
			newEaRight = eaCast.eaLeft;
			break;
		case (2):
			newEaLeft = new Addition(eaCast.eaLeft, eaRightSimp).simplifier();
			newEaRight = eaCast.eaRight;
			break;
		case (3):
			newEaLeft = new Addition(eaCast.eaRight, eaLeftSimp).simplifier();
			newEaRight = eaCast.eaLeft;
			break;
		case (4):
			newEaLeft = new Addition(eaCast.eaLeft, eaLeftSimp).simplifier();
			newEaRight = eaCast.eaRight;
			break;
		default:
			return this;
		}
		
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
			part1 = ((Addition) this.eaLeft).eaLeft;
			part2 = ((Addition) this.eaLeft).eaRight;
			part3 = this.eaRight;
		}
		
		else if(this.eaRight instanceof Addition) {
			part1 = this.eaLeft;
			part2 = ((Addition) this.eaRight).eaLeft;
			part3 = ((Addition) this.eaRight).eaRight;
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
			
			if(part1 instanceof Multiplication && ((Multiplication) part1).eaLeft instanceof ConstReelle && 
					((Multiplication) part1).eaRight instanceof Puissance && ((Multiplication) part3).eaRight instanceof Puissance)  {
				constanteIdentite = ((Multiplication) part1).eaLeft;
				constanteMilieu = new Multiplication(constanteIdentite, new ConstEntiere(2)).simplifier();
				
				if(!(part3 instanceof Multiplication) || !constanteIdentite.equals(((Multiplication) part3).eaLeft)) return this;
				
				else {
					if(((Multiplication) part2).eaLeft.equals(constanteMilieu)) milieuNeg = false;
					
					else if(((Multiplication) part2).eaLeft.equals(new Multiplication(constanteMilieu, new ConstEntiere(-1)).simplifier())) milieuNeg = true;
					
					else return this;
					
					part1NoConst = (Puissance) ((Multiplication) part1).eaRight;
					part2NoConst = (Multiplication) ((Multiplication) part2).eaRight;
					part3NoConst = (Puissance) ((Multiplication) part3).eaRight;
					
				}
			}
			
			// Cas n°2 : pas de puissance devant
			
			else {
				part1NoConst = (Puissance) part1;
				part3NoConst = (Puissance) part3;
				
				if(((Multiplication) part2).eaLeft.equals(constanteMilieu)) milieuNeg = false;
				
				else if(((Multiplication) part2).eaLeft.equals(new Multiplication(constanteMilieu, new ConstEntiere(-1)).simplifier())) milieuNeg = true;
						
				else return this;
				
				part2NoConst = (Multiplication) part2;
			}
			
			// On vérifie bien qu'il s'agit de puissances de VariablesSymboliques au degré 2
			
			if(part1NoConst instanceof Puissance && part1NoConst.eaLeft instanceof VariableSymbolique && part1NoConst.eaRight.equals(new ConstEntiere(2))
					&& part3NoConst instanceof Puissance && part3NoConst.eaLeft instanceof VariableSymbolique && part3NoConst.eaRight.equals(new ConstEntiere(2))
					&& part2NoConst instanceof Multiplication && part2NoConst.equals(new Multiplication(part1NoConst.eaLeft, part3NoConst.eaLeft))) {
				
				VariableSymbolique varA = (VariableSymbolique) part1NoConst.eaLeft;
				VariableSymbolique varB = (VariableSymbolique) part3NoConst.eaLeft;
				
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