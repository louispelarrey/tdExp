package poo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import td3.Addition;
import td3.ConstEntiere;
import td3.ConstRationnelle;
import td3.ConstanteSymbolique;
import td3.ExpressionArithmetique;
import td3.Multiplication;
import td3.Soustraction;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void simpleSum() {

		ConstEntiere neuf = new ConstEntiere(9);
		ConstEntiere deux = new ConstEntiere(2);
		Addition racine = new Addition(neuf, deux);

		assertEquals(11, ((ConstEntiere) racine.simplifier()).getEntier());

	}

	@Test
	public void classExample() {

		ExpressionArithmetique neuf = new ConstEntiere(9);
		ExpressionArithmetique deux = new ConstEntiere(2);
		ExpressionArithmetique trois = new ConstEntiere(3);
		ExpressionArithmetique cr = new ConstRationnelle(1, 17);

		ExpressionArithmetique plus = new Addition(neuf, deux);
		ExpressionArithmetique minus = new Soustraction(trois, cr);
		ExpressionArithmetique times = new Multiplication(plus, minus);

		

		assertEquals(550, ((ConstRationnelle) times.simplifier()).getNumerateur());
		assertEquals(17, ((ConstRationnelle) times.simplifier()).getDenominateur());

	}
	
	@Test
	public void exempleCalculer() {

		ExpressionArithmetique neuf = new ConstEntiere(9);
		ExpressionArithmetique deux = new ConstEntiere(2);
		ExpressionArithmetique trois = new ConstEntiere(3);
		ExpressionArithmetique cr = new ConstRationnelle(1, 17);

		ExpressionArithmetique plus = new Addition(neuf, deux);
		ExpressionArithmetique minus = new Soustraction(trois, cr);
		ExpressionArithmetique times = new Multiplication(plus, minus);

		ExpressionArithmetique results = new ConstRationnelle(550, 17);

		
		assertEquals(550/17.0, times.calculer(),0.00001);

	}
	
	@Test
	public void constSymb() {

		ConstanteSymbolique x = new ConstanteSymbolique("x", 9.88);
		ConstEntiere deux = new ConstEntiere(2);
		Addition racine = new Addition(x, deux);
		
		System.out.println(racine.toString());//test toString() a enlever de addition et mettre dans operation binaire

		assertEquals(11.88, racine.simplifier().calculer(),0.00001);

	}
	
	@Test
	public void constSymbMultiplication() {

		ConstanteSymbolique x = new ConstanteSymbolique("x", 9.88);
		ConstEntiere deux = new ConstEntiere(2);
		Multiplication racine = new Multiplication(x, deux);
		
		//System.out.println(racine.toString());//test toString()

		assertEquals(19.76, racine.simplifier().calculer(),0.00001); //simplifier pas idnsipensable car constaSymb non simpl

	}
	
	@Test
	public void constSymbComplexe() {

		ConstanteSymbolique x = new ConstanteSymbolique("x", 9.88);
		ExpressionArithmetique deux = new ConstEntiere(2);
		ExpressionArithmetique trois = new ConstEntiere(3);
		ExpressionArithmetique cr = new ConstRationnelle(1, 17);

		ExpressionArithmetique plus = new Addition(deux, x); //11.88
		ExpressionArithmetique minus = new Soustraction(trois, cr); //0.11
		ExpressionArithmetique times = new Multiplication(plus, minus);

		ExpressionArithmetique results = new ConstRationnelle(594, 17);

		
		assertEquals(594/17.0, times.calculer(),0.00001);
		
	}
}
