import java.util.LinkedList;
import java.util.*;

public class Node {

    int index;
    Node up;
    Node down;
    Node left;
    Node right;

    Node parent;

    Node next;
 
    ArrayList<Integer> edges;

    boolean visited = false;
    boolean inSolution = false;

    static final String PATH = "X";
    static final String VISIT = ".";
    static final String NOT_VISIT = " ";

    public String toString() {
	if(visited) {
	    if(inSolution) return PATH;
	    else return VISIT;
	}
	else return NOT_VISIT;
    }

    public Node(int i) {
	index = i;
	edges = new ArrayList<Integer>();
    }

}
