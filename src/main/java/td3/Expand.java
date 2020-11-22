package td3;

import java.util.HashMap;
import java.util.Map;

public abstract class Expand extends ExpressionArithmetique{
	
	protected final VariableSymbolique n;
	protected int i;
	protected int max;
	protected final OperationBinaire exp;

	public Expand(VariableSymbolique n, int i, int max, OperationBinaire exp) {
		this.n = n;
		this.i = i;
		this.max = max;
		this.exp = exp;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public VariableSymbolique getN() {
		return n;
	}

	public OperationBinaire getExp() {
		return exp;
	}

	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return expand(map).simplifier();
	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		return expand(map).calculer();
	}
	
	public ExpressionArithmetique expand() {
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); //map vide
		return expand(map);
	}
	
	public abstract ExpressionArithmetique expand(Map<VariableSymbolique, ExpressionArithmetique> map);	
	
	
}