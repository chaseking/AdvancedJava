package me.chaseking.advancedjava.assignments.assignment1;

/**
 * @author Chase King
 *
 * Write a test program that creates a course with default capacity,
 * adds twenty students, removes three, adds another two,
 * and displays the students in the course.
 * Call clear() and display the students in the course again.
 */
public class Assignment1 {
    public static void main(String[] args){
        Course course = new Course("COMP-232", Course.DEFAULT_CAPACITY);

        for(int i = 0; i < 20; i++){
            course.addStudent("Student " + (i + 1));
        }

        course.dropStudent("Student 4");
        course.dropStudent("Student 13");
        course.dropStudent("Student 17");

        course.addStudent("Student 21");
        course.addStudent("Student 40");

        course.debugStudents();

        course.clearStudents();
        course.debugStudents();
    }
}