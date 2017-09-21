/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import MatrixCalculator.Matrix;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static MatrixCalculator.MatrixCalculator.matrices;
import static MatrixCalculator.MatrixCalculator.solveEq;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author UZAIR
 */
public class MatrixTest {
    
    public MatrixTest() {
    }

    @Test
    public void testTranspose() {
        System.out.println("Transpose test");
        Matrix instance = new Matrix(3,3);
        double[] matA = {1,2,3,4,5,6,7,8,9}; 
        instance.setData(matA);
        double[][] expResult = {{1,4,7},{2,5,8},{3,6,9}};
        double[][] result = instance.transpose().getData();
        Assert.assertArrayEquals(expResult, result);
        
    }

    @Test
    public void testAdd() {
        System.out.println("Addition test");
        Matrix instance1 = new Matrix(3,3);
        double[] matA = {1,2,3,4,5,6,7,8,9};
        Matrix instance2 = new Matrix(3,3);
        double[] matB = {1,2,3,4,5,6,7,8,9};
        instance1.setData(matA);
        instance2.setData(matB);
        double[][] expResult = {{2,4,6},{8,10,12},{14,16,18}};
        double[][] result = instance1.Add(instance2).getData();
        Assert.assertArrayEquals(expResult, result);
    }

    @Test
    public void testSub() {
        System.out.println("Subtraction test");
        Matrix instance1 = new Matrix(3,3);
        double[] matA = {1,2,3,4,5,6,7,8,9};
        Matrix instance2 = new Matrix(3,3);
        double[] matB = {1,2,3,4,5,6,7,8,9};
        instance1.setData(matA);
        instance2.setData(matB);
        double[][] expResult = {{0,0,0},{0,0,0},{0,0,0}};
        double[][] result = instance1.Sub(instance2).getData();
        Assert.assertArrayEquals(expResult, result);
    }

    @Test
    public void testScalMul() {
        System.out.println("Scalar Multiplication test");
        Matrix instance1 = new Matrix(2,4);
        double[] matA = {1,1,1,1,1,1,1,1};
        instance1.setData(matA);
        double[][] expResult = {{2,2,2,2},{2,2,2,2}};
        double[][] result = instance1.scalMul(2).getData();
        Assert.assertArrayEquals(expResult, result);
    }

    @Test
    public void testVecMul() {
        System.out.println("Matrix Multiplication test");
        Matrix instance1 = new Matrix(1,3);
        double[] matA = {1,2,3};
        Matrix instance2 = new Matrix(3,1);
        double[] matB = {1,2,3};
        instance1.setData(matA);
        instance2.setData(matB);
        double[][] expResult = {{14}};
        double[][] result = instance1.vecMul(instance2).getData();
        Assert.assertArrayEquals(expResult, result);
    }

    @Test
    public void testInverse() {
        System.out.println("Inverse test");
        Matrix instance1 = new Matrix(2,2);
        double[] matA = {1,2,3,4};
        instance1.setData(matA);
        double[][] expResult = {{-2,1},{1.5,-0.5}};
        double[][] result = instance1.inverse().getData();
        Assert.assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testEquation() throws IOException {
        System.out.println("equation test");
        String eq="A+B";
        List<Matrix> matrices= new ArrayList<Matrix>();
        double[] matA = {1,2,3,4};
        double[] matB = {1,2,3,4};
        
        Matrix instance1 = new Matrix(2,2);
        instance1.setData(matA);
        instance1.setName("A");
        Matrix instance2 = new Matrix(2,2);
        instance2.setData(matB);
        instance2.setName("B");
        matrices.add(instance1);
        matrices.add(instance2);
        double[][] result = solveEq(eq,matrices).getData();
        double[][] expResult = {{2,4},{6,8}};
        Assert.assertArrayEquals(expResult, result);
    }
    
}
