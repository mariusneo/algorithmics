package org.mg.catcoder.fractals;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Mathematical solution for the Koch snowflake problem.
 * Solutions are taken from the website :
 * <ul>
 * <li>http://snowflakecurve.weebly.com/snowflake-curve.html</li>
 * <li>http://snowflakecurve.weebly.com/square-curve.html</li>
 * </ul>
 * <p/>
 * Images which represent the fractals can be found in the same package from the resource folder.
 */
public class Fractals {

    /**
     * The equtation for the perimeter of these snowfalkes (can be inducted) :
     * Pn= P1 ( 4/3)^n-1
     */
    private double calculatePerimeterTriangle(double segmentLength, int iterations) {
        double p0 = 3 * segmentLength;

        double p = p0;
        if (iterations > 0) {
            p = p0 * Math.pow(4, iterations) / Math.pow(3, iterations);
        }
        return p;
    }

    /**
     * The square curve is very similar to the snowflake. The only difference is that instead of an equilateral
     * triangle, it is a equilateral square. Also that after a segment of the equilateral square is cut into three as
     * an equilateral square is formed the three segments become five. If you remember from the snowflake the three
     * segments became four. The equation to get the perimeter for this iteration is  :
     * <p/>
     * Pn = P1 (5/3)^n-1
     */
    private double calculatePerimeterSquare(double segmentLength, int iterations) {
        double p0 = 4 * segmentLength;

        double p = p0;
        if (iterations > 0) {
            p = p0 * Math.pow(5, iterations) / Math.pow(3, iterations);
        }
        return p;
    }

    @Test
    public void testTriangle1() {
        Fractals fractals = new Fractals();
        double perimeter = fractals.calculatePerimeterTriangle(9, 1);
        Assert.assertEquals(36.0, perimeter);
    }

    @Test
    public void testTriangle2() {
        Fractals fractals = new Fractals();
        double perimeter = fractals.calculatePerimeterTriangle(9, 2);
        Assert.assertEquals(48.0, perimeter);
    }


    @Test
    public void catcoderTestLevel1() {
        System.out.println("TRIANGLE");
        Fractals fractals = new Fractals();
        double perimeter1 = fractals.calculatePerimeterTriangle(243, 3);
        System.out.printf("%.0f\n", perimeter1);
        double perimeter2 = fractals.calculatePerimeterTriangle(19683, 7);
        System.out.printf("%.0f\n", perimeter2);
        double perimeter3 = fractals.calculatePerimeterTriangle(531441, 7);
        System.out.printf("%.0f\n", perimeter3);
        double perimeter4 = fractals.calculatePerimeterTriangle(531441, 9);
        System.out.printf("%.0f\n", perimeter4);
    }

    @Test
    public void testSquare1() {
        Fractals fractals = new Fractals();
        double perimeter = fractals.calculatePerimeterSquare(9, 1);
        Assert.assertEquals(60.0, perimeter);
    }

    @Test
    public void testSquare2() {
        Fractals fractals = new Fractals();
        double perimeter = fractals.calculatePerimeterSquare(9, 2);
        Assert.assertEquals(100.0, perimeter);
    }

    @Test
    public void catcoderTestLevel2() {
        System.out.println("SQUARE");
        Fractals fractals = new Fractals();
        double perimeter1 = fractals.calculatePerimeterSquare(243, 3);
        System.out.printf("%.0f\n", perimeter1);
        double perimeter2 = fractals.calculatePerimeterSquare(19683, 7);
        System.out.printf("%.0f\n", perimeter2);
        double perimeter3 = fractals.calculatePerimeterSquare(531441, 7);
        System.out.printf("%.0f\n", perimeter3);
        double perimeter4 = fractals.calculatePerimeterSquare(531441, 9);
        System.out.printf("%.0f\n", perimeter4);
    }


}
