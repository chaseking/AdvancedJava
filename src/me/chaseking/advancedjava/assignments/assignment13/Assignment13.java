package me.chaseking.advancedjava.assignments.assignment13;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author Chase King
 */
public class Assignment13 {
    public static void main(String[] args){
        double[] list = new double[9000000];
        long time = System.currentTimeMillis();
        parallelAssignValues(list);
        System.out.println("Parallel execution time: " + (System.currentTimeMillis() - time) + " ms");
        assign(list, 0, list.length);
        System.out.println("Sequential execution time: " + (System.currentTimeMillis() - time) + " ms");
    }

    public static void parallelAssignValues(double[] list){
        final int threshold = 1000000; //1 million values per task
        int start = 0;
        ForkJoinPool pool = new ForkJoinPool();

        while(true){
            final int finalStart = start;
            final int end = Math.min(start + threshold, list.length);

            pool.invoke(new RecursiveAction(){
                @Override
                protected void compute(){
                    assign(list, finalStart, end);
                }
            });

            if(end == list.length){
                break;
            } else {
                start += threshold;
            }
        }
    }

    private static void assign(double[] list, int start, int end){
        Random random = new Random();

        for(int i = start; i < end; i++){
            list[i] = random.nextDouble();
        }
    }
}

