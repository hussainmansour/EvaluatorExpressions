package com.company;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.lang.Character;

import static java.lang.Character.*;

interface IExpressionEvaluator {
    public String infixToPostfix(String expression);
    public int evaluate(String expression);
}
interface IStack1 {
    public Object pop();
    public Object peek();
    public void push(Object element);
    public boolean isEmpty();
    public int size();
}

class MyStack1 implements IStack1 {

    class Node {
        Object data;
        Node next;
    }

    Node head = null;

    public void add(Object element) {
        Node current = new Node();
        current.data = element;
        current.next = null;
        Node temp = head;
        if (head == null)
            head = current;
        else {
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = current;
        }
    }

    public void display() {
        Node current = head;
        System.out.print("[");
        for (int i = 0; current != null; ++i) {
            System.out.print(current.data);
            if (current.next != null)
                System.out.print(", ");
            current = current.next;
        }
        System.out.print("]");
    }

    public void push(Object element) {
        Node current = new Node();
        current.data = element;
        current.next = head;
        head = current;
    }

    public Object pop() {
        if (head == null) {
            System.out.println("Error");
            System.exit(0);
        }
        Object obj = head.data;
        head = head.next;
        return obj;
    }

    public Object peek() {
        if (head == null) {
            System.out.println("Error");
            System.exit(0);
        }
        return head.data;
    }

    public boolean isEmpty() {
        if (head == null)
            return true;
        return false;
    }

    public int size() {
        Node current = new Node();
        int size = 0;
        if (head == null)
            return size;

        current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }
}



public class Evaluator implements IExpressionEvaluator {

    /**
     * this func take an operator and return a number that refere to its precedence
     * @param c
     * @return
     */
    public int priority(char c){

        if(c=='-'||c=='+')
            return 1;
        else if(c=='*'||c=='/')
            return 2;
        else if(c=='^')
            return 3;
        else if(c=='('||c==')')
            return 0;
        else {
            System.out.println("Error");
            System.exit(0);
            return 0;
        }
    }


    /**
     * Takes a symbolic/numeric infix expression as input and converts it to
     * postfix notation. There is no assumption on spaces between terms or the
     * length of the term (e.g., two digits symbolic or numeric term)
     *
     * @param expression infix expression
     * @return postfix expression
     */
    public String infixToPostfix(String expression){
        MyStack1 stack =new MyStack1();
        String postfix="";
        expression= expression.replaceAll("--|\\++","+");
        expression= expression.replaceAll("/\\+","/");
        expression= expression.replaceAll("\\*\\+","*");
        expression= expression.replaceAll("\\^\\+","^");
        expression= expression.replaceAll("-\\+","-");
        if(expression.charAt(0)=='+')
            expression=expression.substring(1);
        char []infix=expression.toCharArray();
        if(infix[0]=='*'||infix[0]=='/'||infix[0]=='^'||infix[infix.length-1]=='*'||infix[infix.length-1]=='/'||infix[infix.length-1]=='^'||infix[infix.length-1]=='-'||infix[infix.length-1]=='+')
        {
            System.out.println("Error");
            System.exit(0);
        }
        int count=0,count2=0;
        for(int i=0;i<infix.length;i++){
            if(infix[i]=='(')
                count++;
            if(infix[i]==')')
                count2++;
        }
        if(count!=count2){
            System.out.println("Error");
            System.exit(0);
        }
        count=0;count2=0;
        for(int i=0;i<infix.length;i++){
            if(infix[i]=='a'||infix[i]=='b'||infix[i]=='c')
                count++;
            if(infix[i]=='+'||infix[i]=='*'||infix[i]=='/'||infix[i]=='^'||infix[i]=='-')
                count2++;
        }
        if(count<count2){
            System.out.println("Error");
            System.exit(0);
        }

        for(int i=0;i<infix.length;i++){
            if(infix[i]==' ')continue;
            if(isDigit(infix[i])||infix[i]=='a'||infix[i]=='b'||infix[i]=='c')
                postfix+=infix[i];
            else if (infix[i]=='(')
                stack.push('(');
            else if(infix[i]==')'){
                while (stack.peek().toString().charAt(0) != '('){
                    postfix+=stack.peek();
                    stack.pop();
                }
                stack.pop();
            }
            else if (infix[i]=='+'||infix[i]=='-'||infix[i]=='*'||infix[i]=='/'||infix[i]=='^'){
                while (!stack.isEmpty() && priority(infix[i])<= priority(stack.peek().toString().charAt(0))){
                    postfix+=stack.peek();
                    stack.pop();
                }
                stack.push(infix[i]);
            }
            else {
                System.out.println("Error");
                System.exit(0);
            }

        }
        while (!stack.isEmpty()){
            postfix+=stack.peek();
            stack.pop();
        }
        return postfix;
    }

    /**
     * Evaluate a postfix numeric expression, with a single space separator
     * @param expression postfix expression
     * @return the expression evaluated value
     */

    public int evaluate(String expression){
        MyStack1 stack =new MyStack1();
        int firstoperand=0,secoperand=0,res=0;
        String []postfix=expression.split(" ");
        for(int i=0;i<postfix.length;i++){
            if(postfix[i].equals("+")||postfix[i].equals("-")||postfix[i].equals("*")||postfix[i].equals("/")||postfix[i].equals("^")){
                secoperand= Integer.parseInt(stack.pop().toString());
                if(!stack.isEmpty())
                    firstoperand=Integer.parseInt(stack.pop().toString());

                switch (postfix[i]){
                    case "+":
                        res=firstoperand+secoperand;
                        break;
                    case "-":
                        res=firstoperand-secoperand;
                        break;
                    case "*":
                        res=firstoperand*secoperand;
                        break;
                    case "/":
                        if(secoperand==0){
                            System.out.println("Error");
                            System.exit(0);
                        }
                        res=firstoperand/secoperand;
                        break;
                    case "^":
                        res= (int) Math.pow(firstoperand,secoperand);
                        break;
                    default:
                        System.out.println("Error");
                        System.exit(0);
                }
                stack.push(res);
                firstoperand=0;secoperand=0;res=0;
            }
            else if(!postfix[i].equals("")){
                stack.push(postfix[i]);
            }
        }
        if(!stack.isEmpty())
            res= Integer.parseInt(stack.pop().toString());
        return res;
    }

    public static void main(String[] args) {

        Scanner in =new Scanner(System.in);
        Evaluator e=new Evaluator();

            String infix = in.nextLine().replaceAll("\\s", "");
            String postfix = e.infixToPostfix(infix);
            System.out.println(postfix);
            String eva = in.nextLine().replaceAll("\\s|a|=", "");
            String evb = in.nextLine().replaceAll("\\s|b|=", "");
            String evc = in.nextLine().replaceAll("\\s|c|=", "");

            postfix = postfix.replaceAll("\\-", " - ");
            postfix = postfix.replaceAll("a", " " + eva + " ");
            postfix = postfix.replaceAll("b", " " + evb + " ");
            postfix = postfix.replaceAll("c", " " + evc + " ");
            postfix = postfix.replaceAll("\\+", " + ");
            postfix = postfix.replaceAll("\\*", " * ");
            postfix = postfix.replaceAll("/", " / ");
            postfix = postfix.replaceAll("\\^", " ^ ");

            int result = e.evaluate(postfix);
            System.out.println(result);

    }
}