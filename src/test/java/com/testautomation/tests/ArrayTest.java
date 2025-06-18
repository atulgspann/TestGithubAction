package com.testautomation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Test class for array manipulation functionality
 * Contains smoke and regression test cases
 */
public class ArrayTest {
    
    /**
     * Smoke test: Verify array creation and access
     */
    @Test(groups = {"smoke", "array"})
    public void testArrayCreationAndAccess() {
        int[] numbers = {1, 2, 3, 4, 5};
        int expectedLength = 5;
        int expectedFirstElement = 1;
        int expectedLastElement = 5;
        
        Assert.assertEquals(numbers.length, expectedLength, 
            "Array length test failed: array should have " + expectedLength + " elements");
        Assert.assertEquals(numbers[0], expectedFirstElement, 
            "First element test failed: should be " + expectedFirstElement);
        Assert.assertEquals(numbers[numbers.length - 1], expectedLastElement, 
            "Last element test failed: should be " + expectedLastElement);
    }
    
    /**
     * Smoke test: Verify array sorting
     */
    @Test(groups = {"smoke", "array"})
    public void testArraySorting() {
        int[] numbers = {5, 2, 8, 1, 9};
        int[] expectedSorted = {1, 2, 5, 8, 9};
        
        Arrays.sort(numbers);
        
        Assert.assertEquals(numbers, expectedSorted, 
            "Array sorting test failed: array should be sorted in ascending order");
    }
    
    /**
     * Regression test: Verify array search
     */
    @Test(groups = {"regression", "array"})
    public void testArraySearch() {
        int[] numbers = {10, 20, 30, 40, 50};
        int searchValue = 30;
        int expectedIndex = 2;
        
        int actualIndex = Arrays.binarySearch(numbers, searchValue);
        
        Assert.assertEquals(actualIndex, expectedIndex, 
            "Array search test failed: " + searchValue + " should be at index " + expectedIndex);
    }
    
    /**
     * Regression test: Verify array copy
     */
    @Test(groups = {"regression", "array"})
    public void testArrayCopy() {
        int[] original = {1, 2, 3, 4, 5};
        int[] copy = Arrays.copyOf(original, original.length);
        
        Assert.assertEquals(copy.length, original.length, 
            "Array copy length test failed: copy should have same length as original");
        Assert.assertEquals(copy, original, 
            "Array copy test failed: copy should be identical to original");
        
        // Verify they are different objects
        Assert.assertNotSame(copy, original, 
            "Array copy test failed: copy should be a different object reference");
    }
    
    /**
     * Regression test: Verify array fill
     */
    @Test(groups = {"regression", "array"})
    public void testArrayFill() {
        int[] numbers = new int[5];
        int fillValue = 10;
        int[] expectedFilled = {10, 10, 10, 10, 10};
        
        Arrays.fill(numbers, fillValue);
        
        Assert.assertEquals(numbers, expectedFilled, 
            "Array fill test failed: all elements should be " + fillValue);
    }
    
    /**
     * Regression test: Verify array equality
     */
    @Test(groups = {"regression", "array"})
    public void testArrayEquality() {
        int[] array1 = {1, 2, 3, 4, 5};
        int[] array2 = {1, 2, 3, 4, 5};
        int[] array3 = {1, 2, 3, 4, 6};
        
        Assert.assertTrue(Arrays.equals(array1, array2), 
            "Array equality test failed: identical arrays should be equal");
        Assert.assertFalse(Arrays.equals(array1, array3), 
            "Array equality test failed: different arrays should not be equal");
    }
} 