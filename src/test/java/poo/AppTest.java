package poo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import td3.Addition;
import td3.ConstEntiere;
import td3.ConstRationnelle;
import td3.ConstSymbolique;
import td3.Cos;
import td3.Division;
import td3.ExpressionArithmetique;
import td3.Ln;
import td3.MissingValueException;
import td3.Multiplication;
import td3.Pi;
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
		assertEquals(550/17.0, times.calculer(),0.0001);
		

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
		
		assertEquals(Math.pow(9,9), pui.calculer(),0.0001);
		assertEquals(3, racine.calculer(),0.0001);
		assertEquals(2.197224577, ln.calculer(),0.0001);
		assertEquals(0.412118, sin.calculer(),0.0001);
		assertEquals(-0.91113, cos.calculer(),0.0001);

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
		Pi pi = new Pi();
		ExpressionArithmetique piPlusUn = new Addition(pi, un);
		
		ExpressionArithmetique troisQuart = new ConstRationnelle(3,4);
		ExpressionArithmetique unPlusTroisQuart = new Addition(un, troisQuart);

		assertEquals(4.1416, piPlusUn.calculer(),0.0001);	
		assertEquals(1.7500, unPlusTroisQuart.calculer(),0.0001);	
	}
	
	@Test
	public void testVarSymbWithValue() {
		//question 8	
		
		VariableSymbolique x = new VariableSymbolique("x");
		ExpressionArithmetique un = new ConstEntiere(1);
		
		VariableSymbolique y = new VariableSymbolique("y");
		ExpressionArithmetique dix = new ConstEntiere(10);
		
		assertEquals(1, x.calculer(Collections.singletonMap((VariableSymbolique) x, un)), 0.0001);
	
		
		
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); //valorisation de x et y
		map.put(x, un);// x = 1
		map.put(y, un);// y = 1	
		
		Addition denom = new Addition(y, un); // y+1
		Division div = new Division(x, denom);// x/(y+1)
		Addition add = new Addition(un, div);// 1 + (x/(y+1)
		
		assertEquals(3/2.0, add.calculer(map), 0.0001);
		
	}
	
	@Test(expected = MissingValueException.class) //changer par une class perso
	public void testExceptionVarSymb() {
		ExpressionArithmetique x = new VariableSymbolique("x");
		
		double error = x.calculer(); 
	}
	
	@Test
	public void testEquals() {
		
		//x² +3x + 6 ->  x = -0.4
		
		ConstEntiere deux = new ConstEntiere(2);//2		
		ConstEntiere trois = new ConstEntiere(3);//3
		ConstEntiere six = new ConstEntiere(6);//6	
		
		ConstRationnelle moinZeroQuatre = new ConstRationnelle(-2, 5);//-0.4
		ExpressionArithmetique pui = new Puissance(moinZeroQuatre,deux); //-0.4^2
		ExpressionArithmetique times = new Multiplication(trois, moinZeroQuatre); //3*-0.4
		
		ExpressionArithmetique addPuiTimes = new Addition(pui, times);//-0.4^2 + 3*-0.4
		ExpressionArithmetique exp1 = new Addition(addPuiTimes, six);//-0.4^2 + 3*-0.4 + 6
		
		// x² -2x + 4 -> x = -0.4
		
		ExpressionArithmetique times2 = new Multiplication(deux, moinZeroQuatre); //2*-0.4
		ExpressionArithmetique sousPuiTimes2 = new Addition(pui, times2);//-0.4^2 - 2*-0.4
		ExpressionArithmetique exp2 = new Addition(sousPuiTimes2, six);//-0.4^2 + 3*-0.4 + 6
		
		equals(exp1.equals(exp2));
		
		//penser a rajouter un equals avec map 
	}
	
	@Test
	public void testToString() {
		
		//x² +3x + 6 ->  x = -0.4
		
		ConstEntiere deux = new ConstEntiere(2);//2		
		ConstEntiere trois = new ConstEntiere(3);//3
		ConstEntiere six = new ConstEntiere(6);//6	
		
		ConstRationnelle moinZeroQuatre = new ConstRationnelle(-2, 5);//-0.4
		ExpressionArithmetique pui = new Puissance(moinZeroQuatre,deux); //-0.4^2
		ExpressionArithmetique times = new Multiplication(trois, moinZeroQuatre); //3*-0.4
		
		ExpressionArithmetique addPuiTimes = new Addition(pui, times);//-0.4^2 + 3*-0.4
		ExpressionArithmetique exp1 = new Addition(addPuiTimes, six);//-0.4^2 + 3*-0.4 + 6
		

		
		assertEquals("-2/5^2+3*-2/5+6", exp1.toString());
		
		//penser a rajouter un equals avec map 
	}
	
	@Test
	public void test() {
		ConstEntiere deux1 = new ConstEntiere(2);//2
		ConstEntiere trois = new ConstEntiere(3);//2
		VariableSymbolique x = new VariableSymbolique("x");
		
		ExpressionArithmetique oui = new Addition(deux1, x);
		ExpressionArithmetique non = new Soustraction(deux1, x);
		assertFalse(oui.equals(non));
		
		ExpressionArithmetique oui2 = new Addition(deux1, x);
		
		assertTrue(oui.equals(oui2));
		
		ExpressionArithmetique oui3 = new Addition(deux1, trois);
		ExpressionArithmetique non3 = new Addition(deux1, trois);
		
		assertTrue(oui3.equals(oui3));
		
		ExpressionArithmetique opU = new Cos(deux1);
		ExpressionArithmetique opU2 = new Cos(x);
		
		assertFalse(opU.equals(opU2));
		
		ExpressionArithmetique opU3 = new Cos(deux1);
		
		assertTrue(opU.equals(opU3));
	}
}
