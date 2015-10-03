//CHRIS TUFENKJIAN, 102-350-610
//PROJECT 5
//COMP 282
//DUE 12/09/2014
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Graph {
	
	private int[][] adjMatrix;
    private int[] visited;
    private int[] paths;  
    private int number_of_vertices = 0;
    private int number_of_paths = 0;
    
	public Graph (String inputFileName) throws FileNotFoundException  
	{  
		Scanner file_reader = new Scanner (new File (inputFileName));
		number_of_vertices = file_reader.nextInt();
		set_matrix_values_to_zero();
		readInputData(file_reader);	
	}
	
	public void printGraph()
	{
		System.out.println( "Vertices: " + number_of_vertices);
		System.out.println( "Paths: " + number_of_paths + "\n");
		int count = 0;
		for (int i = 0; i < number_of_vertices; i++){
			for (int j = 0; j < number_of_vertices; j++){
				System.out.print(adjMatrix[i][j] + "   ");
				count++;
				if (count % number_of_vertices == 0){
					System.out.println();
				}
			}	
		}
	System.out.println();
	}
	
	public void bfsTraversal (int vertex)   
	{ 	
		System.out.println("Printing BFS Traversal, vertex = " + vertex);
		set_vertices_to_not_visited();
		Queue<Integer> q = new LinkedList<Integer>();
		visited[vertex] = 1;	
		q.add(vertex);
		int w  = 0;
		while (!q.isEmpty()){
			w = q.poll();
			System.out.print(w + ", ");
			for (int i = 0; i < number_of_vertices; i++)
			{
				if (adjMatrix[w][i] == 1 && visited[i] == 0)
				{
					visited[i] = 1;
					q.add(i);
				}
			}			
		}
		System.out.println("\n");
	}
	 
	
	public void dfsTraversal ( int vertex)  
	{  
		System.out.println("Printing DFS Traversal, vertex = " + vertex);
		set_vertices_to_not_visited();
		dfsTraversal_recursive(vertex);
		System.out.println("\n");
	} 
		
	public int[] findShortestPaths (int vertex )
	{ 
		set_vertices_to_not_visited();
		set_paths_to_not_visited();
		Queue<Integer> q = new LinkedList<Integer>();
		visited[vertex] = 1;
		q.add(vertex);
		int w = 0;
		while(!q.isEmpty()){
			w = q.poll();
			for (int i = 0; i < number_of_vertices; i++)
			{
				if (adjMatrix[w][i] == 1 && visited[i] == 0)
				{
					visited[i] = 1;
					paths[i] = w;
					q.add(i);
				}
			}				
		}
		return paths;
	}
	
	public void printShortestPaths( int vertex)
	{  
		findShortestPaths(vertex);
		Stack<Integer> s = new Stack<Integer>();
		System.out.print("The shortest paths from vertex " + vertex + " to: \n");
		for (int i = 0; i < number_of_vertices; i++)
		{		
			int j = i;
			System.out.print("Vertex " + j + " is: ");
			while(paths[j] != -1)
			{	
				s.push(j);
				j = paths[j];
			}
			s.push(vertex);

			while(!s.isEmpty())
			{
				System.out.print(s.pop() + " ");
			}
			System.out.println();
		}
	} 
	
	public boolean existsPath(int x, int y) 
	{ 	
		set_vertices_to_not_visited();
		visited[x] = 1;
		return path(x,y);
	}
	
	public boolean existsTriangle() 
	{
		set_vertices_to_not_visited();
		boolean tri_path_exists = false;
		for (int i = 0; i < number_of_vertices; i++)
		{
			for (int j = 0; j < number_of_vertices; j++)
			{
				if (adjMatrix[i][j] != 0)
				{
					for (int k = 0; k < number_of_vertices; k++)
						if (adjMatrix[j][k] != 0 && adjMatrix[k][i] != 0)
						{
							if (visited[i] + visited[j] + visited[k] <3)
							{
								System.out.printf("There is a triangular path between %d, %d, %d.\n",i,j,k );
							}
							tri_path_exists = true;
							visited[i] = 1;
							visited[j] = 1;
							visited[k] = 1;
						}
				}
			}
		}
		
		if(tri_path_exists == false)
		{
			System.out.println("No triangular path exists.");
		}
		return tri_path_exists;  	
	}
	
	//private methods
	private void  readInputData(Scanner file_reader)
	{  
		int odd_even = 0; // even == STARTING VERTEX, odd == DESTINATION VERTEX
		int from = 0;
		int to = 0;
		while (file_reader.hasNextInt()){
			if (odd_even % 2 == 0 && odd_even != 0)
			{
				adjMatrix[from][to] = 1;
				number_of_paths++;
			}
			if (odd_even % 2 ==  0)	
			{
				from = file_reader.nextInt();
			}
			else 
			{
				to = file_reader.nextInt();
			}
			odd_even++;
		}
		adjMatrix[from][to] = 1;
		number_of_paths++;
	}
	
	private void dfsTraversal_recursive (int current_vertex)
	{
		visited[current_vertex] = 1;
		System.out.print(current_vertex + ", ");
		for (int i = 0; i < number_of_vertices; i++)
		{
			if (adjMatrix[current_vertex][i] == 1 && visited[i] == 0)
			{
				dfsTraversal_recursive(i);		
			}
		}
	}
	
	private void set_matrix_values_to_zero()
	{
		adjMatrix = new int [number_of_vertices][number_of_vertices];
		for (int i = 0; i < number_of_vertices; i++)
		{
			for (int j = 0; j < number_of_vertices; j++)
			{
				adjMatrix[i][j] = 0;
			}
		}
	}
	
	private void set_vertices_to_not_visited()
	{
		visited = new int [number_of_vertices];
		for (int i = 0; i < number_of_vertices; i++)
		{
			visited[i] = 0;
		}
	}
	
	private void set_paths_to_not_visited()
	{
		paths = new int [number_of_vertices];
		for (int i = 0; i < number_of_vertices; i++)
		{
			paths[i] = -1;
		}
	}
		
	private boolean path(int x, int y)
	{
		if ( x == y)
		{
			return true;
		}
		else
		{
			for (int i = 0; i < number_of_vertices; i++)
			{
				if (adjMatrix[x][i] == 1 && visited[i] == 0)
				{
					visited[i] = 1;
					if(path (i,y))
					{
						return true;
					}
				}
			}			
		}
		return false;
	}
}

class TestGraphPosted
{
   public static void main( String[] args)throws Exception
   {
      Graph g1 = new Graph("Graph2.txt");
      g1.printGraph();
      g1.dfsTraversal(0);
      g1.dfsTraversal(4);
      g1.bfsTraversal(5);
      g1.bfsTraversal(4);
      g1.bfsTraversal(0);
      g1.printShortestPaths(4);
      g1.printShortestPaths(5);
      
      int x = 2;
      int y = 4;
      if( g1.existsPath(x,y)) 
      {
    	 System.out.println("There exists a path from " + x + " to " + y);
      }
      else
      {
         System.out.println("There is no path from " + x + " to " + y); 
      }     
      x = 5;
      y = 2;
      if( g1.existsPath(x,y))
      {
         System.out.println("There exists a path from " + x + " to " + y);
      }
      else
      {
         System.out.println("There is no path from " + x + " to " + y); 
   	  }
      g1.existsTriangle();
   }
}