package VIVA2;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {

        // int r = 2, c = 2;

        // //addition
        // System.out.println("\nAddition of "+ r +"x"+ c +" matrices: ");
        // System.out.println("Matrix A: ");
        // int[][] matA = getMatrix(r, c);
        // System.out.println("Matrix B: ");
        // int[][] matB = getMatrix(r, c);
        // int[][] add = addition( matA, matB, r, c);
        // System.out.println("\nResultant Matrix:");
        // display(add);
        
        // //subtraction
        // System.out.println("\nSubtraction of "+ r +"x"+ c +" matrices: ");
        // System.out.println("Matrix A: ");
        // int[][] matAS = getMatrix(r, c);
        // System.out.println("Matrix B: ");
        // int[][] matBS = getMatrix(r, c);
        // int[][] subtract = subtraction(matAS, matBS, r ,c);
        // System.out.println("\nResultant Matrix:");
        // display(subtract);
        
        // //multiplication
        // System.out.println("\nMultiplication of "+ r +"x"+ c +" matrices: ");
        // System.out.println("Matrix A: ");
        // int[][] matAM = getMatrix(r, c);
        // System.out.println("Matrix B: ");
        // int[][] matBM = getMatrix(r, c);
        // int[][] multiply =  multiplication(matAM, matBM);
        // System.out.println("\nResultant Matrix:");
        // display(multiply);

        // //determinant
        // int rD= 3, cD = 3;
        // System.out.println("\nDeterminant of "+ rD +"x"+ cD +" matrices: ");
        // System.out.println("Matrix A: ");
        // double[][] matAD = getMatrixD(rD, cD);
        // double det = determinant(matAD, rD, cD);
        // System.out.println("Determinant of Matrix: " + det);

        //inverse
        int rI = 3, cI = 3;
        System.out.println("Inverse of Matrix: ");
        System.out.println("Matrix A: ");
        double[][] matAI = getMatrixD(rI, cI);
        System.out.println("\nInverse of Matrix:");
        double[][] inverseMat = inverse(matAI);
        displayI(inverseMat);
    }

    public static int[][] getMatrix(int r, int c){
        Scanner sc = new Scanner(System.in);

        int[][] mat = new int[r][c];
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                mat[i][j] = sc.nextInt();
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

    public static void display(int[][] mat){
        for(int[] row : mat){
            for(int val : row){
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

    public static int[][] addition(int[][] matA, int[][] matB, int r, int c){
        int[][] add = new int[r][c];

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                add[i][j] = matA[i][j] + matB[i][j];
            }
        }
        return add;
    }

     public static int [][] subtraction(int[][]matA, int[][]matB, int r, int c){
        int[][]subtract = new int[r][c];
        
        for(int i = 0; i < r ; i++){
            for(int j = 0; j < c; j++){
                subtract[i][j] = matA[i][j] - matB[i][j];
            }
        }
        return subtract;
    }

    public static int[][] multiplication(int[][]matA, int[][]matB){
        int r1 = matA.length, c1 = matA[0].length, r2 = matB.length, c2 = matB[0].length;
        int[][] multiply = new int[r1][c2];
        for(int i = 0; i < r1; i++){
            for(int j = 0; j < c2; j++){
                for(int k = 0; k < c1; k++){
                    multiply[i][j] += matA[i][k] * matB[k][j]; 
                }
            }
        }

        return multiply;
    }

    public static double determinant(double[][] matA, int r, int c){
        return (matA[0][0] * ((matA[1][1] * matA[2][2]) - (matA[1][2] * matA[2][1]))) - (matA[0][1] * ((matA[1][0] * matA[2][2]) - (matA[1][2] * matA[2][0]))) + (matA[0][2] * ((matA[1][0] * matA[2][1]) - (matA[1][1] * matA[2][0])));
    }

    public static double[][] inverse(double[][] mat){
        int r = mat.length;
        int c = mat[0].length;

        if(r != c){
            System.out.println("No inverse matrix because the matrix is not a square matrix");
            System.exit(c);}
        double det = determinant(mat, r, c);
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