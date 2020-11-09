package td3;

import java.util.Map;

public abstract class OperationBinaire extends ExpressionArithmetique {
	protected ExpressionArithmetique eaLeft;
	protected ExpressionArithmetique eaRight;
	
	public OperationBinaire(ExpressionArithmetique eaLeft, ExpressionArithmetique eaRight) {
		this.eaLeft = eaLeft;
		this.eaRight = eaRight;
	}

	
	public ExpressionArithmetique getEaLeft() {
		return eaLeft;
	}

	public ExpressionArithmetique getEaRight() {
		return eaRight;
	}
	

	
	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, ExpressionArithmetique droite) {
		return this;
	}

	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstEntiere droite) {
		return this;
	}

	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstRationnelle droite) {
		return this;
	}

	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstEntiere droite) {
		return this;
	}

	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstRationnelle droite) {
		return this;
	}	
	
	
	
	protected ExpressionArithmetique simplifie(ExpressionArithmetique gauche, ConstEntiere droite) {
		return this;
	}
	
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, ExpressionArithmetique droite) {
		return this;
	}
		
	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {

		ExpressionArithmetique res;
		this.eaLeft = this.eaLeft.simplifier(map);
		this.eaRight = this.eaRight.simplifier(map);
		

		if (this.eaLeft instanceof ConstEntiere && this.eaRight instanceof ConstEntiere) {
			ConstEntiere gauche = (ConstEntiere) this.eaLeft;
			ConstEntiere droite = (ConstEntiere) this.eaRight;
			
			res = simplifie(gauche, droite);
		} else if (this.eaLeft instanceof ConstRationnelle && this.eaRight instanceof ConstRationnelle) {
			ConstRationnelle gauche = (ConstRationnelle) this.eaLeft;
			ConstRationnelle droite = (ConstRationnelle) this.eaRight;

			res = simplifie(gauche, droite);
		} else if (this.eaLeft instanceof ConstRationnelle && this.eaRight instanceof ConstEntiere) {
			ConstRationnelle gauche = (ConstRationnelle) this.eaLeft;
			ConstEntiere droite = (ConstEntiere) this.eaRight;

			res = simplifie(gauche, droite);
		} else if (this.eaLeft instanceof ConstEntiere && this.eaRight instanceof ConstRationnelle) {
			ConstEntiere gauche = (ConstEntiere) this.eaLeft;
			ConstRationnelle droite = (ConstRationnelle) this.eaRight;

			res = simplifie(gauche, droite);
		} else if (this.eaLeft instanceof ExpressionArithmetique && this.eaRight instanceof ConstEntiere) {
				ExpressionArithmetique gauche = this.eaLeft;
				ConstEntiere droite = (ConstEntiere) this.eaRight;

				res = simplifie(gauche, droite);
		 }
		else {
			res = this;
		}

		return res;

	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationBinaire other = (OperationBinaire) obj;
		return (eaLeft.simplifier().equals(other.eaLeft.simplifier()) && eaRight.simplifier().equals(other.eaRight.simplifier()));
	}
}
