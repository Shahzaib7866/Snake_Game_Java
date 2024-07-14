
package snakegame;
import javax.swing.*;
import java.awt.*; //awt package has color class
import java.awt.event.*;
//java concept: in interface implementation, us interface mein jitny b abstract methods hun gy apko unhin override krwana huta hai

public class Board extends JPanel implements ActionListener{
    //Board ko aik panel bana diya ...JFrame parent div hai or JPanel child div ki trha huta hai 
    private Image apple; //Image here is awt class
    private Image dot;
    private Image head;
    private int dots; //declaring dots variable globally because we use it in other functions as well 
   
    private Timer timer; //to start snake..timer variable ko diff. jaga use krna hai tu time variable ko global bana diya 
    
    private final int ALL_DOTS = 900; //THIS WILL BE final variable as there will be total 900 dots(30*30)
    private final int DOT_SIZE = 10; //width of dot minus krna prhy ga fr pechla object ahy ga
    private final int RANDOM_POSITION = 29; //any random position to make apple object to eat
    
    private int apple_x;
    private int apple_y; //aik hi apple k diff. x & y coordinates
    
    //snake k coordinates
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection = true; //initially vo right prr move kry
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean InGame = true; //initially ap game mein ho
    
//making a constructor
    Board(){
        addKeyListener(new TAdapter()); //ActionListener's function(addKeyListener) TAdaptor ka object banaya hai
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true); //frame focus mein ah jahy
        
        loadImages();//dots are all images
        
        initGame();
         
    }
    
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple = i1.getImage(); //apple k andr image store krwa raha
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head = i3.getImage();
    }
    //game init function
    public void initGame(){
        dots = 3;
        for(int i =0; i<dots; i++){
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE; //logic goes as 50,40,30
        }
        locateApple();
        //Timer class k object se snake ko bhrha sakty hain
        timer = new Timer(140, this); //140ms 
        timer.start();
        
    }
    //random position prr apple ko generate krny wala function
    public void locateApple(){
        int r = (int)(Math.random() * RANDOM_POSITION);
        apple_x = r*DOT_SIZE;
         r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = r*DOT_SIZE;

    }
    //teeno images ko frame prr show krny k liye paint krna prhy ga
    public void paintComponent(Graphics g){
        super.paintComponent(g); //g is object of graphics
        draw(g);
        
    }
    public void draw(Graphics g){
        if(InGame){
            
        //paint apple
        g.drawImage(apple, apple_x, apple_y, this);
        //paint head and dots
        for(int i = 0; i < dots; i++ ){
            if(i==0){
                g.drawImage(head, x[i], y[i], this);
            }else{
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        //initializing using defaulttoolkit()
        Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g){
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.red);
        
        g.drawString(msg, (300 - metrices.stringWidth(msg))/2, 300/2);
    }
    
    public void move(){
        for (int i =dots; i>0; i-- ){
            x[i] = x[i-1];
            y[i] = y[i-1]; 
        }
        if(leftDirection){
            x[0]= x[0] -DOT_SIZE; //IF leftDirection true hai tu DOT-SIZE ko minus kr diya 
            
        }
        if(rightDirection){
            x[0]= x[0] + DOT_SIZE; //DOT_SIZE ko increase krna hai   
        }
        if(upDirection){
            y[0]= y[0] -DOT_SIZE; //
        }
        if(downDirection){
            y[0]= y[0] + DOT_SIZE; // y-coordinates increase krna hai
        }
    }
    public void checkApple(){
        //check if apple'coordinates match with snake's coordinates(means snake eat apple) then we increase size of snake
        if((x[0]==apple_x)&&(y[0]==apple_y)){
            dots++;
            
            locateApple();
        }
    }
    public void checkCollision(){
        for(int i = dots; i>0; i--){
            if((i>4) && (x[0] == x[i]) && (y[0]==y[i])){
                            InGame = false;

            }
        }
        //if snake touches boundary
         if(y[0] >= 300){
             InGame = false;
         }
            if(x[0] >= 300){
             InGame = false;
         }
               if(y[0] < 0){
             InGame = false;
         }
                  if(x[0] < 0){
             InGame = false;
         }
                  if(!InGame){
                      timer.stop();
                  }
        
    }
   
    
    //abstract method we need to override...override k concept hai k access specifier, return type, function name & function arguments must be same
    //this method is inside ActionListener
    public void actionPerformed(ActionEvent ae){
       if(InGame){
        checkApple();
        checkCollision();
        move(); 
       }
              repaint();
    }
    
       public class TAdapter extends KeyAdapter{
           @Override
               public void keyPressed(KeyEvent e) {
               int key = e.getKeyCode();
               
               if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                   leftDirection = true;
                   upDirection = false;
                   downDirection = false;
               }
                  if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                   rightDirection = true;
                   upDirection = false;
                   downDirection = false;
               }
                     if(key == KeyEvent.VK_UP && (!downDirection)){
                   upDirection = true;
                   leftDirection = false;
                   rightDirection = false;
               }
                        if(key == KeyEvent.VK_DOWN && (!upDirection)){
                   downDirection = true;
                   leftDirection = false;
                   rightDirection = false;
               } 
               }
    }    
}
