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
		return (ea.equals(this.eaRight) && ea.equals(new ConstEntiere(0)));//si 0 est à droite
	}

	@Override
	public ExpressionArithmetique deriver() {
		return new Soustraction(this.eaLeft.deriver(), this.eaRight.deriver()).simplifier();
	}
	
	
	public ExpressionArithmetique idRemarquable() {
		ExpressionArithmetique constanteIdentite = new ConstEntiere(1);
		ConstEntiere deux = new ConstEntiere(2);
		
		Puissance part1NoConst, part2NoConst;
		
		/* Cas n°1 : la constante devant chaque est différente de 1, on vérifie à chaque fois qu'à droite de la constante on a bien une 
		 * puissance d'une variable symbolique au second degré et que les constantes données sont bien égales.
		 */
		
		if(this.eaLeft instanceof Multiplication && this.eaRight instanceof Multiplication) {
			Multiplication part1 = (Multiplication) this.eaLeft;
			Multiplication part2 = (Multiplication) this.eaRight;
			
			if(part1.getEaLeft() instanceof ConstReelle && part1.getEaLeft().equals(part2.getEaLeft())) {
				constanteIdentite = part1.getEaLeft();
				
				if(part1.getEaRight() instanceof Puissance && ((Puissance) part1.getEaRight()).getEaLeft() instanceof VariableSymbolique &&
						((Puissance) part1.getEaRight()).getEaRight().equals(deux) &&
						part2.getEaRight() instanceof Puissance && ((Puissance) part2.getEaRight()).getEaLeft() instanceof VariableSymbolique && 
						((Puissance) part2.getEaRight()).getEaRight().equals(deux)) {
					part1NoConst = (Puissance) part1.getEaRight();
					part2NoConst = (Puissance) part2.getEaRight();
				}
				
				else return this;
			}
			
			else return this;
		}
		
		/* Cas n°2 : la constante est égale à 1, on vérifie simplement qu'il s'agit bien de puissances de variables symboliques et que 
		 * la puissance est bien 2.
		 */
		
		else if(this.eaLeft instanceof Puissance && ((Puissance) this.eaLeft).getEaLeft() instanceof VariableSymbolique &&
				((Puissance) this.eaLeft).getEaRight().equals(new ConstEntiere(2)) &&
				this.eaRight instanceof Puissance && ((Puissance) this.eaRight).getEaLeft() instanceof VariableSymbolique && 
				((Puissance) this.eaRight).getEaRight().equals(new ConstEntiere(2))) {
			
			part1NoConst = (Puissance) this.eaLeft;
			part2NoConst = (Puissance) this.eaRight;
		}
			
		else return this;
		
		// Dans les 2 cas, on finit avec les objets part1-2NoConst, on remultiplie si nécessaire à la fin

		VariableSymbolique varA = (VariableSymbolique) part1NoConst.getEaLeft();
		VariableSymbolique varB = (VariableSymbolique) part2NoConst.getEaLeft();
		
		Multiplication operation3 = new Multiplication(new Addition(varA, varB), new Soustraction(varA, varB));
		
		return (!constanteIdentite.equals(new ConstEntiere(1))) ? new Multiplication(constanteIdentite, operation3) : operation3;

	}
	
	@Override
	public ExpressionArithmetique clone() {
		return new Soustraction(eaLeft.clone(), eaRight.clone());
	}
}
