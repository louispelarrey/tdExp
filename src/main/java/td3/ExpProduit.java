package td3;

import java.util.Map;

public class ExpProduit extends Expand {
	
	public ExpProduit(VariableSymbolique n, int i, int max, OperationBinaire exp) {
		super(n, i, max, exp);
	}
	
	
	@Override
	public ExpressionArithmetique expand(Map<VariableSymbolique, ExpressionArithmetique> map) {
		map.put(n, new ConstEntiere(i));
		ExpressionArithmetique e = exp.clone().simplifier(map);
		for(int j = i+1; j<=max; j++) {	
				map.put(n, new ConstEntiere(j));
				e = new Multiplication(e, exp.clone().simplifier(map));	
		}
		
		return e;
	}

	@Override
	public ExpressionArithmetique clone() {
		// TODO Auto-generated method stub
		return null;
	}



	
}
