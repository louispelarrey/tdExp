package td3;

public class E extends ConstSymbolique {
	
	private static final String SYMBOLEE = "e";
	private static final double VALEURE = Math.E;
	
	public E() {
		super(SYMBOLEE, VALEURE);
	}

	@Override
	public ExpressionArithmetique clone() {
		return new E();
	}
}
