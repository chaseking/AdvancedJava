package me.chaseking.advancedjava.labs.lab10;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author Chase King
 */
public class Lab10 {
    public static void main(String[] args){
        try(Scanner scanner = new Scanner(System.in)){
            //(1+2) * 5 - 2 + 12/4
            //(5 * 2 ^ 3 + 2 * 3 % 2) * 4
            while(true){
                System.out.print("Type in an mathematical expression: ");

                if(scanner.hasNext()){
                    System.out.println("Value: " + evaluateExpression(scanner.nextLine()));
                }

                System.out.println();
            }
        }
    }

    public static int evaluateExpression(String expression){
        Stack<Integer> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        //Insert blanks around (, ), +, -, /, and *
        expression = insertBlanks(expression);

        //Extract operands and operators
        String[] tokens = expression.split(" ");

        //Phase 1: Scan tokens
        for(String token : tokens){
            //token = token.trim();

            if(token.length() == 0){ // Blank space
                continue; // Back to the while loop to extract the next token
            }

            char c = token.trim().charAt(0);

            switch(c){
                case '+':
                case '-':
                    while(!operatorStack.isEmpty() && (operatorStack.peek() == '+' || operatorStack.peek() == '-' || operatorStack.peek() == '*' || operatorStack.peek() == '/')){
                        processOperator(operandStack, operatorStack);
                    }

                    operatorStack.push(c);
                    break;

                case '*':
                case '/':
                case '%':
                    while(!operatorStack.isEmpty() && (operatorStack.peek() == '*' || operatorStack.peek() == '/' || operatorStack.peek() == '%')){
                        processOperator(operandStack, operatorStack);
                    }

                    operatorStack.push(c);
                    break;

                case '^':
                    while(!operatorStack.isEmpty() && operatorStack.peek() == '^'){
                        processOperator(operandStack, operatorStack);
                    }

                    operatorStack.push(c);
                    break;

                case '(':
                    operatorStack.push(c);
                    break;

                case ')':
                    // Process all the operators in the stack until seeing '('
                    while(operatorStack.peek() != '('){
                        processOperator(operandStack, operatorStack);
                    }

                    operatorStack.pop(); //Pop the '(' symbol from the stack
                    break;

                default:
                    //An operand scanned
                    //Push an operand to the stack
                    operandStack.push(new Integer(token));
            }
        }

        //Phase 2: process all the remaining operators in the stack
        while(!(operatorStack.isEmpty())){
            processOperator(operandStack, operatorStack);
        }

        //Return the result
        return operandStack.pop();
    }

    private static boolean isMathOperator(char c){
        switch(c){
            case '+':
            case '-':
            case '*':
            case '/':
            case '%':
            case '^':
                return true;

            default:
                return false;
        }
    }

    /** Process one operator: Take an operator from operatorStack and
     *  apply it on the operands in the operandStack */
    public static void processOperator(Stack<Integer> operandStack, Stack<Character> operatorStack){
        char operator = operatorStack.pop();
        int op1 = operandStack.pop();
        int op2 = operandStack.pop();

        switch(operator){
            case '+':
                operandStack.push(op2 + op1);
                break;

            case '-':
                operandStack.push(op2 - op1);
                break;

            case '*':
                operandStack.push(op2 * op1);
                break;

            case '/':
                operandStack.push(op2 / op1);
                break;

            case '%':
                operandStack.push(op2 % op1);
                break;

            case '^':
                operandStack.push((int)Math.pow(op2, op1));
                break;

            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }

    }

    public static String insertBlanks(String s){
        s = s.replaceAll(" ", "");
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            if(isMathOperator(c) || c == '(' || c == ')'){
                result.append(" ").append(c).append(" ");
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}