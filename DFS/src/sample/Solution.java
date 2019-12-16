package sample;
import java.lang.reflect.Array;
import java.util.*;

//Note : Pls run the code and wait for the solution to appear after all the routes are figured out
//This might take a few seconds


public class Solution {


    public static boolean goalFound = false;
    /******************************************   Implementation Here  ***************************************/

    /*       Implementation here: you need to implement the Breadth First Search Method                      */
    /*       Please refer the instruction document for this function in details                             */
    public static void breadthFirstSearch(int[] num, int m, Vector solution) {

        //Adding the initial state as route
        Node root = new Node(num);
        root.swappedDigit = m;

        //printing the initial state
        System.out.println("Parent");
        System.out.println(Arrays.toString(num));

        //Printing the location of the empty tile
        System.out.println("Missing");
        System.out.println((m));

        //Queue to hold temporary node for BFS process - As per Question
        Queue<Node> unprocessedList = new LinkedList<>();

        //List to hold process nodes - As per Question
        List<Node> processedList = new ArrayList<>();


        unprocessedList.add(root);
        //Implemnetation of BFS
        while (unprocessedList.size() != 0 && goalFound == false){

            //Dequeuing the front of the queue to process the node
            Node currentNode = unprocessedList.remove();
            //Checking if the current node is the goal node i.e if it has reached the ideal state
            if(currentNode.isGoal()){
                System.out.println(Arrays.toString(currentNode.puzzle));
                goalFound = true;
                System.out.println("goal found");

                System.out.println("+++++++++++++");
                Node loopNode = currentNode;
                //Adding the path to the solution
                while(loopNode.parent != null){
                    solution.add(0,loopNode.swappedDigit);
                    loopNode = loopNode.parent;
                }
                solution.add(root.swappedDigit);
            }else{
                //If the goal is not reached then expand the node on all possible directions
                currentNode.moveNode(m);
                //Move all the children of node to the unprocessed queue
                for (int i=0;i<currentNode.children.size();i++){
                    unprocessedList.add(currentNode.children.get(i));
                }
            }

        }
        System.out.println("solution");
        System.out.println(solution);
        goalFound = false;
        return;
    }
}

//Class for implementing the tree structure
//According to the question it is a general tree which can have multiple children
//In this tree there can be a maximum of 4 children(Right,Left,Up,Down) and a minimum of 2 children(in the case of tile being in the corner)

class Node{
    //Holds the matrix
    int[] puzzle = new int[9];
    //Parent Node from which this state is arrived
    Node parent;
    //All children of this node achieved by moving this state
    List<Node> children = new ArrayList<>();
    //Direction of movement
    String side = "";
    //The ideal state
    int[] ideal = {0,1,2,3,4,5,6,7,8};
    //The position with which the swapping happens
    int swappedDigit = 0;


    //Constructor to create the puzzle
    public Node(int[] p){
        for(int i=0;i<p.length;i++){
            puzzle[i] = p[i];
        }
    }

    //Moving the puzzle in all possible directions

    public void moveNode(int y){
        for(int i=0;i<puzzle.length;i++){
            if(puzzle[i] == y){
                int[] tempPuzzle = puzzle;
                swapUp(puzzle,i);
                swapDown(puzzle,i);
                swapLeft(puzzle,i);
                swapRight(puzzle,i);
            }
        }
    }

    //To check if the current state is the ideal state
    public boolean isGoal(){
        boolean goal = true;
        int k = puzzle[0];
        for(int i=1; i < puzzle.length;i++){
            if(k > puzzle[i]){
                goal = false;
            }
            k = puzzle[i];
        }
        // System.out.println(isGoal);
        return goal;
    }

    //Moving the empty tile up
    public void swapUp(int[] matrix,int index){
        if(index - 3 > 0){
            int temp = 0;
            int[] tempMat = new int[9];
            tempMat = copyMatrix(tempMat,matrix);

            //Swap positions
            temp = tempMat[index];
            tempMat[index] = tempMat[index -3];
            tempMat[index -3] = temp;

            Node child = new Node(tempMat);
            child.side = "Up";
            child.parent = this;
            child.swappedDigit = index;
            children.add(child);

            System.out.println("Up");
            System.out.println(Arrays.toString(child.puzzle));

        }
    }
    public void swapDown(int[] matrix,int index){
        if(index + 3 < matrix.length){
            int temp = 0;
            int[] tempMat = new int[9];
            tempMat = copyMatrix(tempMat,matrix);

            //Swap positions
            temp = tempMat[index];
            tempMat[index] = tempMat[index + 3];
            tempMat[index + 3] = temp;



            Node child = new Node(tempMat);
            child.side = "Down";
            child.parent = this;
            children.add(child);
            child.swappedDigit = index;
            System.out.println("Down");
            System.out.println(Arrays.toString(child.puzzle));
        }
    }
    public void swapLeft(int[] matrix,int index){
        if(index % 3 > 0){
            int temp = 0;
            int[] tempMat = new int[9];

            tempMat = copyMatrix(tempMat,matrix);

            //Swap positions
            temp = tempMat[index];
            tempMat[index] = tempMat[index - 1];
            tempMat[index - 1] = temp;


            Node child = new Node(tempMat);
            child.side = "Left";
            child.parent = this;
            child.swappedDigit = index;
            children.add(child);
            System.out.println("Left");
            System.out.println(Arrays.toString(child.puzzle));
        }
    }
    public void swapRight(int[] matrix,int index){
        if(index % 3 < 2){
            int temp = 0;
            int[] tempMat = new int[9];
            tempMat = copyMatrix(tempMat,matrix);

            //Swap positions
            temp = tempMat[index];
            tempMat[index] = tempMat[index + 1];
            tempMat[index + 1] = temp;



            Node child = new Node(tempMat);
            child.side = "Right";
            child.parent = this;
            child.swappedDigit = index;
            children.add(child);
            System.out.println("Right");
            System.out.println(Arrays.toString(child.puzzle));

        }
    }
    //copying one matrix to another

    public int[] copyMatrix(int[] mat1,int[] mat2){
        for(int i=0;i<mat1.length;i++){
            mat1[i] = mat2[i];
        }
        return mat1;
    }





}