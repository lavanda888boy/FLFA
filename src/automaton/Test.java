package automaton;

import java.util.HashSet;
import java.util.Set;

public class Test {
    
    public static void main(String[] args) {
        Set<String> s = new HashSet<>();
        s.add("q1");
        System.out.println(String.join(", ", s));
    }
}
