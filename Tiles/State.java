import java.util.*;

public class State extends StateOps{
    int[] tilesArray= new int[9];
    int pathCost;

    public State(int[] tilesArray, int pathCost){
        this.tilesArray = tilesArray;
        this.pathCost = pathCost;
    }

    public int[] getTilesArray() {
        return tilesArray;
    }

    public int getPathCost(){
        return pathCost;
    }
}
