/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.Serializable;

/**
 *
 * @author Kondos
 */
class Node  implements Comparable ,  Serializable 
{ 
    int ascii, frequency; 
    Node left, right; 
  
    public Node(int item , int freq) 
    { 
        frequency = freq;
        ascii = item; 
        left = right = null; 
        
    } 

    @Override
    public int compareTo(Object t) {
        int compareFreq=((Node)t).getFrequency();
        /* For Ascending order*/
        return this.frequency-compareFreq;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "Node{" + "ascii=" + ascii + ", frequency=" + frequency + '}';
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
    /*    public Object clone() throws CloneNotSupportedException {

        Node Node = new Node(this.ascii , this.frequency);
        Node.setLeft(this.left);
        Node.setRight(this.right);
        return Node;
    }*/

    
    
} 
