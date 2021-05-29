import java.io.*;
import java.util.*;

public class AStarSearch extends StateOps{
    private int pathCost=0;
    private int numNodesExplored=0;
    private State goal;
    private State initialState;

    public AStarSearch(State initialState, State goal){
        super();
        this.initialState = initialState;
        this.goal = goal;
    }

    public int runAstar(){
        boolean found = false;
        boolean stillLooking = true;
        int num_moves=0;

        Set<int[]> explored = new HashSet<int[]>();

        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(20, 
                new Comparator<int[]>(){
                    public int compare(int[] i, int[] j){
                        if(fn(i, goal.getTilesArray()) > fn(j, goal.getTilesArray())){
                            return 1;
                        }
    
                        else if (fn(i, goal.getTilesArray()) < fn(j, goal.getTilesArray())){
                            return -1;
                        }
    
                        else{
                            return 0;
                        }
                     }

        });

        System.out.print("\nA* search\n---------\nInitial state:\n");
        displayState(initialState.getTilesArray());

        queue.add(initialState.getTilesArray());

        while((!queue.isEmpty())&&(!found)){
            pathCost++;
            int[] current = queue.poll();
            explored.add(current);
            numNodesExplored++;

            if(similarArrays(current, goal.getTilesArray())){
                 found = true;
                 displayReport(pathCost,numNodesExplored);
                 return 1;
            }
              

            for(int[] child: getChildren(current)){
                if(explored.contains(child) && (fn(current, goal.getTilesArray()) >= fn(child, goal.getTilesArray())))
                    continue;

                else if((!queue.contains(child)) || (fn(current, goal.getTilesArray())) < fn(child, goal.getTilesArray())){
                    queue.add(child);
                }
            }


        }
                
        return -1;
    }

    public int[] toExtendNext(int[] parent){
        int[] x = (getChildren(parent)).get(0);
        int min_fn= fn(x, goal.getTilesArray());

        for(int[] child:getChildren(parent)){
            if(fn(child, goal.getTilesArray()) < min_fn)
               x = child;
        }
        return x;
    }

    public int fn(int[] current, int[] goal){
        return gn() + hn(current, goal); 
    }

    private int gn(){
        return pathCost;
    }

    private int hn(int[] current, int[] goal){
        int num_misplaced=0;
        
        for(int i = 0; i < current.length; i++){
            if(current[i] != goal[i] && current[i]!=0)
                 num_misplaced++;
        }
        
        return num_misplaced;   
    }
}