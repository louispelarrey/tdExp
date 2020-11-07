package poo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.Expression;
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
		
		assertEquals(550/17.0, times.simplifier().calculer(),0.0001);
			 
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
	
	@Test //question 5
	public void testSimplifierVarSymb(){
		ExpressionArithmetique unQuart = new ConstRationnelle(1, 4);
		ExpressionArithmetique troisQuart = new ConstRationnelle(3, 4);
		ExpressionArithmetique x = new VariableSymbolique("x");
		ConstEntiere un = new ConstEntiere(1);
		
		ExpressionArithmetique plu = new Addition(unQuart, troisQuart);
		

		ExpressionArithmetique question = new Addition(plu, x); 	//((1/4) + (3/4))+x
		ExpressionArithmetique questionRep = new Addition(un, x); 	//1+x

		
		assertEquals(true, question.simplifier().equals(questionRep.simplifier()));
	}
	
	@Test //question 6
	public void testConstSymb() {
		
		ExpressionArithmetique un = new ConstEntiere(1);
		Pi pi = new Pi();
		ExpressionArithmetique piPlusUn = new Addition(pi, un);
		
		ExpressionArithmetique troisQuart = new ConstRationnelle(3,4);
		ExpressionArithmetique unPlusTroisQuart = new Addition(un, troisQuart);

		assertEquals(4.1416, piPlusUn.calculer(),0.0001);	
		assertEquals(1.7500, unPlusTroisQuart.calculer(),0.0001);	
	}
	
	@Test //question 8	
	public void testVarSymbWithValue() {	
		
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
	
	@Test(expected = MissingValueException.class) 
	public void testExceptionVarSymb() {
		ExpressionArithmetique x = new VariableSymbolique("x");
		
		double error = x.calculer(); 
	}
	
	@Test //question 7
	public void testEquals() {
		
		ConstEntiere deux = new ConstEntiere(2);//2	
		ConstEntiere deux2 = new ConstEntiere(2);//2
		
		assertEquals(true,deux.equals(deux2));
		
		ConstEntiere trois = new ConstEntiere(3);//3
		ConstEntiere six = new ConstEntiere(6);//6	
		
		assertEquals(false,six.equals(trois));
	
		ExpressionArithmetique add = new Addition(trois, six);
		ExpressionArithmetique add2 = new Addition(trois, six);
		
		assertEquals(true,add.equals(add2));
		
		ExpressionArithmetique div = new Division(six, trois);
		ExpressionArithmetique div2 = new Division(trois, six);
		
		assertEquals(false,div.equals(div2));
		
	}
	
	@Test //question 9
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
		
		// Question 17
		
		/*
		ExpressionArithmetique baseAdd = new Addition(new VariableSymbolique("x"), new ConstEntiere(2));
		ExpressionArithmetique baseAdd2 = new Addition(baseAdd, new ConstEntiere(2));
		ExpressionArithmetique baseAdd3 = new Addition(baseAdd2, new VariableSymbolique("y"));
		ExpressionArithmetique simpString = baseAdd3.simplifier();
		*/
		
		
		ExpressionArithmetique baseMul = new Multiplication(new ConstEntiere(2), new VariableSymbolique("x"));
		ExpressionArithmetique baseMul2 = new Multiplication(new ConstEntiere(5), baseMul);
		ExpressionArithmetique baseMul3 = new Multiplication(baseMul2, new ConstEntiere(6));
		ExpressionArithmetique simpString = baseMul3.simplifier();
		
		//System.out.println(simpString);

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
	
	@Test //question 15
	public void testElementNeutre() {
		ConstEntiere un = new ConstEntiere(1);//1
		ConstEntiere zero = new ConstEntiere(0);//0		
		ConstEntiere cinq = new ConstEntiere(5);//5
		VariableSymbolique x = new VariableSymbolique("x");
		
		ExpressionArithmetique add = new Addition(zero, cinq);	
		ExpressionArithmetique sous = new Soustraction(cinq, zero);
		
		assertEquals(true, cinq.equals(add.simplifier()));
		assertEquals(true, cinq.equals(sous.simplifier()));
		
		ExpressionArithmetique div = new Division(cinq, un);
		ExpressionArithmetique mult = new Multiplication(un, cinq);
		
		assertEquals(true, cinq.equals(div.simplifier()));
		assertEquals(true, cinq.equals(mult.simplifier()));
		
		ExpressionArithmetique addX = new Addition(zero, x);	
		
		assertEquals(true, x.equals(addX.simplifier()));
	}
	
	@Test // question 16
	public void testDistributivite() {
		ConstEntiere un = new ConstEntiere(1);//1
		ConstEntiere deux = new ConstEntiere(2);//2
		VariableSymbolique x = new VariableSymbolique("x");	//x	
		ConstRationnelle unDemi = new ConstRationnelle(1,2);// 1/2
		
		
		//Distributivité addition
		
		ExpressionArithmetique add = new Addition(x, unDemi); // (x+1/2)
		
		ExpressionArithmetique mult = new Multiplication(deux, add); 										// 2*(x+1/2)
		ExpressionArithmetique add2 = new Addition(new Multiplication(deux, x), un);						// 2x + 1
		
		assertEquals(true, mult.simplifier().equals(add2.simplifier()));
		
		ExpressionArithmetique mult3 = new Multiplication(x, add); 											// x*(x+1/2)
		ExpressionArithmetique add3= new Addition(new Multiplication(x, x), new Multiplication(x, unDemi)); // x*x + x*1/2
		
		assertEquals(true, mult3.simplifier().equals(add3.simplifier()));
		
		Cos cos = new Cos(deux); //cos(2)
		ExpressionArithmetique mult4 = new Multiplication(cos, add); 											// cos(2)*(x+1/2)
		ExpressionArithmetique add4= new Addition(new Multiplication(cos, x), new Multiplication(cos, unDemi));	// cos(2)*x + cos(2)*1/2
		
		assertEquals(true, mult4.simplifier().equals(add4.simplifier()));
	
		//Distributivité soustraction
		
		ExpressionArithmetique sous = new Soustraction(x, unDemi); // (x-1/2)
		
		ExpressionArithmetique mult5 = new Multiplication(deux, sous); 										// 2*(x-1/2)
		ExpressionArithmetique sous5 = new Soustraction(new Multiplication(deux, x), un);					// 2x - 1
		
		assertEquals(true, mult5.simplifier().equals(sous5.simplifier()));
		
		ExpressionArithmetique mult6 = new Multiplication(x, sous); 											// x*(x-1/2)
		ExpressionArithmetique sous6= new Soustraction(new Multiplication(x, x), new Multiplication(x, unDemi));// x*x - x*1/2
		
		assertEquals(true, mult6.simplifier().equals(sous6.simplifier()));
		
		Cos cos2 = new Cos(deux); //cos(2)
		ExpressionArithmetique mult7 = new Multiplication(cos2, sous); 													// cos*(x-1/2)
		ExpressionArithmetique sous7= new Soustraction(new Multiplication(cos2, x), new Multiplication(cos2, unDemi)); 	// cos(2)*x - cos(2)*1/2
		
		assertEquals(true, mult7.simplifier().equals(sous7.simplifier()));
		
		//Distributivité inversé
		
		ExpressionArithmetique mult8 = new Multiplication(sous, deux); 										// (x-1/2)*2
		ExpressionArithmetique sous8 = new Soustraction(new Multiplication(x, deux), un);					//  x*2 - 1	
		
		assertEquals(true, mult8.simplifier().equals(sous8.simplifier()));
		
		//Double distributivité
		
		ExpressionArithmetique mult9 = new Multiplication(add, add); //	(x+1/2)*(x+1/2)
		ExpressionArithmetique sous9 = new Addition(new Addition(new Multiplication(x, x),new Multiplication(unDemi, x)), new Addition(new Multiplication(x, unDemi),new Multiplication(unDemi, unDemi))); //x*x+1/2*x+x*1/2+1/4

		assertEquals(true, mult9.simplifier().equals(sous9.simplifier()));
		
		//Double distrib avec une soustraction ne marche pas !!
	}
		
	@Test
	public void testIdRemarquable() {
		ConstEntiere trois = new ConstEntiere(3);
		ConstRationnelle rat = new ConstRationnelle(2, 5);
		VariableSymbolique a = new VariableSymbolique("a");
		VariableSymbolique b = new VariableSymbolique("b");
		Puissance puissA = new Puissance(a, new ConstEntiere(2));
		Puissance puissB = new Puissance(b, new ConstEntiere(2));
		
		/*
		ExpressionArithmetique part1 = new Multiplication(trois, puissA);
		ExpressionArithmetique part2 = new Multiplication(six, new Multiplication(a, b));
		ExpressionArithmetique part3 = new Multiplication(trois, puissB);
		
		Addition idRemarquable = new Addition(part1, new Addition(part2, part3));
		*/
		
		
		ExpressionArithmetique part1 = new Multiplication(rat, puissA);
		ExpressionArithmetique part2 = new Multiplication(rat, puissB);
		
		Soustraction idRemarquable = new Soustraction (part1, part2);
		
		
		ExpressionArithmetique idRemarquableSimp = idRemarquable.idRemarquable();
		System.out.println(idRemarquableSimp);
	}
}
