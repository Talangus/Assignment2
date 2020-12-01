package bgu.spl.mics.application;

import bgu.spl.mics.application.misc.CircularIterator;

import java.util.LinkedList;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		CircularIterator<Integer> iter = new CircularIterator<>(list);
		for (int i = 0; i<10 ; i++){
			System.out.println(iter.next());

		}
	}
}
