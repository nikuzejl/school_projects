import java.util.*;

public class StateOps {

    protected void displayReport(int pathCost, int numExplored){
        System.out.print("Number of moves: " + pathCost + "\n");
        System.out.print("Number of nodes explored moves: " + numExplored + "\n");
    }
    protected ArrayList<int[]> getChildren(int[] state) {
        ArrayList<int[]> states = new ArrayList<int[]>();
    
        if(blankIndex(state) == 0){
            states.add(transition(state,"Right"));
            states.add(transition(state,"Down"));
        }

        else if (blankIndex(state) == 1){
            states.add(transition(state,"Right"));
            states.add(transition(state,"Down"));
            states.add(transition(state,"Left"));
        }

        else if (blankIndex(state) == 2){
            states.add(transition(state,"Down"));
            states.add(transition(state,"Left"));
        }

        else if (blankIndex(state) == 3){
            states.add(transition(state,"Down"));
            states.add(transition(state,"Right"));
            states.add(transition(state,"Up"));
        }

        else if (blankIndex(state) == 4){
            states.add(transition(state,"Down"));
            states.add(transition(state,"Left"));
            states.add(transition(state,"Right"));
            states.add(transition(state,"Up"));
        }

        else if (blankIndex(state) == 5){
            states.add(transition(state,"Down"));
            states.add(transition(state,"Left"));
            states.add(transition(state,"Up"));
        }

        else if (blankIndex(state) == 6){
            states.add(transition(state,"Right"));
            states.add(transition(state,"Up"));
        }

        else if (blankIndex(state) == 7){
            states.add(transition(state,"Right"));
            states.add(transition(state,"Up"));
            states.add(transition(state,"Left"));
        }

        else if (blankIndex(state) == 8){
            states.add(transition(state,"Up"));
            states.add(transition(state,"Left"));
        }

        return states;
    }

    private static int blankIndex(int[] state_arr) {
        for (int i = 0; i < 9; i++)
            if (state_arr[i] == 0)
              return i;
        
        return -1;
    }

    public static int[] transition(int[] state_array, String action){
        int blank_index = -1;
        //get the index of the blank;
        for(int i=0; i<9; i++)
            if(state_array[i]==0)
               blank_index = i;

        return trans_blank(state_array, blank_index, action);    
    }

    private static void swap(int[] array, int src_i, int dest_i){
        int temp= array[src_i];
        array[src_i] = array[dest_i];
        array[dest_i] = temp;
    }

    private static int[] trans_blank(int[] arr_state_to_clone, int blank_index, String action){
        int[] arr_state = arr_state_to_clone.clone();
        //0
        if(blank_index == 0 && (action.equals("Right")))
             swap(arr_state, 0,1);
        else if(blank_index == 0 && (action.equals("Down")))
            swap(arr_state, 0, 3);

        //1
        else if(blank_index == 1 && (action.equals("Left")))
            swap(arr_state,0,1);
        else if(blank_index == 1 && (action.equals("Right")))
            swap(arr_state,1,2); 
        else if(blank_index == 1 && (action.equals("Down")))
            swap(arr_state,1,4);

        //2
        else if(blank_index == 2 && (action.equals("Left")))
             swap(arr_state,1,2);
        else if(blank_index == 2 && (action.equals("Down")))
             swap(arr_state,2,5);

        //3
        else if(blank_index == 3 && (action.equals("Up")))
            swap(arr_state,0,3);
        else if(blank_index == 3 && (action.equals("Down")))
            swap(arr_state,3,6);
        else if(blank_index == 3 && (action.equals("Right")))
            swap(arr_state,3,4);
  
        //4
        else if(blank_index == 4 && (action.equals("Up")))
            swap(arr_state,1,4);
        else if(blank_index == 4 && (action.equals("Down")))
            swap(arr_state,4,7);
        else if(blank_index == 4 && (action.equals("Left")))
            swap(arr_state,3,4);
        else if(blank_index == 4 && (action.equals("Right")))
            swap(arr_state,4,5);
    
        //5
        else if(blank_index == 5 && (action.equals("Up")))
            swap(arr_state,2,5);
        else if(blank_index == 5 && (action.equals("Down")))
            swap(arr_state,5,8);
        else if(blank_index == 5 && (action.equals("Left")))
            swap(arr_state,4,5);

        //6
        else if(blank_index == 6 && (action.equals("Up")))
            swap(arr_state,3,6);
        else if(blank_index == 6 && (action.equals("Right")))
            swap(arr_state,6,7);

        //7
        else if(blank_index == 7 && (action.equals("Up")))
            swap(arr_state,4,7);
        else if(blank_index == 7 && (action.equals("Left")))
            swap(arr_state,7,6);
        else if(blank_index == 7 && (action.equals("Right")))
            swap(arr_state,7,8);
    
        //8
        else if(blank_index == 8 && (action.equals("Up")))
            swap(arr_state,5,8);
        else if(blank_index == 8 && (action.equals("Left")))
            swap(arr_state,7,8);
        
        else{
            System.out.println("Move not allowed: \n blank at index " + 
            blank_index + " wants to go to " + action);
        }

        return arr_state;
    }

    public static StringBuilder displayState(int[] state_arr){
        int i;
        StringBuilder str = new StringBuilder();
        for(i =0;i<3;i++)
             str.append(state_arr[i] + " ");
        str.append('\n');

        for(i =3;i<6;i++)
            str.append(state_arr[i] + " ");
        str.append('\n');

        for(i =6;i<9;i++)
        str.append(state_arr[i] + " ");
            str.append('\n');

        System.out.println(str);

        return str;
    }   

    public boolean similarArrays(int[] current, int[] goal){
        for(int i =0; i<current.length; i++){
            if(current[i] != goal[i])
                return false;
        }
        return true;
    }

    private int getInversion(int[] arr){
        int v;
        int totalInversion=0;

        for(int i=0; i < arr.length-1; i++){
            v=0;
            for(int j= i;j < arr.length; j++){
                if(arr[j]<arr[i] && arr[j]!=0)
                v++;
            }
           totalInversion+= v;
            
        }

        return totalInversion;
    }

    public int getManhattanHeuristic(int arr[], int[] goal){
        int totalHeuristic =0;
        int[] destIndices = findDestIndices(arr, goal);
        for(int i=0; i<goal.length;i++){
            totalHeuristic+= manhattanTwopoints(indexToCoord(i),indexToCoord(destIndices[i]));

        }
        return totalHeuristic;
    }

    private int[] indexToCoord(int x){
        int[] coord = new int[2];
        coord[0] = x%3;
        coord[1] = x/3;
        return coord;
    }

    private int manhattanTwopoints (int[] src , int dest[]){
        return Math.abs(src[0] - dest[0]) + Math.abs(src[1] - dest[1]);
    }

    private int[] findDestIndices(int[] array, int[] goal){
        int[] destIndices = new int[9];
        for(int i=0; i<array.length; i++){
            for(int j=0; i<goal.length; j++){
                if(goal[j] == array[i])
                   destIndices[i] = j;
            }
        }

        return destIndices;
    }
    
}
