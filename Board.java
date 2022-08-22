/*
File: Board.java
Author: Linh Phan (Michelle)
Date: 02/22/2022

The Board class holds the array of Cells that make up the Sudoku board. 
It will be able to read a board from a file, 
test if a value is valid at a certain position on the board, and test if the board is solved.

How to run: type "javac Board.java" in the command line.
            type "java Board <name of the file to read, for example, test1.txt> " in the command line.
*/

import java.io.*;
import java.awt.Graphics;

public class Board {
    private Cell[][] board;
    public static final int SIZE = 9;

    public Board(){
        // create a new 2D array of Cells that is Board.Size by Board.Size
        board = new Cell[Board.SIZE][Board.SIZE];

        // initialize each location in the grid with a new Cell with value 0.
        for (int i = 0; i < Board.SIZE; i++){
            for (int j = 0; j < Board.SIZE; j++){
                board[i][j] = new Cell(i, j, 0, false);
                // System.out.println(board[i][j]);
            }
        }
    }

    // generates a multi-line string representation of the board. 
    public String toString(){
        String str = "";
        for (int i = 0; i < Board.SIZE; i++){
            for (int j = 0; j < Board.SIZE; j++){
                str += board[i][j].value + " ";
            }
            str += "\n";
        }
        return str;
    }

    // returns the number of columns
    public int getCols(){
        return Board.SIZE;
    }

    //  returns the number of rows
    public int getRows(){
        return Board.SIZE;
    }

    // returns the Cell at location r, c.
    public Cell get(int r, int c){
        return board[r][c];
    }

    // returns whether the Cell at r, c, is locked.
    public boolean isLocked(int r, int c){
        return board[r][c].locked;
    }

    // set the Cell at r, c, locked.
    public void setLocked(int r, int c){
        board[r][c].locked = true;
    }

    // returns the number of locked Cells on the board.
    public int numLocked(){
        int numLocked = 0;
        for (int i = 0; i < Board.SIZE; i++){
            for (int j = 0; j < Board.SIZE; j++){
                if (board[i][j].locked == true){
                    numLocked ++;}
            }
        }
        return numLocked;
    }

    // returns the value at Cell r, c.
    public int value(int r, int c){
        return board[r][c].value;
    }

    // sets the value of the Cell at r, c.
    public void set(int r, int c, int value){
        board[r][c].value = value;
    }

    // sets the value and locked fields of the Cell at r, c.
    public void set(int r, int c, int value, boolean locked){
        board[r][c].value = value;
        board[r][c].locked = locked;
    }

    public boolean read(String filename) {
        try {
            // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            FileReader fileReader = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            String line = bufferedReader.readLine();

            int row = 0;
            // start a while loop that loops while line isn't null
            while (line != null){

                String[] words = line.split("[ ]+");
                // print the String (line)
                // System.out.println(line);
                // System.out.println(words.length);
                if (words.length > 1){
                    int col = 0;
                    // go through each item, add it to the board 
                    for (int i = 0; i < words.length; i++){
                        // System.out.println(words[i]);
                        if (words[i] != ""){
                            board[row][col].setValue(Integer.parseInt(words[i]));
                            col++;
                        }
                    }
                    row ++;
                }
                
                // print the size of the String array (you can use .length)
                // System.out.println(words.length);

                // assign to line the result of calling the readLine method of your BufferedReader object.
                line = bufferedReader.readLine();
            }
            // call the close method of the BufferedReader
            bufferedReader.close();
            
            // return true
            return true;
        }

        catch(FileNotFoundException ex) {
          System.out.println("Board.read():: unable to open file " + filename );
        }

        catch(IOException ex) {
          System.out.println("Board.read():: error reading file " + filename);
        }
    
        return false;
    }

