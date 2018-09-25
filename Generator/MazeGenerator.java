import java.lang.Math;
import java.util.Random;

public class MazeGenerator {

    public void run(int n) {

        // creates all cells
        Cell[][] mazeMap = new Cell[n][n];
        initializeCells(mazeMap);

        // create a list of all internal walls, and links the cells and walls
        Wall[] walls = getWalls(mazeMap);

        //printMaze(mazeMap);

        System.out.println(n);
        createMaze(walls, mazeMap);

    }

    public void createMaze(Wall[] walls, Cell[][] map){
        UnionFind jlist = new UnionFind();

        Random rand = new Random();

        boolean stillRun = true;
        int numRight = 0;


        while(stillRun){
            int  n = rand.nextInt(walls.length-1) + 1;
            if(walls[n].first != null && walls[n].second != null){

                if(jlist.find(walls[n].first) != jlist.find(walls[n].second)  ){
                    jlist.union(walls[n].first, walls[n].second);
                    walls[n].visible = false;
                }
            }

            //checks to see if every cell is in the same path/unionfind
            for(int i = 0; i < map.length; i++){
                for(int j = 0; j < map[0].length; j++){
                    if(jlist.find(map[0][0]) != jlist.find(map[i][j])){
                      stillRun = true;		       
                    }
                    if(jlist.find(map[0][0]) == jlist.find(map[i][j]) ){
                      numRight++;		       
                    }
                }
            }
            if(numRight == (map.length*map[0].length)){
                stillRun = false;
            }
            numRight = 0;

	      }

	      printMaze(map);
    }


    // print out the maze in a specific format
    public void printMaze(Cell[][] maze) {
	      for(int i = 0; i < maze.length; i++) {
            // print the up walls for row i
            for(int j = 0; j < maze.length; j++) {
                Wall up = maze[i][j].up;
                if(up != null && up.visible) System.out.print("+--");
                else System.out.print("+  ");
            }
	          System.out.println("+");

	          // print the left walls and the cells in row i
	          for(int j = 0; j < maze.length; j++) {
		            Wall left = maze[i][j].left;
		            if(left != null && left.visible) System.out.print("|  ");
		            else System.out.print("   ");
	          }

	          //print the last wall on the far right of row i
	          Wall lastRight = maze[i][maze.length-1].right;
	          if(lastRight != null && lastRight.visible) System.out.println("|");
	          else System.out.println(" ");
	       }

	      // print the last row's down walls
	      for(int i = 0; i < maze.length; i++) {
	          Wall down = maze[maze.length-1][i].down;
	          if(down != null && down.visible) System.out.print("+--");
	          else System.out.print("+  ");
	      }
	      System.out.println("+");

    }

    // create a new Cell for each position of the maze
    public void initializeCells(Cell[][] maze) {
	      for(int i = 0; i < maze.length; i++) {
	          for(int j = 0; j < maze[0].length; j++) {
		            maze[i][j] = new Cell();
	          }
	      }
    }

    // create all walls and link walls and cells
    public Wall[] getWalls(Cell[][] mazeMap) {

	      int n = mazeMap.length;

	      Wall[] walls = new Wall[2*n*(n+1)];
	      int wallCtr = 0;

	      // each "inner" cell adds its right and down walls
	      for(int i = 0; i < n; i++) {
	          for(int j = 0; j < n; j++) {
		            // add down wall
                if(i < n-1) {
                    walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i+1][j]);
                    mazeMap[i][j].down = walls[wallCtr];
                    mazeMap[i+1][j].up = walls[wallCtr];
                    wallCtr++;
                }
		
                // add right wall
                if(j < n-1) {
                    walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i][j+1]);
                    mazeMap[i][j].right = walls[wallCtr];
                    mazeMap[i][j+1].left = walls[wallCtr];
                    wallCtr++;
                }
	           }
	       }


          // "outer" cells add their outer walls
          for(int i = 0; i < n; i++) {
              // add left walls for the first column
              walls[wallCtr] = new Wall(null, mazeMap[i][0]);
              mazeMap[i][0].left = walls[wallCtr];
              if(i == 0){
              walls[wallCtr].visible = false;
              }
              wallCtr++;

              // add up walls for the top row
              walls[wallCtr] = new Wall(null, mazeMap[0][i]);
              mazeMap[0][i].up = walls[wallCtr];
              wallCtr++;

              // add down walls for the bottom row
              walls[wallCtr] = new Wall(null, mazeMap[n-1][i]);
              mazeMap[n-1][i].down = walls[wallCtr];
              wallCtr++;

              // add right walls for the last column
              walls[wallCtr] = new Wall(null, mazeMap[i][n-1]);
              mazeMap[i][n-1].right = walls[wallCtr];
              wallCtr++;
	        }

       	  walls[wallCtr-1].visible = false;

	        return walls;
    }


    public static void main(String [] args) {
	      if(args.length > 0) {
          int n = Integer.parseInt(args[0]);
          new MazeGenerator().run(n);
	      }
	      else new MazeGenerator().run(5);
    }

}
