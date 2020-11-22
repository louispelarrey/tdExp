package poo;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import td3.Addition;
import td3.ConstEntiere;
import td3.ConstRationnelle;
import td3.Cos;
import td3.Division;
import td3.ExpSomme;
import td3.ExpressionArithmetique;
import td3.Ln;
import td3.Matrice;
import td3.MissingValueException;
import td3.Multiplication;
import td3.Pi;
import td3.Puissance;
import td3.RacineCarre;
import td3.Sin;
import td3.Soustraction;
import td3.VariableSymbolique;
import td3.VariableSymboliqueIndicee;
import td3.E;
import td3.ExpProduit;

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
	
	@Test //question 2
	public void testVarSymb() {
		VariableSymbolique x = new VariableSymbolique("x");
		
		assertEquals(x, x.simplifier());
		
		ExpressionArithmetique mult = new Multiplication(x, new ConstEntiere(2));
		
		assertEquals(mult, mult.simplifier());
	}

	@Test //question 3 
	public void testOperationBinaire() {

		ExpressionArithmetique neuf = new ConstEntiere(9);
		ExpressionArithmetique deux = new ConstEntiere(2);
		ExpressionArithmetique trois = new ConstEntiere(3);
		ExpressionArithmetique cr = new ConstRationnelle(1, 17);
		ExpressionArithmetique cr2 = new ConstRationnelle(6, 8);

		ExpressionArithmetique plus = new Addition(neuf, deux);
		ExpressionArithmetique minus = new Soustraction(trois, cr);
		ExpressionArithmetique times = new Multiplication(plus, minus);

		assertEquals(550 / 17.0, times.simplifier().calculer(), 0.0001);
		
		
		//ajout pour COVERAGE (effectué après la méthode Equals)
		
		ExpressionArithmetique plus1 = new Addition(neuf, deux);
		ExpressionArithmetique plus2 = new Addition(neuf, cr);
		ExpressionArithmetique plus3 = new Addition(cr, neuf);
		ExpressionArithmetique plus4 = new Addition(cr2, cr);
		
		assertEquals(11, plus1.calculer(), 0.0001);
		assertEquals(new ConstEntiere(11), plus1.simplifier());
		assertEquals(new ConstRationnelle(26, 17), plus2.simplifier());
		assertEquals(new ConstRationnelle(26, 17), plus3.simplifier());
		assertEquals(new ConstRationnelle(55, 68), plus4.simplifier());
		
		
		ExpressionArithmetique sous1 = new Soustraction(neuf, deux);
		ExpressionArithmetique sous2 = new Soustraction(neuf, cr);
		ExpressionArithmetique sous3 = new Soustraction(cr, neuf);
		ExpressionArithmetique sous4 = new Soustraction(cr2, cr);
		
		assertEquals(7, sous1.calculer(), 0.0001);
		assertEquals(new ConstEntiere(7), sous1.simplifier());
		assertEquals(new ConstRationnelle(152, 17), sous2.simplifier());
		assertEquals(new ConstRationnelle(-8, 17), sous3.simplifier());
		assertEquals(new ConstRationnelle(47, 68), sous4.simplifier());
				
		ExpressionArithmetique div1 = new Division(neuf, deux);
		ExpressionArithmetique div2 = new Division(neuf, cr);
		ExpressionArithmetique div3 = new Division(cr, neuf);
		ExpressionArithmetique div4 = new Division(cr2, cr);
		
		assertEquals(9.0/2, div1.calculer(), 0.0001);
		assertEquals(new ConstRationnelle(9, 2), div1.simplifier());
		assertEquals(new ConstRationnelle(9, 17), div2.simplifier());
		assertEquals(new ConstRationnelle(1, 153), div3.simplifier());
		assertEquals(new ConstRationnelle(51, 4), div4.simplifier());	
		
		ExpressionArithmetique mult1 = new Multiplication(neuf, deux);
		ExpressionArithmetique mult2 = new Multiplication(neuf, cr);
		ExpressionArithmetique mult3 = new Multiplication(cr, neuf);
		ExpressionArithmetique mult4 = new Multiplication(cr2, cr);
		
		assertEquals(18, mult1.calculer(), 0.0001);
		assertEquals(new ConstEntiere(18), mult1.simplifier());
		assertEquals(new ConstRationnelle(9, 17), mult2.simplifier());
		assertEquals(new ConstRationnelle(9, 17), mult3.simplifier());
		assertEquals(new ConstRationnelle(3, 68), mult4.simplifier());	
		
	}

	@Test //question 3
	public void testOpUnaire() {
		ExpressionArithmetique neuf = new ConstEntiere(9);
		ExpressionArithmetique pui = new Puissance(neuf, neuf);
		ExpressionArithmetique racine = new RacineCarre(neuf);
		ExpressionArithmetique ln = new Ln(neuf);
		ExpressionArithmetique cos = new Cos(neuf);
		ExpressionArithmetique sin = new Sin(neuf);

		assertEquals(Math.pow(9, 9), pui.calculer(), 0.0001);
		assertEquals(3, racine.calculer(), 0.0001);
		assertEquals(2.197224577, ln.calculer(), 0.0001);
		assertEquals(0.412118, sin.calculer(), 0.0001);
		assertEquals(-0.91113, cos.calculer(), 0.0001);

	}
	
	@Test //question 4
	public void testConstSymbolique() {
		Pi pi = new Pi();
		E e = new E();
		
		assertEquals(3.1416, pi.calculer(), 0.0001);
		assertEquals(2.7182, e.calculer(), 0.0001);
		
		ExpressionArithmetique mult = new Multiplication(pi, new ConstEntiere(2));
		
		assertEquals(mult, mult.simplifier());
	}
	
	@Test // question 5
	public void testSimplifcationStandart() {
		ExpressionArithmetique unQuart = new ConstRationnelle(1, 4);
		ExpressionArithmetique troisQuart = new ConstRationnelle(3, 4);
		ExpressionArithmetique x = new VariableSymbolique("x");
		ConstEntiere un = new ConstEntiere(1);

		ExpressionArithmetique plu = new Addition(unQuart, troisQuart);

		ExpressionArithmetique question = new Addition(plu, x); // ((1/4) + (3/4))+x
		ExpressionArithmetique questionRep = new Addition(un, x); // 1+x

		assertEquals(questionRep, question.simplifier());
	}

	@Test // question 6
	public void testCalculeExpArithmetique() {
		ExpressionArithmetique un = new ConstEntiere(1);
		Pi pi = new Pi();
		ExpressionArithmetique piPlusUn = new Addition(pi, un);

		ExpressionArithmetique troisQuart = new ConstRationnelle(3, 4);
		ExpressionArithmetique unPlusTroisQuart = new Addition(un, troisQuart);

		assertEquals(4.1416, piPlusUn.calculer(), 0.0001);
		assertEquals(1.7500, unPlusTroisQuart.calculer(), 0.0001);
	}

	@Test // question 7
	public void testEquals() {

		ConstEntiere deux = new ConstEntiere(2);// 2
		ConstEntiere deux2 = new ConstEntiere(2);// 2

		assertEquals(true, deux.equals(deux2));

		ConstEntiere trois = new ConstEntiere(3);// 3
		ConstEntiere six = new ConstEntiere(6);// 6

		assertEquals(false, six.equals(trois));

		ExpressionArithmetique add = new Addition(trois, six);
		ExpressionArithmetique add2 = new Addition(trois, six);

		assertEquals(true, add.equals(add2));

		ExpressionArithmetique div = new Division(six, trois);
		ExpressionArithmetique div2 = new Division(trois, six);

		assertEquals(false, div.equals(div2));
		
		ConstEntiere deux1 = new ConstEntiere(2);// 2

		VariableSymbolique x = new VariableSymbolique("x");

		ExpressionArithmetique oui = new Addition(deux1, x);
		ExpressionArithmetique non = new Soustraction(deux1, x);
		
		assertEquals(false, oui.equals(non));

		ExpressionArithmetique opU = new Cos(deux1);
		ExpressionArithmetique opU2 = new Cos(x);

		assertEquals(false, opU.equals(opU2));

		ExpressionArithmetique opU3 = new Cos(deux1);

		assertEquals(true, opU.equals(opU3));

	}
	
	@Test // question 8
	public void testVarSymbWithValue() {

		VariableSymbolique x = new VariableSymbolique("x");
		ExpressionArithmetique un = new ConstEntiere(1);
		VariableSymbolique y = new VariableSymbolique("y");

		assertEquals(1, x.calculer(Collections.singletonMap((VariableSymbolique) x, un)), 0.0001);

		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); // valorisation de x et y
		map.put(x, un);// x = 1
		map.put(y, un);// y = 1

		Addition denom = new Addition(y, un); // y+1
		Division div = new Division(x, denom);// x/(y+1)
		Addition add = new Addition(un, div);// 1 + (x/(y+1)

		assertEquals(3 / 2.0, add.calculer(map), 0.0001);

	}
	
	@Test(expected = MissingValueException.class) //question 8
	public void testExceptionVarSymb() {
		ExpressionArithmetique x = new VariableSymbolique("x");

		x.calculer();
	}


	@Test // question 9
	public void testToString() {

		ConstEntiere deux = new ConstEntiere(2);// 2
		ConstEntiere trois = new ConstEntiere(3);// 3
		ConstEntiere six = new ConstEntiere(6);// 6

		ConstRationnelle moinZeroQuatre = new ConstRationnelle(-2, 5);// -0.4
		ExpressionArithmetique pui = new Puissance(moinZeroQuatre, deux); // -0.4^2
		ExpressionArithmetique times = new Multiplication(trois, moinZeroQuatre); // 3*-0.4

		ExpressionArithmetique addPuiTimes = new Addition(pui, times);// -0.4^2 + 3*-0.4
		ExpressionArithmetique exp1 = new Addition(addPuiTimes, six);// -0.4^2 + 3*-0.4 + 6

		assertEquals("-2/5^2+3*-2/5+6", exp1.toString());
		
		ExpressionArithmetique div = new Division(six , trois);
		
		assertEquals("6/3", div.toString());
		
		ExpressionArithmetique sous = new Soustraction(six , trois);
		
		assertEquals("6-3", sous.toString());

	}
	
	@Test // question 10
	public void testValeursRemarquables(){
		ConstEntiere un = new ConstEntiere(1);// 1
		ConstEntiere deux1 = new ConstEntiere(2);// 2
		ConstEntiere zero = new ConstEntiere(0);// 0
		ConstEntiere quatre = new ConstEntiere(4);// 4
		
		ExpressionArithmetique ln = new Ln(un);
		ExpressionArithmetique qLouis = new Addition(un, ln);
		
		assertEquals(un , qLouis.simplifier());

		Pi pi = new Pi();
		E e = new E();

		ExpressionArithmetique ePui = new Puissance(e, zero);
		ExpressionArithmetique qLouis3 = new Addition(un, ePui);

		assertEquals(deux1, qLouis3.simplifier());

		ExpressionArithmetique pi2 = new Division(pi, deux1);
		ExpressionArithmetique sin2 = new Sin(pi2);
		ExpressionArithmetique qLouis4 = new Addition(un, sin2);

		assertEquals(deux1, qLouis4.simplifier());

		ExpressionArithmetique qLouis5 = new RacineCarre(quatre);
		// peut etre compléter avec d'autres exemples

		assertEquals(deux1 , qLouis5.simplifier());
	}
	
	@Test //question 11
	public void testVariableSymbIndicee() {
		VariableSymboliqueIndicee u = new VariableSymboliqueIndicee("U", new VariableSymbolique("n"));
		assertEquals("Un" , u.toString());
		
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); // valorisation de n
		map.put(new VariableSymbolique("n"), new ConstEntiere(2));// n = 2
		
		assertEquals(new VariableSymboliqueIndicee("U", new ConstEntiere(2)), u.simplifier(map));
		
		map.put(u, new Puissance(new VariableSymbolique("n"), new ConstEntiere(2)));// U_n = n^2
		assertEquals(new ConstEntiere(4), u.simplifier(map));
	}
	
	@Test //question 11
	public void testExpSomme() {
		VariableSymbolique x = new VariableSymbolique("x");
		VariableSymbolique i = new VariableSymbolique("i");
		
		VariableSymboliqueIndicee a_i = new VariableSymboliqueIndicee("a", i); // a_i
		
		Puissance pui = new Puissance(i, new ConstEntiere(2)); // i^2
		Multiplication mult = new Multiplication(new ConstEntiere(4),i); // 4*i		
		Puissance droite = new Puissance(x, i); //x^i
		
		Multiplication multi = new Multiplication(a_i,droite); // a_i * x^i
		
		ExpSomme exp = new ExpSomme(new VariableSymbolique("i"),0, 4, multi);
		
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); // valorisation de a_i et x
		map.put(a_i, pui); // a_i = i^2
		map.put(x, new ConstEntiere(4)); // x = 4
		
		assertEquals(new ConstEntiere(4740), exp.simplifier(map));
		
		map.put(a_i, mult); // a_i = 4*i
		
		assertEquals(new ConstEntiere(5008), exp.simplifier(map));

	}
	
	@Test //question 12
	public void testExpProduit() {
		VariableSymbolique x = new VariableSymbolique("x");
		VariableSymbolique i = new VariableSymbolique("i");
		
		VariableSymboliqueIndicee a_i = new VariableSymboliqueIndicee("a", i); // a_i
		
		Puissance pui = new Puissance(i, new ConstEntiere(2)); // i^2	
		Puissance droite = new Puissance(x, i); //x^i
		
		Multiplication multi = new Multiplication(a_i,droite); // a_i * x^i
		
		ExpProduit exp = new ExpProduit(new VariableSymbolique("i"),0, 4, multi);
		
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); // valorisation de a_i et x
		map.put(a_i, pui); // a_i = i^2
		map.put(x, new ConstEntiere(4)); // x = 4
		
		assertEquals(new ConstEntiere(0), exp.simplifier(map));
			
	}
	
	@Test // question 13
	public void testDerivPolynome() {
			ConstEntiere trois1 = new ConstEntiere(3);
			ConstEntiere six = new ConstEntiere(6);
			ConstEntiere cinq = new ConstEntiere(5);
			ConstEntiere dix = new ConstEntiere(10);
			VariableSymbolique x1 = new VariableSymbolique("x");
			ConstEntiere deux1 = new ConstEntiere(2);// 2
			ConstEntiere trois = new ConstEntiere(3);// 3
			VariableSymbolique x = new VariableSymbolique("x");

			ExpressionArithmetique add = new Addition(x, trois);
			ExpressionArithmetique multi = new Multiplication(x, trois);
			ExpressionArithmetique puissa = new Puissance(x, deux1);
			ExpressionArithmetique TroisixCarre = new Multiplication(trois1, puissa);

			assertEquals(new ConstEntiere(1), add.deriver());
			assertEquals(new ConstEntiere(3), multi.deriver());
			assertEquals(new Multiplication(deux1, x1), puissa.deriver());

			ExpressionArithmetique cinqIx = new Multiplication(cinq, x);
			ExpressionArithmetique sixIx = new Multiplication(six, x);

			assertEquals(sixIx, new Multiplication(trois1, new Multiplication(deux1, x1)).simplifier());
			assertEquals(sixIx, TroisixCarre.deriver().simplifier());
			
			assertEquals(new Addition(sixIx, cinq), new Addition(dix, new Addition(TroisixCarre, cinqIx)).deriver());

	}
	
	@Test //question 14
	public void testDerivPolynomeOrdreN(){
		ConstEntiere trois1 = new ConstEntiere(3);
		ConstEntiere cinq = new ConstEntiere(5);
		ConstEntiere dix = new ConstEntiere(10);
		VariableSymbolique x1 = new VariableSymbolique("x");
		ConstEntiere deux1 = new ConstEntiere(2);// 2
		ConstEntiere quatre = new ConstEntiere(4);// 4
		
		ExpressionArithmetique cinqxquatre = new Multiplication(cinq, new Puissance(x1, quatre));
		ExpressionArithmetique quatrexcube = new Multiplication(quatre, new Puissance(x1, trois1));
		ExpressionArithmetique troisxcarre = new Multiplication(trois1, new Puissance(x1, deux1));
		ExpressionArithmetique cinqx = new Multiplication(cinq, x1);
		ExpressionArithmetique centvingt = new ConstEntiere(120);
		
		assertEquals(new Addition(new Multiplication(centvingt, x1), new ConstEntiere(24)), new Addition(cinqxquatre,
				new Addition(quatrexcube, new Addition(troisxcarre, new Addition(cinqx, dix)))).deriver(3));
	}

	@Test // question 15
	public void testElementNeutre() {
		ConstEntiere un = new ConstEntiere(1);// 1
		ConstEntiere zero = new ConstEntiere(0);// 0
		ConstEntiere cinq = new ConstEntiere(5);// 5
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
		ConstEntiere un = new ConstEntiere(1);// 1
		ConstEntiere deux = new ConstEntiere(2);// 2
		VariableSymbolique x = new VariableSymbolique("x"); // x
		ConstRationnelle unDemi = new ConstRationnelle(1, 2);// 1/2

		// Distributivité addition

		ExpressionArithmetique add = new Addition(x, unDemi); // (x+1/2)

		ExpressionArithmetique mult = new Multiplication(deux, add); // 2*(x+1/2)
		ExpressionArithmetique add2 = new Addition(new Multiplication(deux, x), un); // 2x + 1
		assertEquals(true, mult.simplifier().equals(add2.simplifier()));

		ExpressionArithmetique mult3 = new Multiplication(x, add); // x*(x+1/2)
		ExpressionArithmetique add3 = new Addition(new Multiplication(x, x), new Multiplication(x, unDemi)); // x*x + x*1/2
		assertEquals(true, mult3.simplifier().equals(add3.simplifier()));


		// Distributivité soustraction

		ExpressionArithmetique sous = new Soustraction(x, unDemi); // (x-1/2)
		
		ExpressionArithmetique mult5 = new Multiplication(deux, sous); // 2*(x-1/2)
		ExpressionArithmetique sous5 = new Soustraction(new Multiplication(deux, x), un); // 2x - 1
		assertEquals(true, mult5.simplifier().equals(sous5.simplifier()));

		ExpressionArithmetique mult6 = new Multiplication(x, sous); // x*(x-1/2)
		ExpressionArithmetique sous6 = new Soustraction(new Multiplication(x, x), new Multiplication(x, unDemi));// x*x - x*1/2
		assertEquals(true, mult6.simplifier().equals(sous6.simplifier()));


		// Distributivité inversée

		ExpressionArithmetique mult8 = new Multiplication(sous, deux); // (x-1/2)*2
		ExpressionArithmetique sous8 = new Soustraction(new Multiplication(x, deux), un); // x*2 - 1		
		assertEquals(true, mult8.simplifier().equals(sous8.simplifier()));

		// Double distributivité

		ExpressionArithmetique mult9 = new Multiplication(add, add); // (x+1/2)*(x+1/2)
		ExpressionArithmetique sous9 = new Addition(
				new Addition(new Multiplication(x, x), new Multiplication(unDemi, x)),
				new Addition(new Multiplication(x, unDemi), new Multiplication(unDemi, unDemi))); // x*x+1/2*x+x*1/2+1/4
		assertEquals(true, mult9.simplifier().equals(sous9.simplifier()));

		// Double distrib avec une soustraction ne marche pas !!
	}
	
	@Test //question 17
	public void testAssociativite() {	
		ConstEntiere un = new ConstEntiere(1);
		VariableSymbolique x = new VariableSymbolique("x");
		ConstEntiere deux = new ConstEntiere(2);
		
		ExpressionArithmetique add = new Addition(un, new Addition(un, x)); // 1 + (1+x)
		ExpressionArithmetique add2 = new Addition(deux, x); // 2 + x
		add.simplifier();
		
		assertEquals(add2, add.simplifier());
				
		ExpressionArithmetique mult = new Multiplication(deux, new Multiplication(new ConstRationnelle(1, 2), x)); //2 * (1/2 * x) 
		assertEquals(x, mult.simplifier());
	}

	@Test //question 18
	public void testFactorisationIdRemarquable() {
		ConstEntiere trois = new ConstEntiere(4);
		ConstEntiere six = new ConstEntiere(8);
		ConstEntiere deux = new ConstEntiere(2);
		VariableSymbolique a = new VariableSymbolique("a");
		VariableSymbolique b = new VariableSymbolique("b");
		
		ExpressionArithmetique puissA = new Puissance(a, new ConstEntiere(2));
		ExpressionArithmetique puissB = new Puissance(b, new ConstEntiere(2));
		
		ExpressionArithmetique part1 = new Multiplication(trois, puissA);
		ExpressionArithmetique part2 = new Multiplication(six, new Multiplication(a,b));
		ExpressionArithmetique part3 = new Multiplication(trois, puissB);
		 
		Addition idRemarquable = new Addition(part1, new Addition(part2, part3)); //3a^2 + 6ab + 3b^2
		
		ExpressionArithmetique puissC = new Puissance(new Addition(a, b), deux);
		ExpressionArithmetique idRemarqueFactorise = new Multiplication(trois, puissC);//3 * (a+b)^2
		
		assertEquals(idRemarqueFactorise, idRemarquable.idRemarquable());
		 
	}
	
	@Test //question 29
	public void testMatrice() {
		
		//SIMPLIFICATION
		
		Map<VariableSymbolique, ExpressionArithmetique> map = new HashMap<>(); // valorisation de x et y
		map.put(new VariableSymbolique("x"), new ConstEntiere(3));// x = 3
		map.put(new VariableSymbolique("y"), new ConstEntiere(5));// y = 5
		
		ExpressionArithmetique[][] s={{new ConstEntiere(7),new VariableSymbolique("x")},{new ConstEntiere(2), new VariableSymbolique("y")}}; 
		Matrice mSimp = new Matrice(s);
		
		ExpressionArithmetique[][] sR={{new ConstEntiere(7),new ConstEntiere(3)},{new ConstEntiere(2), new ConstEntiere(5)}};
		Matrice mSimpRep = new Matrice(sR);
		
		assertEquals(mSimpRep, mSimp.simplifier(map));
		
		//PRODUIT 2 MATRICES
		
		ExpressionArithmetique[][] a={{new ConstEntiere(4),new ConstEntiere(3)},{new ConstEntiere(2), new ConstEntiere(1)}}; 
		ExpressionArithmetique[][] b={{new ConstEntiere(2),new ConstEntiere(4)},{new ConstEntiere(4), new ConstEntiere(5)}}; 
		
		Matrice m = new Matrice(a);
		Matrice m2 = new Matrice(b);
		
		ExpressionArithmetique[][] c={{new ConstEntiere(20),new ConstEntiere(31)},{new ConstEntiere(8), new ConstEntiere(13)}}; 
		Matrice m3 = new Matrice(c);
		
		ExpressionArithmetique produit = new Multiplication(m, m2); 
		assertEquals(m3, produit.simplifier());
		
		//SOMME MATRICIELLE
		
		ExpressionArithmetique[][] d={{new ConstEntiere(8),new ConstEntiere(6)},{new ConstEntiere(4), new ConstEntiere(2)}}; 
		Matrice m4= new Matrice(d);
		
		ExpressionArithmetique somme = new Addition(m, m);
		assertEquals(m4, somme.simplifier());
		
		
		//PRODUIT MATRICE ET EXP ARITHMETIQUE
		
		ExpressionArithmetique produit2 = new Multiplication(m, new ConstEntiere(2)); 
		assertEquals(m4, produit2.simplifier());
	}
	
	
}
