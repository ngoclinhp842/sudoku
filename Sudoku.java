/*
File: Sudoku.java
Author: Linh Phan (Michelle)
Date: 02/22/2022

The Sudoku class that will actually solve a puzzle.

How to run: type "javac Sudoku.java" in the command line.
            type "java Sudoku <number of initially specified cells>" in the command line.
*/


import java.util.Random;

public class Sudoku {
    Board board;
    LandscapeDisplay display;
    boolean draw;

    // a constructor that creates a new Board. 
    // By default, the Board should be all zeros.
    public Sudoku(boolean draw){
        board = new Board();
        display = new LandscapeDisplay(board, 30);
        this.draw = draw;

    }
    
    /* a second constructor that takes in an int parameter 
    that is the number of populated starting values N.
    Initialize the board to have N randomly selected valid values.
    */
    public Sudoku(int N, boolean draw){
        // int index;
        board = new Board();
        this.draw = draw;

        Random rand = new Random();

        for (int a = 0; a < N; a++){
            int value;
            int col;
            int row;
            
            // generate random int for row, col, and value
            row = rand.nextInt(9);
            col = rand.nextInt(9);
            value = rand.nextInt(9);

            // keep ramdomly choose another spot if it's not empty
            while (board.value(row, col) != 0){
                row = rand.nextInt(9);
                col = rand.nextInt(9);
            }
            
            /* If the location does not yet have a value, 
            then test if the value is valid in that location using your board's validValue method.
            */ 
            // insert the value into the board at that location and specify it as locked. 
            while ((!board.isLocked(row, col)) && !board.validValue(row, col, value)) {
                // If it is not valid, then try again.
                value = rand.nextInt(9);
            }

            // set the board to that value and set the position locked
            board.set(row, col, value);
            board.setLocked(row, col);
            
        }

        if (this.draw){
            display = new LandscapeDisplay(board, 30);
        }
    }

    // generates a multi-line string representation of the board. 
    public String toString(){
        String str = "";
        for (int i = 0; i < Board.SIZE; i++){
            for (int j = 0; j < Board.SIZE; j++){
                str += board.value(i, j) + " ";
            }
            str += "\n";
        }
        return str;
    }

    // a stack to keep track of the solution and allow backtracking when it gets stuck.
    public boolean solve(int delay){
        CellStack stack = new CellStack();
        
        // calculate the num of cell not filled
        int unCell = 81 - board.numLocked();
        Cell check;
        Cell curCell;
        
        // while the stack size is less than the number of unspecified cells
        while (stack.size() < unCell){
            // select the next cell to check
            curCell = board.findBestCell();
            
            // if there is a valid next cell to try
            if (curCell != null){
                // System.out.println("curCell: " + curCell);
                // push the cell onto the stack
                stack.push(curCell);
                // System.out.println("stack: " + stack);
                // update the board
                board.set(curCell.getRow(), curCell.getCol(), curCell.getValue());
                // System.out.println(board);
            }
            else if (stack.size() == unCell){
                return true;
            }
            else {
                // while it is necessary and possible to backtrack
                while (!stack.empty()){
                    // pop a cell off the stack
                    
                    check = stack.pop();
                    // System.out.println("check: " + check);
                    // System.out.println("stack: " + stack);
                    
                    // a variable to store whether the board is stuck 
                    boolean stuck = true;
                    
                    // check if there are other untested values this cell could try
                    // test num from range [current value, 9]
                    for (int i = check.getValue(); i < 9; i++){
                        if (board.validValue(check.getRow(), check.getCol(), i + 1)){
                            check.setValue(i + 1);
                            // System.out.println("check:\n" + i + " " + check);
                            // System.out.println(i);
                            // push the cell with its new value onto the stack
                            stack.push(check);
                            // System.out.println("stack:\n " + stack);
                            // update the board
                            board.set(check.getRow(), check.getCol(), i + 1);
                            // System.out.println("Board backtrack:\n" + board);
                            // set the state to not stuck
                            stuck = false;
                            break;
                        }
                    }
                    
                    
                    // if the board is not stuck, break out of the loop 
                    if (!stuck){
                        break;
                    }
                    else {
                        board.set(check.getRow(),check.getCol(), 0);
                    }
                } 
                // if the stack size is 0 (no more backtracking possible)
                if (stack.size() == 0){
                    // return false: there is no solution
                    return false;
                } 
            }
            
            if( delay > 0 && this.draw) {
                try {
                    Thread.sleep(delay);
                }
                catch(InterruptedException ex) {
                    System.out.println("Interrupted");
                }
                display.repaint();
            }
        
        }
        // return true: the board contains the solution
        return true;  
    }

    public static void main(String[] argv){
        int N = Integer.parseInt(argv[0]);
        Sudoku sudoku = new Sudoku(N, true);
        sudoku.solve(10);

        // System.out.println("Initial board:\n" + sudoku);
        
        // boolean solved = sudoku.solve(10);
        // System.out.println("Board:\n" + sudoku);
        // if (solved){
        //     System.out.println("Solved board:\n" + sudoku);
        // }
        // else {
        //     System.out.println("Unsolved board:\n" + sudoku);
        // }

        // LandscapeDisplay display = new LandscapeDisplay(sudoku.board, 30);
            
    }
}

