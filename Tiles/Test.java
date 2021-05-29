// import java.io.*;
// import java.util.*;
// import java.util.LinkedList; 
// import java.util.Queue;

// import javax.lang.model.element.Element;

// public class Test {
//     private int pathCost=0;
//     private int initial;
//     private int final_goal;

//     public Test(int initial, int goal){
//         this.initial = initial;
//         this.final_goal = goal;
//     }

//     public static void main(String[] args) {
//          Test test = new Test(0,8);
//          test.bfs();
//      }


//     public int bfs(){
//         int current = this.initial;
//         Queue<Integer> frontier = new LinkedList<>();
//         Queue<Integer> reached = new LinkedList<>();

//         frontier.add(current);
//         reached.add(current);

//         if(similarArrays(current, this.final_goal)){
//             System.out.println("Solved! Path: " + pathCost);
//             return this.final_goal;
//         }

//         ///State s = new State();

//         while(!frontier.isEmpty()){
//             current = frontier.remove();
//             this.pathCost++;
            
//             for(int x : getChildren(current)){    
//                 if(similarArrays(x,this.final_goal)){
//                     System.out.println("Solved! Path: " + pathCost);
//                     return this.final_goal;
//                 }

//                 if(!reached.contains(x)){
//                     System.out.println("First time" + x);
//                     reached.add(x);
//                     frontier.add(x);
//                 } 
                
//                 else
//                    System.out.println("Have reached "+ x);

//             } 
//         }

//         return initial;
//     }

//     public boolean similarArrays(int x, int y){
//         if(x==y)
//            return true;

//         else
//          return false;

//     }

//     public int[] getChildren(int x){
//         if(x%2==0){
//             int[] arr= new int[2];
//             arr[0] = x+1;
//             arr[1] = x+2;
//             return arr;
//         }

//         else{
//             int[] arr= new int[3];
//             arr[0]=x+1;
//             arr[1] = x+2;
//             arr[2] = x+3;
//             return arr;
//         } 

//     }
    
// }
