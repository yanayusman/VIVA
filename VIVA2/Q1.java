package VIVA2;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {

        int r = 2, c = 2;

        //addition
        System.out.println("\nAddition of "+ r +"x"+ c +" matrices: ");
        System.out.println("Matrix A: ");
        double[][] matA = getMatrix(r, c);
        System.out.println("Matrix B: ");
        double[][] matB = getMatrix(r, c);
        double[][] add = addition( matA, matB, r, c);
        System.out.println("\nResultant Matrix:");
        display(add);
        
        //subtraction
        System.out.println("\nSubtraction of "+ r +"x"+ c +" matrices: ");
        System.out.println("Matrix A: ");
        double[][] matAS = getMatrix(r, c);
        System.out.println("Matrix B: ");
        double[][] matBS = getMatrix(r, c);
        double[][] subtract = subtraction(matAS, matBS, r ,c);
        System.out.println("\nResultant Matrix:");
        display(subtract);
        
        //multiplication
        System.out.println("\nMultiplication of "+ r +"x"+ c +" matrices: ");
        System.out.println("Matrix A: ");
        double[][] matAM = getMatrix(r, c);
        System.out.println("Matrix B: ");
        double[][] matBM = getMatrix(r, c);
        double[][] multiply =  multiplication(matAM, matBM);
        System.out.println("\nResultant Matrix:");
        display(multiply);

        //determinant
        int rD= 2, cD = 2;
        System.out.println("\nDeterminant of "+ rD +"x"+ cD +" matrices: ");
        System.out.println("Matrix A: ");
        double[][] matAD = getMatrixD(rD, cD);
        double det = determinant(matAD);
        System.out.println("Determinant of Matrix: " + det);

        //inverse
        int rI = 3, cI = 3;
        System.out.println("Inverse of Matrix: ");
        System.out.println("Matrix A: ");
        double[][] matAI = getMatrixD(rI, cI);
        System.out.println("\nInverse of Matrix:");
        double[][] inverseMat = inverse(matAI);
        displayI(inverseMat);
    }

    public static double[][] getMatrix(int r, int c){
        Scanner sc = new Scanner(System.in);

        double[][] mat = new double[r][c];
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                mat[i][j] = sc.nextDouble();
            }
        }
        return mat;
    }

    public static double[][] getMatrixD(int r, int c){
        Scanner sc = new Scanner(System.in);

        double[][] mat = new double[r][c];
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                mat[i][j] = sc.nextDouble();
            }
        }
        return mat;
    }

    public static void display(double[][] mat){
        for(double[] row : mat){
            for(double val : row){
                System.out.print(val + "   ");
            }
            System.out.println();
        }
    }

    public static void displayI(double[][] mat) {
        System.out.println("Inverse Matrix:");
        for (double[] row : mat) {
            for (double val : row) {
                System.out.printf("%.2f   ", val);
            }
            System.out.println();
        }
    }

    public static double[][] addition(double[][] matA, double[][] matB, int r, int c){
        double[][] add = new double[r][c];

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                add[i][j] = matA[i][j] + matB[i][j];
            }
        }
        return add;
    }

     public static double [][] subtraction(double[][]matA, double[][]matB, int r, int c){
        double[][]subtract = new double[r][c];
        
        for(int i = 0; i < r ; i++){
            for(int j = 0; j < c; j++){
                subtract[i][j] = matA[i][j] - matB[i][j];
            }
        }
        return subtract;
    }

    public static double[][] multiplication(double[][]matA, double[][]matB){
        int r1 = matA.length, c1 = matA[0].length, r2 = matB.length, c2 = matB[0].length;
        double[][] multiply = new double[r1][c2];

        for(int i = 0; i < r1; i++){
            for(int j = 0; j < c2; j++){
                for(int k = 0; k < c1; k++){
                    multiply[i][j] += matA[i][k] * matB[k][j]; 
                }
            }
        }

        return multiply;
    }

    public static double determinant(double[][] matA) {
        int r = matA.length;
        int c = matA[0].length;

        //1x1, return the same element
        if (r == 1 && c == 1)
            return matA[0][0];
    
        //2x2, return using formula ad-bc 
        if (r == 2 && c == 2)
            return matA[0][0] * matA[1][1] - matA[0][1] * matA[1][0];
    
        //size larger than 2x2
        double determinantValue = 0;
        for (int j = 0; j < c; j++) {
            //calculate the cofactor (matrix without current row and column)
            double[][] cofactor = new double[r - 1][c - 1];
            for (int i = 1; i < r; i++) {
                for (int k = 0, l = 0; k < c; k++) {
                    if (k != j) {
                        cofactor[i - 1][l++] = matA[i][k];
                    }
                }
            }
    
            //calculate determinant of the cofactor
            double cofactorDeterminant = determinant(cofactor);
    
            // Add the contribution of this element to the determinant
            determinantValue += (j % 2 == 0 ? 1 : -1) * matA[0][j] * cofactorDeterminant;
        }
    
        return determinantValue;
    }

   public static double[][] inverse(double[][] mat){
        int r = mat.length;
        int c = mat[0].length;

        if(r != c){
            System.out.println("No inverse matrix because the matrix is not a square matrix");
            System.exit(c);}
        double det = determinant(mat);
        if(det == 0){
            System.out.println("No inverse matrix because the matrix is singular");
            System.exit(c);;}
    
        double[][] adjugate = adjugate(mat);
        double inverseDet = 1.0 / det;

        //multiply each element of adjugate matrix by inverse determinant
        for(int i = 0; i < r; i++){
            for(int j =0; j < c; j++){
                adjugate[j][i] *= inverseDet;
            }
        }
        return adjugate;
    }
    
    public static double[][] adjugate(double[][] mat) {
        int r = mat.length;
        int c = mat[0].length;
        double[][] adjugate = new double[r][c];
    
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                double cofactor = Math.pow(-1, i + j) * minorMatrix(mat, i, j);
                adjugate[j][i] = cofactor * ((i + j) % 2 == 0 ? 1 : -1);
            }
        }
    
        return adjugate;
    }

    public static double minorMatrix(double[][] mat, int r, int c){
        int minorR1 = (r + 1) % 3, minorR2 = (r + 2) % 3;
        int minorC1 = (c + 1) % 3, minorC2 = (c + 2) % 3;

        return mat[minorR1][minorC1] * mat[minorR2][minorC2] - mat[minorR1][minorC2] * mat[minorR2][minorC1];
    }
}