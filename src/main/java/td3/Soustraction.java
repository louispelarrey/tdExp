package td3;

import java.util.Map;

public class Soustraction extends OperationBinaire {

	public Soustraction(ExpressionArithmetique eaLeft, ExpressionArithmetique eaRight) {
		super(eaLeft, eaRight);

	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return this.eaLeft.calculer(map) - this.eaRight.calculer(map);
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstEntiere droite) {
		return new ConstRationnelle(gauche.getNumerateur() * droite.getEntier() - gauche.getDenominateur() * 1,
				1 * gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return new ConstRationnelle(
				gauche.getNumerateur() * droite.getDenominateur() - gauche.getDenominateur() * droite.getNumerateur(),
				droite.getDenominateur() * gauche.getDenominateur()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstEntiere droite) {
		return new ConstEntiere(gauche.getEntier() - droite.getEntier()).simplifier();
	}

	@Override
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {
		return new ConstRationnelle(droite.getDenominateur() * gauche.getEntier() - droite.getNumerateur() * 1,
				1 * droite.getDenominateur()).simplifier();
	}
	
	@Override
	public String toString() {
		return eaLeft.toString() + "-" + eaRight.toString();
	}
	
	@Override
	protected boolean isNeutre(ExpressionArithmetique ea) {
		return (ea instanceof ConstEntiere && ((ConstEntiere)ea).getEntier() == 0);
	}

	public ExpressionArithmetique idRemarquable() {
		ExpressionArithmetique constanteIdentite = new ConstEntiere(1);
		boolean isIdRemarquable = true;
		
		Puissance part1NoConst = null, part2NoConst = null;
		Multiplication idRemarquable = null;
		
		if(this.eaLeft instanceof Multiplication && this.eaRight instanceof Multiplication) {
			Multiplication part1 = (Multiplication) this.eaLeft;
			Multiplication part2 = (Multiplication) this.eaRight;
			
			if((part1.getEaLeft() instanceof ConstEntiere || part1.getEaLeft() instanceof ConstRationnelle)
					&& part1.getEaLeft().equals(part2.getEaLeft())) {
				constanteIdentite = part1.getEaLeft();
				
				if(part1.getEaRight() instanceof Puissance && ((Puissance) part1.getEaRight()).getEaLeft() instanceof VariableSymbolique &&
						((Puissance) part1.getEaRight()).getEaRight().equals(new ConstEntiere(2)) &&
						part2.getEaRight() instanceof Puissance && ((Puissance) part2.getEaRight()).getEaLeft() instanceof VariableSymbolique && 
						((Puissance) part2.getEaRight()).getEaRight().equals(new ConstEntiere(2))) {
					part1NoConst = (Puissance) part1.getEaRight();
					part2NoConst = (Puissance) part2.getEaRight();
				}
				
				else {
					isIdRemarquable = false;
				}
			}
			
			else {
				isIdRemarquable = false;
			}
		}
		
		else if(this.eaLeft instanceof Puissance && ((Puissance) this.eaLeft).getEaLeft() instanceof VariableSymbolique &&
				((Puissance) this.eaLeft).getEaRight().equals(new ConstEntiere(2)) &&
				this.eaRight instanceof Puissance && ((Puissance) this.eaRight).getEaLeft() instanceof VariableSymbolique && 
				((Puissance) this.eaRight).getEaRight().equals(new ConstEntiere(2))) {
			
			part1NoConst = (Puissance) this.eaLeft;
			part2NoConst = (Puissance) this.eaRight;
		}
			
		else {
			isIdRemarquable = false;
		}
		
		if(isIdRemarquable) {
			VariableSymbolique varA = (VariableSymbolique) part1NoConst.getEaLeft();
			VariableSymbolique varB = (VariableSymbolique) part2NoConst.getEaLeft();
			
			Addition addGauche = new Addition(varA, varB);
			Soustraction sousDroite = new Soustraction(varA, varB);
			Multiplication operation3 = new Multiplication(addGauche, sousDroite);
			
			if(!constanteIdentite.equals(new ConstEntiere(1))) {
				idRemarquable = new Multiplication(constanteIdentite, operation3);
			}
			
			else {
				idRemarquable = operation3;
			}
		}
		
		return (isIdRemarquable) ? idRemarquable : this;
	}
}
