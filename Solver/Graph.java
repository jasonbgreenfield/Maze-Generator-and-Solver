import java.lang.Math;
public class Graph {

    int numNodes;
    Node[] nodes;
    int[][] aM;

    public void addEdge(int i, int j) {
    
        if(i < numNodes && j < numNodes && i != j){
            aM[i][j] = 1;
            aM[j][i] = 1;
        }

	return;
    }
    
    public boolean edgeExist(int i, int j){

        if( aM[i][j] == 1)
            return true;

        return false;

    }

    public Graph(int num) {
    
	 numNodes = num;

	 aM = new int[num][num];
	
	 nodes = new Node[numNodes];
        
        for(int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i);
        }

    }

}






