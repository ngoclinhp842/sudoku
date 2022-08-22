/*
File: Cell.java
Author: Linh Phan (Michelle)
Date: 02/22/2022

The Cell class should have four fields to hold the row index, 
column index, value, and whether the value is locked. 
Use integers for the first three and a boolean for the last.

How to run: type "javac Cell.java" in the command line.
            type "java Cell" in the command line.
*/

import java.awt.Graphics;
import java.awt.Color;

public class Cell {
    // initialize all valus to 0 or false;
    int row;
    int col;
    int value;
    boolean locked;
    
    public Cell(){
        row = 0;
        col = 0; 
        value = 0;
        locked = false;
    } 

    /*
    initialize the row, column, and value fields to the given parameter values. 
    Set the locked field to false;
    */
    public Cell(int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = false;
    }

    // initialize all of the Cell fields given the parameters.
    public Cell(int row, int col, int value, boolean locked){
        this.row = row;
        this.col = col;
        this.value = value;
        this.locked = locked;
    }

    // return the Cell's row index.
    public int getRow(){
        return row;
    }

    // return the Cell's column index.
    public int getCol(){
        return col;
    }

    // return the Cell's value.
    public int getValue(){
        return value;
    }

    // set the Cell's value.
    public void setValue(int newval){
        this.value = newval;
    }

    // return the value of the locked field.
    public boolean isLocked() {
        return locked;
    }

    // set the Cell's locked field to the new value.
    public void setLocked(boolean lock) {
        this.locked = lock;
    }

    // return a new Cell with the same values as the existing Cell.
    public Cell clone(){
        // make a new Cell
        Cell new_cell = new Cell();

        // copy the data from the current Cell fields to the new Cell fields.
        new_cell.row = this.row;
        new_cell.col = this.col;
        new_cell.value = this.value;
        new_cell.locked = this.locked;

        return new_cell;
    }

    // generate and return a representating String
    public String toString(){
        // return a string with the Cell's numeric value.
        String toStr = "position: " + this.row + ", " + this.col +
        " value: " + this.value + " locked: " + this.locked;
        return toStr;
    }

    // draws the Cell's number
    public void draw(Graphics g, int x0, int y0, int scale){
        char[] mychar = new char[2];
        mychar[0] = (char)('0' + this.value);
        mychar[1] = 0;

        if (this.locked && this.value != 0){
            g.setColor(Color.pink);
            // System.out.println(this);
        }
        else {
            g.setColor(Color.darkGray);
        }
        g.drawChars(mychar, 0, 1, x0 * scale + scale , y0 * scale + scale );
    }


    public static void main(String[] argv){
        Cell cell = new Cell(1, 2, 0, false);
        System.out.println(cell);
    }

}
