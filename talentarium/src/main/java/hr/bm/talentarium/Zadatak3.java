package hr.bm.talentarium;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class Zadatak3 {

	final static int SUMA_PRVIH_100_BROJEVA = 5050;

	public static void main(String[] args) {
		/*
		 * Zadano polje cijelih brojeva izmedu 1 i 100, random poredanih, 
		 * pri cemu se svaki broj pojavljuje samo jednom. 
		 * No, jedan broj nedostaje.
		 */
		Set<Integer> set = new LinkedHashSet<Integer>();
		while (set.size() < 99) {
			Integer value = (int) (Math.random() * 100) + 1;
			if (value != 0) {
				set.add(value);
			}
		}

		/*
		 * Ispis sortiranog polja.
		 */
		Set<Integer> tset = new TreeSet<Integer>();
		tset.addAll(set);
		System.out.println(tset);

		/*
		 * Ispis rezultata i trajanja prve metode.
		 */
		Date start = new Date();
		System.out.println(findMissing(set));
		System.out.println("Duration in milliseconds: " + (new Date().getTime() - start.getTime()));

		System.out.println();

		/*
		 * Ispis rezultata i trajanja druge metode.
		 */
		start = new Date();
		System.out.println(findMissing2(set));
		System.out.println("Duration in milliseconds: " + (new Date().getTime() - start.getTime()));
	}

	/**
	 * Metoda trazi broj koji fali u polju cijelih brojeva izmedu 1 i 100.
	 * @param polje cijelih brojeva izmedu 1 i 100
	 * @return broj koji fali
	 */
	private static int findMissing(Set<Integer> set) {
		int sum = 0;
		for (Integer value : set) {
			sum += value;
		}
		return SUMA_PRVIH_100_BROJEVA - sum;
	}

	/**
	 * Metoda trazi broj koji fali u polju cijelih brojeva izmedu 1 i 100.
	 * @param polje cijelih brojeva izmedu 1 i 100
	 * @return broj koji fali
	 */
	private static int findMissing2(Set<Integer> set) {
		Set<Integer> tset = new TreeSet<Integer>();
		tset.addAll(set);
		int prev = 0;
		int curr = 0;
		for (Integer value : tset) {
			if (curr == 0) {
				curr = value;
			} else {
				prev = curr;
				curr = value;
				if (curr - prev == 2) {
					return curr - 1;
				}
			}
		}
		return -1;
	}
}
