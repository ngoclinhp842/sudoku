/*
File: CellStack.java
Author: Linh Phan (Michelle)
Date: 02/22/2022

CellStack class that implements an array-based Stack that can hold Cell types. 
You may not use an ArrayList to implement your stack; build it with regular arrays.

How to run: type "javac CellStack.java" in the command line.
            type "java CellStack" in the command line.
*/

public class CellStack {
    private Cell[] stack;
    private int top;

    // initialize the stack to a default size.
    public CellStack(){
        stack = new Cell[10];
        top = 0;
    }

    // initialize the stack to hold up to max elements.
    public CellStack(int max) {
        stack = new Cell[max];
        top = 0; 
    }

    // if there is space, push c onto the stack.
    public void push( Cell c ){
        // if there is space for another item on the stack
        if (top < stack.length){
            // push c onto the stack by assigning the Cell c to the top location
            stack[top] = c;
            // increment the top location
            top ++;
        }
        else {
            // allocate a new Cell array that is twice as big as the prior array
            Cell[] new_stack = new Cell[top * 2];

            // copy all of the elements from the old array to the new array
            for (int i = 0; i < top; i++){
                new_stack[i] = this.stack[i];
            }

            // replace the old array
            this.stack = new_stack;

            // push c onto the stack and return.
            stack[top] = c;
            top ++;
        }
    }

    // remove and return the top element from the stack; return null if the stack is empty.
    public Cell pop(){
        if (top == 0){
            return null;
        }
        Cell last = stack[top - 1];
        stack[top - 1] = null;
        top -= 1;
        return last;
    }

    // return the number of selements on the stack.
    public int size(){
        return this.top;
    }

    // return true if the stack is empty.
    public boolean empty(){
        if (top == 0){
            return true;
        }
        return false;
    }

    /**
	* Returns, but does not remove, the element at the top of the stack. 
	* @return top element in the stack (or null if empty)
	*/
	public Cell peek() {
		if (empty() == false){
			return stack[top - 1];
		} else {
			return null;
		}
	}

    public String toString() {
		String s = "";
		for ( int i=0; i < top; i++){
			s += stack[i];
			s += ";";
		}
		return s;
	}

    public static void main(String[] argv){
        CellStack test = new CellStack();
        Cell c1 = new Cell();
        Cell c2 = new Cell();
        Cell c3 = new Cell();
        test.push(c1);
        test.push(c2);
        test.push(c3);
        System.out.println(test.size());

    }
}
