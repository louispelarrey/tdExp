package td3;

import java.util.Arrays;
import java.util.Map;

public class Matrice extends ExpressionArithmetique {
	private final int row;
    private final int column;
    private final ExpressionArithmetique[][] datas;

    public Matrice(int row, int column) {
        this.row = row;
        this.column = column;
        this.datas = new ExpressionArithmetique[row][column];
    }
    
    public Matrice(ExpressionArithmetique[][] datas) {
    	this.row = datas.length;
        this.column = datas[0].length;
        this.datas = datas;
    }

    private void setData(int i, int j, ExpressionArithmetique data) {
        datas[i][j] = data;
    }

    public ExpressionArithmetique getData(int i, int j) {
        return datas[i][j];
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


	@Override
	public ExpressionArithmetique simplifier(Map<VariableSymbolique, ExpressionArithmetique> map) {		
		Matrice m = new Matrice(this.row, this.column);
		for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                m.setData(i, j, getData(i, j).simplifier(map));
            }
        }
		
		return m;
	}

	@Override
	public double calculer(Map<VariableSymbolique, ExpressionArithmetique> map) {
		if(this.row == 1 && this.column == 1)
			return getData(1, 1).calculer(map);
		else {
			throw new IllegalArgumentException("Impossible de calculer cette matrice");
		}
	}

	@Override
	public ExpressionArithmetique clone() {
		Matrice m = new Matrice(this.row, this.column);
		for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                m.setData(i, j, getData(i, j).clone());
            }
        }
		
		return m;
	}
	
	public Matrice produit(Matrice mat) {

        if(this.column != mat.getRow()) {
            throw new IllegalArgumentException("Impossible d'effectuer le produit : les matrices sont incompatibles");
        } 
        
        Matrice m = new Matrice(this.row, mat.getColumn());
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < mat.getColumn(); j++) {
                    ExpressionArithmetique e = new ConstEntiere(0);
                    for (int k = 0; k < this.column; k++) {
                        e = new Addition(e, new Multiplication(this.getData(i,k), mat.getData(k,j))).simplifier();
                    }
                    m.setData(i,j,e);
                }
            }
            return m;
        
    }
	
	public Matrice produit(ExpressionArithmetique droite) {

		Matrice produit = new Matrice(this.row, this.column);
        for(int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                produit.setData(i, j, new Multiplication(getData(i, j), droite).simplifier());
            }
        }
        
        return produit;
        
    }
	
	public Matrice somme(Matrice mat) {
		if(this.column != mat.getColumn() || this.row != mat.getRow()) {
			throw new IllegalArgumentException("Impossible d'effectuer le produit : les matrices sont incompatibles");
        } 
		
		Matrice somme = new Matrice(this.row, this.column);
        for(int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                somme.setData(i, j, new Addition(getData(i, j), mat.getData(i, j)).simplifier());
            }
        }
        
        return somme;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
 
		for (int i = 0; i < this.row; i++) {
			for(int j = 0; j < this.column; j++) {
				sb.append(this.getData(i, j).toString()).append("  ");
			}
			sb.append("\n");
		}
 
		return sb.toString();
	}
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + Arrays.deepHashCode(datas);
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrice other = (Matrice) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return (Arrays.deepEquals(datas, other.datas));
	}
}
