package examen1.pratique03;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.examen.Bean;

public class Pratique03 {
	public static void main(String[] args) {
		Collection set = new HashSet<>();
		Collection liste = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		System.out.println(typeCollection(set));
		System.out.println(typeCollection(liste));
		System.out.println(typeCollection(map));
		System.out.println(typeCollection(new Bean()));
	}
	public static String typeCollection(Object c) {
		String string = (c.getClass()+"").substring(16);
		if(string.contains("List")) {
			return "C'est une liste";
		}else if(string.contains("Set")) {
			return "C'est un set";
		}else if(string.contains("Map")) {
			return "C'est une map";
		}else {
			return "Ce n'est ni une liste, ni un set, ni une map.";
		}
	}
}
