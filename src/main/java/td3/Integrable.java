package td3;

import java.util.Collections;
import java.util.Map;

public interface Integrable {
	default double integrationRiemann(VariableSymbolique var, int precision, double a, double b) {
		double intervalle = b - a;
		double pas = intervalle / precision;
		double somme = 0.0;
		
		for(int i = 0; i < pas; i++) {
			double Xi = a + i * pas;
			double Xiplus1 = a + (i + 1) * pas;
			
			double milieu = (Xi + Xiplus1) / 2;
			double calculFonc = this.calculer(Collections.singletonMap(new VariableSymbolique("x"), ConstRationnelle.getFraction(milieu)));
			
			somme += (Xiplus1 - Xi) * calculFonc;
		}
		
		return somme;
	}
	
	double calculer(Map<VariableSymbolique, ExpressionArithmetique> map);
}
