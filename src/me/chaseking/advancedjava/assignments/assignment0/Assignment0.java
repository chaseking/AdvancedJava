package me.chaseking.advancedjava.assignments.assignment0;

import javax.swing.*;

/**
 * @author Chase King
 */
public class Assignment0 {
    public static void main(String[] args){
        String name = JOptionPane.showInputDialog(null, "Please enter your name:", "Assignment 0", JOptionPane.QUESTION_MESSAGE);

        JOptionPane.showMessageDialog(null, "Welcome to Java, " + name + "!", "Assignment 0", JOptionPane.INFORMATION_MESSAGE);
    }
}