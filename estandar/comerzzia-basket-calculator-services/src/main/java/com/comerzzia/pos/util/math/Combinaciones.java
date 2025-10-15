package com.comerzzia.pos.util.math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Combinaciones {

	public static List<int[]> generarPosiciones(int numeroElementos, int numeroElementosAEscoger) {
		List<int[]> combinations = new ArrayList<>();
		helper(combinations, new int[numeroElementosAEscoger], 0, numeroElementos - 1, 0);
		return combinations;
	}

	private static void helper(List<int[]> combinations, int data[], int start, int end, int index) {
		if (index == data.length) {
			int[] combination = data.clone();
			combinations.add(combination);
		}
		else if (start <= end) {
			data[index] = start;
			helper(combinations, data, start + 1, end, index + 1);
			helper(combinations, data, start + 1, end, index);
		}
	}
	
	public static <T> Set<List<T>> getCombinaciones(List<List<T>> lists) {
	    Set<List<T>> combinations = new HashSet<List<T>>();
	    Set<List<T>> newCombinations;

	    int index = 0;

	    // extract each of the integers in the first list
	    // and add each to ints as a new list
	    for(T i: lists.get(0)) {
	        List<T> newList = new ArrayList<T>();
	        newList.add(i);
	        combinations.add(newList);
	    }
	    index++;
	    while(index < lists.size()) {
	        List<T> nextList = lists.get(index);
	        newCombinations = new HashSet<List<T>>();
	        for(List<T> first: combinations) {
	            for(T second: nextList) {
	                List<T> newList = new ArrayList<T>();
	                newList.addAll(first);
	                newList.add(second);
	                newCombinations.add(newList);
	            }
	        }
	        combinations = newCombinations;

	        index++;
	    }

	    return combinations;
	}
	
	public static <T> Set<List<T>> getCombinacionesOld(List<List<List<T>>> lists) {
		Set<List<T>> combinations = new HashSet<List<T>>();
		Set<List<T>> newCombinations;
		
		int index = 0;
		
		// extract each of the integers in the first list
		// and add each to ints as a new list
		for (List<T> i : lists.get(0)) {
			List<T> newList = new ArrayList<T>();
			newList.addAll(i);
			combinations.add(newList);
		}
		index++;
		while (index < lists.size()) {
			List<List<T>> nextList = lists.get(index);
			newCombinations = new HashSet<List<T>>();
			for (List<T> first : combinations) {
				for (List<T> second : nextList) {
					List<T> newList = new ArrayList<T>();
					newList.addAll(first);
					newList.addAll(second);
					newCombinations.add(newList);
				}
			}
			combinations = newCombinations;
			
			index++;
		}
		
		return combinations;
	}

}
