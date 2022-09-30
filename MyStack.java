package com.company;
import java.util.*;

interface IStack {

    /*** Removes the element at the top of stack and returnsthat element.
     * @return top of stack element, or through exception if empty
     */

    public Object pop();

    /*** Get the element at the top of stack without removing it from stack.
     * @return top of stack element, or through exception if empty
     */

    public Object peek();

    /*** Pushes an item onto the top of this stack.
     * @return object to insert*
     */

    public void push(Object element);

    /*** Tests if this stack is empty
     * @return true if stack empty
     */
    public boolean isEmpty();

    public int size();
}

public class MyStack implements IStack{

    public class Node{
        Object data;
        Node next;
    }
    Node head = null;

    public void add(Object element){
        Node current=new Node();
        current.data=element;
        current.next=null;
        Node temp = head;
        if(head==null)
            head=current;
        else {
            while(temp.next!=null){
                temp=temp.next;
            }
            temp.next=current;
        }
    }
    public void display(){
        Node current = head;
        System.out.print("[");
        for(int i = 0; current!=null; ++i) {
            System.out.print(current.data);
            if(current.next!=null )
                System.out.print(", ");
            current = current.next;
        }
        System.out.print("]");
    }


    public void push(Object element){
        Node current=new Node();
        current.data=element;
        current.next=head;
        head=current;
    }


    public Object pop(){
        if(head==null){
            System.out.println("Error");
            System.exit(0);
        }
        Object obj=head.data;
        head=head.next;
        return obj;
    }
    public Object peek(){
        if(head==null){
            System.out.println("Error");
            System.exit(0);
        }
        return head.data;
    }
    public boolean isEmpty(){
        if(head==null)
            return true;
        return false;
    }
    public int size(){
        Node current=new Node();
        int size=0;
        if(head==null)
            return size;

        current=head;
        while (current!=null){
            size++;
            current=current.next;
        }
            return size;
    }
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        MyStack stack =new MyStack();

        String str=in.nextLine().replaceAll("\\[|\\]", "");
        String []s=str.split(", ");
        Object[] arr = new Object[s.length];
        if (s.length == 1 && s[0].isEmpty())
            arr = new Object[]{};
        else {
            for(int i = 0; i < s.length; ++i){
                arr[i] = s[i];
                stack.add(arr[i]);
            }
        }
        String op=in.next();
        if(op.equals("push")){
            Object obj=in.next();
            stack.push(obj);
            stack.display();
        }
        else if(op.equals("pop")){
            stack.pop();
            stack.display();
        }
        else if(op.equals("peek")){
            System.out.println(stack.peek());
        }
        else if(op.equals("isEmpty")){
            if(stack.isEmpty()) System.out.println("True");
            else System.out.println("False");
        }
        else if (op.equals("size")){
            System.out.println(stack.size());
        }
        else
            System.out.println("Error");
    }
}