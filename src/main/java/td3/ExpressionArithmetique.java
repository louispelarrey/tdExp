package td3;

public interface ExpressionArithmetique<E> {
	public ExpressionArithmetique<?> simplifier();
	public E calculer();
}
