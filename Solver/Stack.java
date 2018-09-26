import java.util.Vector;
public class Stack<E> {

    //private Integer[] list;
    public Vector<E> list; 

    public int size() {
    
	return list.size();
  
    }

    public boolean isEmpty() {
    
	return list.isEmpty();
  
    }

    public void push(E toAdd) {
    
	//if(top == list.size() ) resize();
	
	list.add(toAdd);

    }

    public E pop() {
    
        if(list.size() == 0){
                return null;
        }
  
        E toReturn =  list.get(list.size() - 1);

        list.remove(list.size()-1);

        return toReturn;

    }

    public E peek() {
	
      return list.get(list.size() - 1);
    
    }

    private void resize() {
	
      Vector<E> temp = new Vector<E>()[top * 2];

      for(int i = 0; i < top; i++) {
          temp[i] = list[i];
      }
	
      list = temp;

      return;
  
    }

    public void print() {
	
        System.out.print("[");

        for(int i = list.size() - 1; i >= 0; i--) {
            if(list.get(i) == null) continue;
            System.out.print(list.get(i) + " ");
        }

        System.out.println("]");

    }

    public Stack() {
	
      list = new Vector<E>();

    }

}
