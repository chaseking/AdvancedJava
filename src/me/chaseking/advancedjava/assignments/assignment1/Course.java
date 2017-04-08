package me.chaseking.advancedjava.assignments.assignment1;

import java.util.Arrays;

/**
 * @author Chase King
 */
public class Course {
    public static final int DEFAULT_CAPACITY = 16;

    private final String name;

    private String[] students;
    private int size = 0;

    public Course(String name, int capacity){
        this.name = name;
        this.students = new String[capacity];
    }

    public String getName(){
        return name;
    }

    public String[] getStudents(){
        return students;
    }

    public void addStudent(String student){
        log("Adding student \"" + student + "\"");

        if(size >= students.length){
            //If the array is full, increase its size
            students = Arrays.copyOf(students, students.length + 1);
        }

        students[size++] = student;
    }

    public boolean dropStudent(String student){
        log("Dropping student \"" + student + "\"");

        for(int i = 0; i < students.length; i++){
            if(students[i].equals(student)){
                students[i] = null;

                if(i != students.length){ //If the student removed wasn't the last in the array
                    //Shift all remaining objects down in the array
                    for(int x = i + 1; x < students.length; x++){
                        students[x - 1] = students[x];
                    }

                    students[students.length - 1] = null;
                }

                size -= 1;

                return true;
            }
        }

        return false;
    }

    public int getSize(){
        return size;
    }

    public void clearStudents(){
        log("Cleared all " + size + " students.");

        for(int i = 0; i < students.length; i++){
            students[i] = null;
        }

        size = 0;
    }

    private void log(String msg){
        System.out.println("[" + name + "] " + msg);
    }

    public void debugStudents(){
        if(size == 0){
            log("There are no students in this course!");
        } else {
            log("Showing all " + size + " students:");

            for(int i = 0; i < size; i++){
                log(" " + (i + 1) + ". " + students[i]);
            }
        }
    }
}