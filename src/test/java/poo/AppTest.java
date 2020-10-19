package poo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		ExpressionArithmetique pui = new Puissance(plus, minus);
		ExpressionArithmetique racine = new RacineCarre(neuf);
		ExpressionArithmetique ln = new Ln(neuf);
		ExpressionArithmetique cos = new Cos(neuf);
		ExpressionArithmetique sin = new Sin(neuf);
		
		
		System.out.println(times.calculer());
		assertEquals(550/17.0, times.calculer(),0.00001);
		assertEquals(1155.89839, pui.calculer(),0.00001);
		assertEquals(3, racine.calculer(),0.00001);
		assertEquals(2.197224577, ln.calculer(),0.00001);
		assertEquals(0.412118, sin.calculer(),0.00001);
		assertEquals(-0.91113, cos.calculer(),0.00001);

		
		ExpressionArithmetique dr = new ConstRationnelle(1, 3);
		ExpressionArithmetique br = new ConstRationnelle(3, 4);
		ExpressionArithmetique sy = new VariableSymbolique("x");

		ExpressionArithmetique plu = new Addition(dr, br);
		ExpressionArithmetique question = new Addition(plu, sy);
		
		//question 5
		System.out.println(question.simplifier());
		
		//question 6
		ExpressionArithmetique un = new ConstEntiere(1);
		ExpressionArithmetique plusSpe = new Addition(br, un);
		assertEquals(1.7500, plusSpe.calculer(),0.00001);
		
		ExpressionArithmetique pi = new ConstSymbolique("π");
		ExpressionArithmetique plusSpeDeux = new Addition(pi, un);
		assertEquals(4.1416, plusSpeDeux.calculer(),0.00001);

		//question 7
		assertTrue(plusSpeDeux.equals(question));
		assertFalse(plusSpeDeux.equals(minus));
		
		//question 8
		ExpressionArithmetique xUn = new VariableSymbolique("x", 1);
		ExpressionArithmetique yUn = new VariableSymbolique("y", 2);
		
		ExpressionArithmetique add = new Addition(yUn, un);
		ExpressionArithmetique divSymbol = new Division(un, add);
		ExpressionArithmetique addSymbol = new Addition(un, divSymbol);

		System.out.println(addSymbol.calculer());
	}
}
