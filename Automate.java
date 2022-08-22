/*
File: Automate.java
Author: Linh Phan (Michelle)
Date: 02/22/2022
Course: CS231 A
Project 3

The Automate class automate the process of exploring the trends in the simulation.
An automated process should be able to analyze the results of hundreds or even thousands of games.

How to run: type "javac Automate.java" in the command line.
            type "java Automate <number of initially specifed cells> <number of board simulations>" in the command line.
*/

import java.util.concurrent.TimeUnit;


public class Automate {
    private int time;
    private int N;

    Automate(int time, int N){
        this.time = time;
        this.N = N;
    }

    public float[] simulation(){
        Sudoku sudoku = new Sudoku(N, false);
        long startTime = System.currentTimeMillis();
        
        /* … The code being measured starts … */
 
        Boolean solved = sudoku.solve(0);
 
        /* … The code being measured ends … */
 
        long endTime = System.currentTimeMillis();
 
        long timeElapsed = endTime - startTime;
          
        float[] result = new float[2];
        if (solved){
            result[0] = 0;
            result[1] = timeElapsed;
        }
        else{
            result[0] = 1;
        }

        return result;
    }

    public void simulations(){
        float sum_time = 0;
        float sum_solved = 0;
        for (int i = 0; i < time; i++){
            float[] result = simulation();
            if (result[0] == 0){
                sum_solved++;
                sum_time += result[1];
            }
        }
        float avg_time = sum_time /sum_solved;
        System.out.println("Run " + time + " simulations...");
        System.out.println("There is " + sum_solved + " solved case.");
        System.out.println("The average time took to solve the case is " + avg_time + " milliseconds.");
    }

    public static void main(String[] argv){
        int N = Integer.parseInt(argv[0]);
        int time = Integer.parseInt(argv[1]);
        Automate test = new Automate(time, N);
        // System.out.println(test.simulation());
        test.simulations();
    }
}
