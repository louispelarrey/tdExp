package td3;

import java.util.Map;

public class ExpSomme extends Expand {
	private Multiplication produit;
	
	public ExpSomme(Multiplication produit, int minimum, int maximum) {
		if(produit.getEaLeft() instanceof VariableSymbolique && produit.getEaRight() instanceof ExpressionArithmetique) {
			this.produit = produit;
		}
		
		else throw new IllegalArgumentException("Pas une multiplication d'une variable symbolique par un expression arithmétique");
	}
	
	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		/*if(map instanceof Map<VariableSymboliqueIndicee, ExpressionArithmetique>)
		for(Map.Entry<VariableSymboliqueIndicee, ExpressionArithmetique> variable : map.entrySet()) {
			
		}*/
		
		return null;
	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return 0;
	}
}
