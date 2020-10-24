package poo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import td3.Addition;
import td3.ConstEntiere;
import td3.ConstRationnelle;
import td3.ConstSymbolique;
import td3.Cos;
import td3.Division;
import td3.ExpressionArithmetique;
import td3.Ln;
import td3.Multiplication;
import td3.Puissance;
import td3.RacineCarre;
import td3.Sin;
import td3.Soustraction;
import td3.VariableSymbolique;

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
		
		
		System.out.println(times.calculer());
		assertEquals(550/17.0, times.calculer(),0.00001);
		

		//(1/4 + 3/4) + x = 
		//question 7
		//assertTrue(plusSpeDeux.equals(question));
		//assertFalse(plusSpeDeux.equals(minus));
		
		 
	}
	
	@Test
	public void testOpUnaire(){
		ExpressionArithmetique neuf = new ConstEntiere(9);
		ExpressionArithmetique pui = new Puissance(neuf, neuf);
		ExpressionArithmetique racine = new RacineCarre(neuf);
		ExpressionArithmetique ln = new Ln(neuf);
		ExpressionArithmetique cos = new Cos(neuf);
		ExpressionArithmetique sin = new Sin(neuf);
		
		assertEquals(Math.pow(9,9), pui.calculer(),0.00001);
		assertEquals(3, racine.calculer(),0.00001);
		assertEquals(2.197224577, ln.calculer(),0.00001);
		assertEquals(0.412118, sin.calculer(),0.00001);
		assertEquals(-0.91113, cos.calculer(),0.00001);

	}
	
	@Test
	public void testSimplifierVarSymb(){
		ExpressionArithmetique dr = new ConstRationnelle(1, 2);
		ExpressionArithmetique br = new ConstRationnelle(1, 2);
		ExpressionArithmetique sy = new VariableSymbolique("x");

		ExpressionArithmetique plu = new Addition(dr, br);
		ExpressionArithmetique question = new Addition(plu, sy);
		
		//question 5
		System.out.println(question.simplifier());
		
		//remplacer par un assert de String
	}
	
	@Test
	public void testConstSymb() {
		//question 6
		ExpressionArithmetique un = new ConstEntiere(1);
		ExpressionArithmetique pi = new ConstSymbolique("π");
		ExpressionArithmetique piPlusUn = new Addition(pi, un);
		
		assertEquals(4.1416, piPlusUn.calculer(),0.00001);	
	}
	
	@Test
	public void testVarSymbWithValue() {
		//question 8	
		ExpressionArithmetique un = new ConstEntiere(1);
		ExpressionArithmetique x = new VariableSymbolique("x");
		
		assertEquals(1, x.calculer(Collections.singletonMap((VariableSymbolique) x, un)), 0.00001);
		
		
		
	}
	
	@Test(expected = RuntimeException.class) //changer par une class perso
	public void testExceptionVarSymb() {
		ExpressionArithmetique x = new VariableSymbolique("x");
		
		double error = x.calculer(); 
	}
}
