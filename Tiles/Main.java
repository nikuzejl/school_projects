import java.io.*;
import java.util.*;
import java.lang.*; 

public class Main extends StateOps{
    int[] goal_0= new int[]{1, 2, 3, 8, 0, 4, 7, 6, 5};
    int[] goal_1 = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 0};
    public static void main(String[] args) {
//specific
        int[] start_start = new int[]{8, 1, 2, 0, 4, 3, 7, 6, 5}; 
        System.out.print("\nSPECIFIC CONFIGURATION\n\n");
        specificStart(start_start);

//random
        // System.out.print("RANDONM CONFIGURATION\n\n");
        // randomStart();
    }

    private static void randomStart(){
        Main main = new Main();
        int[] initialArray = randomShuffle();
        main.solveBfsPuzzle(initialArray);
        main.solveStarPuzzle(initialArray);
    }

    private static void specificStart(int[] initialArray){
        Main main = new Main();
        main.solveBfsPuzzle(initialArray);
        main.solveStarPuzzle(initialArray);
    }

    private static int[] randomShuffle(){
        ArrayList tilesArr = new ArrayList<Integer>();
        int[] arr= new int[9];
        int i, j=0;
     
        for(i=0; i<9; i++)
           tilesArr.add(i);

        Collections.shuffle(tilesArr);

        for(Object x:tilesArr){
            arr[j] = (int) x;
            j++;
        }
        return arr;
    }

    private void solveBfsPuzzle(int[] initialState){
        BfsSearch runBfs;
        if(getInversion(initialState)%2==0){
            runBfs = new BfsSearch(new State(initialState,0), new State(goal_0,0));
            runBfs.runBfs();
        }
            
        else{
            runBfs = new BfsSearch(new State(initialState,0), new State(goal_0,0));
            runBfs.runBfs();
        }
            
    }

    private void solveStarPuzzle(int[] initialState){
        AStarSearch star;
        if(getInversion(initialState)%2==0){
            star = new AStarSearch(new State(initialState,0), new State(goal_1,0));
            star.runAstar();
        }
            
        else{
            star = new AStarSearch(new State(initialState,0), new State(goal_0,0));
            star.runAstar();
        }
            
    }

    private int[] stringToIntArray(String s){
        String[] string_array = s.split(" ", 9);
        int[] int_array= new int[9];
        for(int i=0; i<9; i++)
            int_array[i]= Integer.parseInt(string_array[i]);

        return int_array;
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

}
