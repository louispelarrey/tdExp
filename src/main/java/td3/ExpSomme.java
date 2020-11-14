package td3;

import java.util.Map;

public class ExpSomme extends ExpressionArithmetique {
	
	private final OperationBinaire exp;
	private ConstEntiere i;
	private int max;
	
	public ExpSomme(OperationBinaire exp, ConstEntiere i, int max) {
		this.exp = exp;
		this.i = i;
		this.max = max;
	}

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		VariableSymbolique i = new VariableSymbolique("i");
		map.put(i, this.i);
		ExpressionArithmetique e;
		
		if(exp instanceof Addition)
			e = new Addition(exp.eaLeft, exp.eaRight).simplifier(map);
		else if(exp instanceof Soustraction)
			e = new Soustraction(exp.eaLeft, exp.eaRight).simplifier(map);
		else if(exp instanceof Multiplication)
			e = new Multiplication(exp.eaLeft, exp.eaRight).simplifier(map);
		else
			e = new Division(exp.eaLeft, exp.eaRight).simplifier(map);
			
		for(int j = this.i.getEntier()+1; j<=max; j++) {	
			map.put(i, new ConstEntiere(j));
			e = new Addition(e, new Multiplication(exp.eaLeft, exp.eaRight).simplifier(map));			
		}
		
		return e;
	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

	
}
