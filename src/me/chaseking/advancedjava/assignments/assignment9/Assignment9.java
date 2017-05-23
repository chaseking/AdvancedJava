package me.chaseking.advancedjava.assignments.assignment9;

/**
 * @author Chase King
 */
public class Assignment9 {
    public static void main(String[] args){
        Complex[][] matrix1 = new Complex[][]{
                { new Complex(1, 5), new Complex(1, 6), new Complex(1, 7) },
                { new Complex(2, 5), new Complex(2, 6), new Complex(2, 7) },
                { new Complex(3, 5), new Complex(3, 6), new Complex(3, 7) }
        };

        Complex[][] matrix2 = new Complex[][]{
                { new Complex(1, 6), new Complex(1, 7), new Complex(1, 8) },
                { new Complex(2, 6), new Complex(2, 7), new Complex(2, 8) },
                { new Complex(3, 6), new Complex(3, 7), new Complex(3, 8) }
        };

        ComplexMatrix matrix = new ComplexMatrix();

        System.out.println("Matrix 1 + Matrix 2 =");
        ComplexMatrix.printResult(matrix1, matrix2, matrix.addMatrix(matrix1, matrix2), '+');
        System.out.println();
        System.out.println("Matrix 1 * Matrix 2 =");
        ComplexMatrix.printResult(matrix1, matrix2, matrix.multiplyMatrix(matrix1, matrix2), '*');
    }
}