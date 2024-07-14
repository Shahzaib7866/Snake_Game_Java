

package snakegame;
//swing package has many classes, JFrame is one of them...JFrame is a fundamental class in Java Swing used to 
//create top-level windows with a title bar, border, and other decorations...belongs to the javax.swing package.
import javax.swing.*;

public class SnakeGame extends JFrame {
    
    //this is constructor and it shows frame 
    SnakeGame(){
     //jframe aik class hai jo k Swing package k andr se ahti hai..Swing aik java core ka framwork hai js se 
     //desktop application bnaty hain
     super(); 
     //agr ap kisi parent/super class k constructor ko call krty ho tu super keyword use kro gy...super must 
     //be the first statement inside the constructor
     setTitle("Snake Game"); // Sets the title of the JFrame
     add(new Board());
     pack(); //pack function frame ko refresh kr k new things dikha sakta hai
     
     setLocationRelativeTo(null); 
     setResizable(false);
    }
    
    public static void main(String[] args){
        new SnakeGame().setVisible(true);
//this is an object or class ka object bnty hi constructor call huta hai
    }
   
}
