package br.unicamp.ic.laser.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class PairsTest {
	@Test
    public void should_not_return_null(){
		ArrayList<ArrayList<String>> result = Utils.pairs(Arrays.asList("a", "b", "c"));

        Assert.assertNotNull(result);
    }
	
    @Test
    public void should_return_three_pairs(){
    	ArrayList<ArrayList<String>> actual = Utils.pairs(Arrays.asList("a", "b", "c"));
    	ArrayList<ArrayList<String>> expect = new ArrayList<ArrayList<String>>();

    	ArrayList<String> arrayA = new ArrayList<String>();
    	arrayA.add("a");
    	arrayA.add("b");
    	
    	ArrayList<String> arrayB = new ArrayList<String>();
    	arrayB.add("a");
    	arrayB.add("c");
    	
    	ArrayList<String> arrayC = new ArrayList<String>();
    	arrayC.add("b");
    	arrayC.add("c");

    	expect.add(arrayA);
    	expect.add(arrayB);
    	expect.add(arrayC);
    	
        Assert.assertEquals(expect, actual);
    }

    @Test
    public void should_return_empty() {
    	ArrayList<ArrayList<String>> actual = Utils.pairs(Arrays.asList());
        ArrayList<ArrayList<String>> expect = new ArrayList<ArrayList<String>>();

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void should_return_two() {
    	ArrayList<ArrayList<String>> actual = Utils.pairs(Arrays.asList("0", "1"));
        ArrayList<ArrayList<String>> expect = new ArrayList<ArrayList<String>>();
        
        ArrayList<String> arrayA = new ArrayList<String>();
    	arrayA.add("0");
    	arrayA.add("1");
    	
    	expect.add(arrayA);
    	
        Assert.assertEquals(expect, actual);
    }
}
