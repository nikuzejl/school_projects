import java.io.*;
import java.util.*;

public class BfsSearch extends StateOps{
    private int pathCost =0;
    private int numNodesExplored =0;
    private State goal;
    private State initialState;

    public BfsSearch(State initialState, State goal){
        super();
        this.initialState = initialState;
        this.goal = goal;
    }

    public int runBfs(){
        int[] current = initialState.getTilesArray();
        Queue<int[]> frontier = new LinkedList<>();
        LinkedList<int[]> explored = new LinkedList<>();

        frontier.add(current);
        explored.add(current);
        numNodesExplored++;

        System.out.print("BFS search\n-----------\nInitial state\n");
        displayState(current);

        if(similarArrays(current, goal.getTilesArray())){
            displayReport(pathCost,numNodesExplored);
            return 1;
        }

        while(!frontier.isEmpty()){
            current = frontier.remove();
            pathCost ++;
            
            for(int[] x : getChildren(current)){
                if(similarArrays(x,goal.getTilesArray())){
                    displayReport(pathCost,numNodesExplored);
                    return 1;
                }

                if(!explored.contains(x)){
                    explored.add(x);
                    numNodesExplored++;
                    frontier.add(x);
                }
                
                else
                  System.out.println("Already explored" + "\n" + displayState(x));
            } 
        }

        return -1;
    }

    public int getPathCost(){
        return pathCost;
    }
    
}


