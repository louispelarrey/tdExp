package td3;

public abstract class OperationBinaire implements ExpressionArithmetique {
	protected ExpressionArithmetique eaLeft;
	protected ExpressionArithmetique eaRight;

	public OperationBinaire(ExpressionArithmetique eaLeft, ExpressionArithmetique eaRight) {
		this.eaLeft = eaLeft;
		this.eaRight = eaRight;
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
	

	//constante symbolique
	protected ExpressionArithmetique simplifieConstSymbolique() {
		return this;
	}
	
	/*protected ExpressionArithmetique simplifie(ConstEntiere gauche, ConstanteSymbolique droite) {
		return this;
	}
	
	protected ExpressionArithmetique simplifie(ConstanteSymbolique gauche, ConstEntiere droite) {
		return this;
	}
	
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, ConstanteSymbolique droite) {
		return this;
	}
	
	protected ExpressionArithmetique simplifie(ConstanteSymbolique gauche, ConstRationnelle droite) {
		return this;
	}*/
	
	//communes a variable symb et const symb
	protected ExpressionArithmetique simplifie(VariableSymbolique gauche, ConstanteSymbolique droite) {
		return this;
	}
	
	protected ExpressionArithmetique simplifie(ConstanteSymbolique gauche, VariableSymbolique droite) {
		return this;
	}
	
	//variable symbolique
	protected ExpressionArithmetique simplifie(VariableSymbolique gauche, VariableSymbolique droite) {
		return this;
	}
	
	protected ExpressionArithmetique simplifie(ConstEntiere gauche, VariableSymbolique droite) {
		return this;
	}
	
	protected ExpressionArithmetique simplifie(VariableSymbolique gauche, ConstEntiere droite) {
		return this;
	}
	
	protected ExpressionArithmetique simplifie(ConstRationnelle gauche, VariableSymbolique droite) {
		return this;
	}
	
	protected ExpressionArithmetique simplifie(VariableSymbolique gauche, ConstRationnelle droite) {
		return this;
	}
	
	
	

	@Override
	public ExpressionArithmetique simplifier() {

		ExpressionArithmetique res;
		this.eaLeft = this.eaLeft.simplifier();
		this.eaRight = this.eaRight.simplifier();
		
		if (this.eaLeft instanceof ConstEntiere && this.eaRight instanceof ConstEntiere) {
			ConstEntiere gauche = (ConstEntiere) this.eaLeft;
			ConstEntiere droite = (ConstEntiere) this.eaRight;
			res = simplifie(gauche, droite);
			
		} else if (this.eaLeft instanceof ConstRationnelle && this.eaRight instanceof ConstRationnelle) {
			ConstRationnelle gauche = (ConstRationnelle) this.eaLeft;
			ConstRationnelle droite = (ConstRationnelle) this.eaRight;

			res = simplifie(gauche, droite);

		} else if (this.eaLeft instanceof ConstRationnelle && this.eaRight instanceof ConstEntiere){
			ConstRationnelle gauche = (ConstRationnelle) this.eaLeft;
			ConstEntiere droite = (ConstEntiere) this.eaRight;

			res = simplifie(gauche, droite);

		} else if (this.eaLeft instanceof ConstEntiere && this.eaRight instanceof ConstRationnelle) {
			ConstEntiere gauche = (ConstEntiere) this.eaLeft;
			ConstRationnelle droite = (ConstRationnelle) this.eaRight;

			res = simplifie(gauche, droite);

		} else if (this.eaLeft instanceof ConstanteSymbolique || this.eaRight instanceof ConstanteSymbolique) {
			
			res = simplifieConstSymbolique();										//commencement const symb
			
		} 
		
		
		/*else if (this.eaLeft instanceof ConstanteSymbolique && this.eaRight instanceof ConstanteSymbolique) {
			ConstanteSymbolique gauche = (ConstanteSymbolique) this.eaLeft;
			ConstanteSymbolique droite = (ConstanteSymbolique) this.eaRight;

			res = simplifie(gauche, droite);															//commencement const symb
			
		} else if (this.eaLeft instanceof ConstEntiere && this.eaRight instanceof ConstanteSymbolique) {
			ConstEntiere gauche = (ConstEntiere) this.eaLeft;
			ConstanteSymbolique droite = (ConstanteSymbolique) this.eaRight;

			res = simplifie(gauche, droite);
			
		} else if (this.eaLeft instanceof ConstanteSymbolique && this.eaRight instanceof ConstEntiere) {
			ConstanteSymbolique gauche = (ConstanteSymbolique) this.eaLeft;
			ConstEntiere droite = (ConstEntiere) this.eaRight;

			res = simplifie(gauche, droite);
			
		} else if (this.eaLeft instanceof ConstRationnelle && this.eaRight instanceof ConstanteSymbolique) {
			ConstRationnelle gauche = (ConstRationnelle) this.eaLeft;
			ConstanteSymbolique droite = (ConstanteSymbolique) this.eaRight;

			res = simplifie(gauche, droite);
			
		} else if (this.eaLeft instanceof ConstanteSymbolique && this.eaRight instanceof ConstRationnelle) {
			ConstanteSymbolique gauche = (ConstanteSymbolique) this.eaLeft;
			ConstRationnelle droite = (ConstRationnelle) this.eaRight;

			res = simplifie(gauche, droite);
			
		} else if (this.eaLeft instanceof VariableSymbolique && this.eaRight instanceof ConstanteSymbolique) {
			VariableSymbolique gauche = (VariableSymbolique) this.eaLeft;
			ConstanteSymbolique droite = (ConstanteSymbolique) this.eaRight;

			res = simplifie(gauche, droite);
			
		} else if (this.eaLeft instanceof ConstanteSymbolique && this.eaRight instanceof VariableSymbolique) {
			ConstanteSymbolique gauche = (ConstanteSymbolique) this.eaLeft;
			VariableSymbolique droite = (VariableSymbolique) this.eaRight;

			res = simplifie(gauche, droite);
			
		}*/

		else {
			res = this;
		}

		return res;

	}

}
