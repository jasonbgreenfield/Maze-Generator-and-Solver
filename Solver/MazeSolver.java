import java.io.*;
import java.lang.Math;

public class MazeSolver {

    public static int jNumNodes;

    public void run(String filename) throws IOException {

	// read the input file to extract relevant information about the maze
	String[] readFile = parse(filename);
	int mazeSize = Integer.parseInt(readFile[0]);
	int numNodes = mazeSize*mazeSize;

	jNumNodes = numNodes;
	
	String mazeData = readFile[1];

	// construct a maze based on the information read from the file
	Graph mazeGraph = buildGraph(mazeData, numNodes);

	Node[] solution = solve(mazeGraph);

	Node temp = mazeGraph.nodes[jNumNodes-1];
	mazeGraph.nodes[0].inSolution = true;

	while(temp.parent != null){
	    temp.inSolution = true;
	    temp = temp.parent;
	}


	// print out the final maze with the solution path
	printMaze(mazeGraph.nodes, mazeData, mazeSize);
    }

    // prints out the maze
    // includes the final path from entrance to exit, if one has been recorded,
    // and which cells have been visited, if this has been recorded
    public void printMaze(Node[] mazeCells, String mazeData, int mazeSize) {
	
	int ind = 0;
	int inputCtr = 0;

	System.out.print("+");
	for(int i = 0; i < mazeSize; i++) {
	    System.out.print("--+");
	}
	System.out.println();

	for(int i = 0; i < mazeSize; i++) {
	    if(i == 0) System.out.print(" ");
	    else System.out.print("|");

	    for(int j = 0; j < mazeSize; j++) {
		System.out.print(mazeCells[ind] + "" + mazeCells[ind] +  mazeData.charAt(inputCtr));
		inputCtr++;
		ind++;
	    }
	    System.out.println();

	    System.out.print("+");
	    for(int j = 0; j < mazeSize; j++) {
		System.out.print(mazeData.charAt(inputCtr) + "" +  mazeData.charAt(inputCtr) + "+");
		inputCtr++;
	    }
	    System.out.println();
	}
	
    }

    // reads in a maze from an appropriately formatted file from the generator
    // returns an array of Strings, where position 0 stores the size of the maze
    // grid (i.e., the length/width of the grid) and position 1 stores minimal 
    // information about which walls exist
    public String[] parse(String filename) throws IOException {
	FileReader fr = new FileReader(filename);

	// determine size of maze
	int size = 0;
	int nextChar = fr.read();
	while(nextChar >= 48 && nextChar <= 57) {
	    size = 10*size + nextChar - 48;
	    nextChar = fr.read();
	}

	String[] result = new String[2];
	result[0] = size + "";
	result[1] = "";


	// skip over up walls on first row
	for(int j = 0; j < size; j++) {
	    fr.read();
	    fr.read();
	    fr.read();
	}
	fr.read();
	fr.read();

	for(int i = 0; i < size; i++) {
	    // skip over left wall on each row
	    fr.read();
	    
	    for(int j = 0; j < size; j++) {
		// skip over two spaces for the cell
		fr.read();
		fr.read();

		// read wall character
		nextChar = fr.read();
		result[1] = result[1] + (char)nextChar;

	    }
	    // clear newline character at the end of the row
	    fr.read();
	    
	    // read down walls on next row of input file
	    for(int j = 0; j < size; j++)  {
		// skip over corner
		fr.read();
		
		//skip over next space, then handle wall
		fr.read();
		nextChar = fr.read();
		result[1] = result[1] + (char)nextChar;
	    }

	    // clear last wall and newline character at the end of the row
	    fr.read();
	    fr.read();
	    
	}

	return result;
    }

  
    
    public Graph buildGraph(String maze, int numNodes) {

	Graph mazeGraph = new Graph(numNodes);
	int size = (int)Math.sqrt(numNodes);

	int mazeInd = 0;
	for(int i = 0; i < size; i++) {
	    // create edges for right walls in row i
	    for(int j = 0; j < size; j++) {
		char nextChar = maze.charAt(mazeInd);
		mazeInd++;
		if(nextChar == ' ') {
		    // add an edge corresponding to a right wall, using the 
		    // indexing convention for nodes
		    mazeGraph.addEdge(size*i + j, size*i + j + 1);
		}
	    }

	    // create edges for down walls below row i
	    for(int j = 0; j < size; j++)  {
		char nextChar = maze.charAt(mazeInd);
		mazeInd++;
		if(nextChar == ' ') {
		    // add an edge corresponding to a down wall, using the 
		    // indexing convention for nodes
		    mazeGraph.addEdge(size*i + j, size*(i+1) + j);
		}
	    }    
	}

	return mazeGraph;
    }


    public Node[] solve(Graph g){
	Node[] toReturn = new Node[jNumNodes];

	return DFS(g, g.nodes[0]);

    }

    public Node[] DFS(Graph g, Node n){
	Node[] toReturn = new Node[jNumNodes];

	Stack<Node> toVisit = new Stack<Node>();

	toVisit.push(n);

	Node temp = new Node(0);

        g.nodes[0].visited = true;

	    while(!toVisit.isEmpty()){
		temp = toVisit.pop();

		for(int i = 0; i < jNumNodes; i++){
		    if( g.edgeExist(temp.index, g.nodes[i].index)){
			    if(!g.nodes[i].visited){
				toVisit.push(g.nodes[i]);
				g.nodes[i].parent = temp;
				g.nodes[i].visited = true;
			    }
			}

		}
	    }
	    for(int i = 0; i < toVisit.list.size(); i++){
		toReturn[i] = toVisit.pop();
	    }
	    return toReturn;
    }
    
    public static void main(String [] args) {
	if(args.length < 1) {
	    System.out.println("USAGE: java MazeSolver <filename>");
	}
	else{
	    try{
		new MazeSolver().run(args[0]);
	    }
	    catch(IOException e) {
		e.printStackTrace();
	    }
	}
    }

}
