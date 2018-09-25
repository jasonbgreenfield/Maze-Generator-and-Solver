public class UnionFind{

    public void makeset(Cell c){
	      c.head = new LLAddOnly();
	      c.head.add(c);
    }

    public LLAddOnly find(Cell c){
	      if(c.head == null){
	          makeset(c);
	      }
	      return c.head;
    }

    public void union(Cell a, Cell b){

	      a.head.last.next = b.head.first;
	      a.head.last = b.head.last;
	      b.head.first = a.head.first;
	      b.head = a.head;
	
	
	      Cell temp = b.head.first;
	      while(temp != null){	   
	          temp.head = a.head;
	          temp = temp.next;
	      }
	
    }

}


