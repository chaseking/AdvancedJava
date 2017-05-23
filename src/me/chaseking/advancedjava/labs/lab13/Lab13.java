package me.chaseking.advancedjava.labs.lab13;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Chase King
 */
public class Lab13 {
    private static final int NUM_THREADS = 1000;
    private static final long DELAY = 1;

    public static void main(String[] args){
        //System.out.println("No synchronization: " + noSynchronization());
        //System.out.println("Sync no lock: " + syncNoLock());
        //System.out.println("Sync lock: " + syncLock());

        Set<Integer> set = Collections.synchronizedSet(new HashSet<>());

        //First thread adds a new number to the set every second
        new Thread(() -> {
            int num = 0;

            while(true){
                synchronized(set){
                    set.add(num);
                }
                System.out.println(num);
                num++;

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

        //Second thread obtains an iterator for the set and traverses the set back and forth through the iterator every second
        new Thread(() -> {
            while(true){
                synchronized(set){
                    for(int num : set);
                }

                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static int noSynchronization(){
        return execute(new IntWrapper(){
            @Override
            void increment(){
                int newValue = value + 1;

                try{
                    Thread.sleep(DELAY);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }

                value = newValue;
            }
        });
    }

    private static int syncNoLock(){
        return execute(new IntWrapper(){
            @Override
            void increment(){
                synchronized(this){
                    value++;

                    try{
                        Thread.sleep(DELAY);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private static int syncLock(){
        final Lock lock = new ReentrantLock();

        return execute(new IntWrapper(){
            @Override
            void increment(){
                lock.lock();

                try{
                    value++;
                    Thread.sleep(DELAY);
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
    }

    private static int execute(IntWrapper value){
        ExecutorService executorService = Executors.newCachedThreadPool();

        for(int i = 0; i < NUM_THREADS; i++){
            executorService.execute(value::increment);
        }

        executorService.shutdown();

        try{
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        return value.get();
    }

    private static abstract class IntWrapper {
        int value = 0;

        public int get(){
            return value;
        }

        public void set(int value){
            this.value = value;
        }

        abstract void increment();
    }
}