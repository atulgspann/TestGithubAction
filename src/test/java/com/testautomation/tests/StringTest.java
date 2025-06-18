package com.testautomation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for string manipulation functionality
 * Contains smoke and regression test cases
 */
public class StringTest {
    
    /**
     * Smoke test: Verify string concatenation
     */
    @Test(groups = {"smoke", "string"})
    public void testStringConcatenation() {
        String firstName = "John";
        String lastName = "Doe";
        String expectedResult = "John Doe";
        
        String actualResult = firstName + " " + lastName;
        
        Assert.assertEquals(actualResult, expectedResult, 
            "String concatenation test failed: " + firstName + " + " + lastName + " should equal " + expectedResult);
    }
    
    /**
     * Smoke test: Verify string length
     */
    @Test(groups = {"smoke", "string"})
    public void testStringLength() {
        String testString = "Hello World";
        int expectedLength = 11;
        
        int actualLength = testString.length();
        
        Assert.assertEquals(actualLength, expectedLength, 
            "String length test failed: " + testString + " should have length " + expectedLength);
    }
    
    /**
     * Regression test: Verify string to uppercase conversion
     */
    @Test(groups = {"regression", "string"})
    public void testStringToUpperCase() {
        String testString = "hello world";
        String expectedResult = "HELLO WORLD";
        
        String actualResult = testString.toUpperCase();
        
        Assert.assertEquals(actualResult, expectedResult, 
            "String toUpperCase test failed: " + testString + " should become " + expectedResult);
    }
    
    /**
     * Regression test: Verify string to lowercase conversion
     */
    @Test(groups = {"regression", "string"})
    public void testStringToLowerCase() {
        String testString = "HELLO WORLD";
        String expectedResult = "hello world";
        
        String actualResult = testString.toLowerCase();
        
        Assert.assertEquals(actualResult, expectedResult, 
            "String toLowerCase test failed: " + testString + " should become " + expectedResult);
    }
    
    /**
     * Regression test: Verify string contains method
     */
    @Test(groups = {"regression", "string"})
    public void testStringContains() {
        String testString = "Hello World";
        String searchString = "World";
        
        boolean containsResult = testString.contains(searchString);
        
        Assert.assertTrue(containsResult, 
            "String contains test failed: " + testString + " should contain " + searchString);
    }
    
    /**
     * Regression test: Verify string trim method
     */
    @Test(groups = {"regression", "string"})
    public void testStringTrim() {
        String testString = "  Hello World  ";
        String expectedResult = "Hello World";
        
        String actualResult = testString.trim();
        
        Assert.assertEquals(actualResult, expectedResult, 
            "String trim test failed: " + testString + " should become " + expectedResult);
    }
} 