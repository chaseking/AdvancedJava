package me.chaseking.advancedjava.assignments.assignment9;

/**
 * @author Chase King
 */
public class Complex implements Cloneable {
    public static final Complex ZERO = new Complex(0);

    private double a = 0, b = 0;

    public Complex() {
    }

    Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Complex(double a) {
        this.a = a;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public Complex add(Complex secondComplex) {
        double newA = a + secondComplex.getA();
        double newB = b + secondComplex.getB();
        return new Complex(newA, newB);
    }

    public Complex subtract(Complex secondComplex){
        double newA = a - secondComplex.getA();
        double newB = b - secondComplex.getB();
        return new Complex(newA, newB);
    }

    public Complex multiply(Complex secondComplex) {
        double newA = a * secondComplex.getA() - b * secondComplex.getB();
        double newB = b * secondComplex.getA() + a * secondComplex.getB();
        return new Complex(newA, newB);
    }

    public Complex divide(Complex secondComplex) {
        double newA = (a * secondComplex.getA() + b * secondComplex.getB())
                / (Math.pow(secondComplex.getA(), 2.0) + Math.pow(secondComplex.getB(),
                2.0));
        double newB = (b * secondComplex.getA() - a * secondComplex.getB())
                / (Math.pow(secondComplex.getA(), 2.0) + Math.pow(secondComplex.getB(),
                2.0));
        return new Complex(newA, newB);
    }

    public double abs() {
        return Math.sqrt((a * a) + (b * b));
    }

    @Override
    public String toString() {
        if (b != 0)
            return a + " + " + b + "i";
        return a + "";
    }

    public double getRealPart() {
        return a;
    }

    public double getImaginaryPart() {
        return b;
    }

    @Override /** Implement the clone method in
     the Object class */
    public Object clone() {
        // Implement it
        try {
            return super.clone(); // Automatically perform a shallow copy
        }
        catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}