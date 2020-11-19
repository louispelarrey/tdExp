package td3;

public class Pi extends ConstSymbolique {
	
	private static final String SYMBOLEPI = "π";
	private static final double VALEURPI = Math.PI;
	
	public Pi() {
		super(SYMBOLEPI, VALEURPI);
	}

	@Override
	public ExpressionArithmetique clone() {
		return new Pi();
	}	

}
