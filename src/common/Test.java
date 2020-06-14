package common;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		ArrayList<String> s = new ArrayList<String>();
		s.toArray(new String[s.size()]);
		
		System.out.println(s);
	}

}