    // tests if the given value is a valid value at the given row and column of the board. 
    // Check to see if each of the 9 sub-squares contains values 1-9, without repetition. 
    // If they do, the Sudoku board is valid; otherwise, it is invalid.
    public boolean validValue(int row, int col, int value){
        for (int i = 0; i < 9; i++){
            if (board[row][i].value == value && i != col){
                return false;
            }
            if (board[i][col].value == value && i != row){
                return false;
            }
        }

        int x = row / 3;
        int y = col / 3;

        // test that the value is in the range [1,9] 
        for (int i = x * 3; i < x * 3 + 3; i++){
            for (int j = y * 3; j < y * 3 + 3; j++){
                // test that the value is in the range [1,9] 
                if (value <= 0 || value > 9){
                    return false;
                }

                // test if the value is unique in the square 
                if (board[i][j].value == value && (i != row || j != col)){
                    return false;
                }
            }
        }
        return true;
    }

    // returns true if the board is solved
    public boolean validSolution(){
        // looping over all of the Cells on the board
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                // If any of the Cell values not in range [1,9] or are not valid
                if (!validValue(i, j, board[i][j].value)){
                    return false;
                }
            }
        }
        return true;
    }

    // find and return the next cell to check
    public Cell findBestCell(){
        // loop through the broad from row 0 
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                // Use the first 0 value cell
                if (board[i][j].value == 0 && board[i][j].locked == false){
                    // checking the possible values from 1 to 9 using the validValue method
                    for (int num = 1; num <= 9; num++){
                        // System.out.println(num);
                        if (validValue(i, j, num)){
                            board[i][j].value = num;
                            // System.out.println(i + " " + j + " " + board[i][j]);
                            return board[i][j];
                        }
                    }
                    return null;
                }
            }
        }
        return null;
    }

    public void draw(Graphics g, int scale){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                // call the draw method of each Cell
                if (board[i][j].locked){
                    board[i][j].draw(g, i, j , scale);
                }   
            }
        }

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                // call the draw method of each Cell
                if (!board[i][j].locked){
                    board[i][j].draw(g, i, j , scale);
                }
            }
        }

        g.drawLine(scale * 3 + scale * 4/7, 4, scale * 3 + scale * 4/7, scale * 10);
        g.drawLine(scale * 6 + scale * 4/7, 4, scale * 6 + scale * 4/7, scale * 10);
        g.drawLine(6, scale * 3 + scale * 3/7, scale * 10, scale * 3 + scale * 3/7);
        g.drawLine(6, scale * 6 + scale * 3/7, scale * 10, scale * 6 + scale * 3/7);
        
    }


    public static void main(String[] argv){
        Board board = new Board();
        // System.out.println(board.toString());
        // System.out.println("row " + board.getRows());
        // System.out.println("col " + board.getCols());
        // System.out.println("cell " + board.get(2, 2));
        // Cell cell = board.get(2,2);
        // System.out.println(cell);
        // System.out.println("isLocked " + board.isLocked(2, 2));
        // System.out.println("value " + board.value(2, 2));
        // board.set(2, 2, 1);
        // board.set(2, 2, 2, true);
        // System.out.println("row " + board.getRows());
        // System.out.println("col " + board.getCols());
        // System.out.println("cell " + board.get(2, 2));
        // System.out.println("isLocked " + board.isLocked(2, 2));
        // System.out.println(board.toString());
        // System.out.println(board.read(argv[0]));
        // System.out.println(board.toString());

        board.read(argv[0]);
        System.out.println(board);

        // for (int i = 0; i < 9; i++){
        //     for (int j = 0; j < 9; j++){
        //         System.out.println(board.get(i,j));
        //     }
        // }

        // System.out.println(board.validValue(1, 1, 4));
        // System.out.println(board.validValue(1, 1, 2));
        // System.out.println(board.validValue(1, 8, 2));
        // System.out.println(board.validValue(1, 8, 3));
        
        // System.out.println(board.validSolution());
        // System.out.println(board.get(0,0));
        // System.out.println(board.findBestCell());
        // System.out.println(board);

        
    }
}