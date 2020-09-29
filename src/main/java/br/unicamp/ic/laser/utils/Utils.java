package br.unicamp.ic.laser.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Utils {
	public static ArrayList<ArrayList<String>> pairs(List<String> list) {
		int size = list.size();
		ArrayList<ArrayList<String>> returned = new ArrayList<ArrayList<String>>();
		
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				ArrayList<String> l = new ArrayList<String>();
				l.add(list.get(i));
				l.add(list.get(j));
				
				returned.add(l);
			}
		}
		
		return returned;		
	}
}
