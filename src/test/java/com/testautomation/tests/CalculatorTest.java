package com.testautomation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for calculator functionality
 * Contains smoke and regression test cases
 */
public class CalculatorTest {
    
    /**
     * Smoke test: Verify basic addition operation
     */
    @Test(groups = {"smoke", "calculator"})
    public void testAddition() {
        int a = 5;
        int b = 3;
        int expectedResult = 8;
        
        int actualResult = a + b;
        
        Assert.assertEquals(actualResult, expectedResult, 
            "Addition test failed: " + a + " + " + b + " should equal " + expectedResult);
    }
    
    /**
     * Smoke test: Verify basic subtraction operation
     */
    @Test(groups = {"smoke", "calculator"})
    public void testSubtraction() {
        int a = 10;
        int b = 4;
        int expectedResult = 6;
        
        int actualResult = a - b;
        
        Assert.assertEquals(actualResult, expectedResult, 
            "Subtraction test failed: " + a + " - " + b + " should equal " + expectedResult);
    }
    
    /**
     * Regression test: Verify multiplication operation
     */
    @Test(groups = {"regression", "calculator"})
    public void testMultiplication() {
        int a = 6;
        int b = 7;
        int expectedResult = 42;
        
        int actualResult = a * b;
        
        Assert.assertEquals(actualResult, expectedResult, 
            "Multiplication test failed: " + a + " * " + b + " should equal " + expectedResult);
    }
    
    /**
     * Regression test: Verify division operation
     */
    @Test(groups = {"regression", "calculator"})
    public void testDivision() {
        int a = 20;
        int b = 4;
        int expectedResult = 5;
        
        int actualResult = a / b;
        
        Assert.assertEquals(actualResult, expectedResult, 
            "Division test failed: " + a + " / " + b + " should equal " + expectedResult);
    }
    
    /**
     * Regression test: Verify division by zero handling
     */
    @Test(groups = {"regression", "calculator"}, expectedExceptions = ArithmeticException.class)
    public void testDivisionByZero() {
        int a = 10;
        int b = 0;
        
        // This should throw ArithmeticException
        int result = a / b;
    }
    
    /**
     * Regression test: Verify negative number operations
     */
    @Test(groups = {"regression", "calculator"})
    public void testNegativeNumbers() {
        int a = -5;
        int b = 3;
        int expectedResult = -2;
        
        int actualResult = a + b;
        
        Assert.assertEquals(actualResult, expectedResult, 
            "Negative number test failed: " + a + " + " + b + " should equal " + expectedResult);
    }
} 