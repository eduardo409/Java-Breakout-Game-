package breakout4;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;
import java.awt.*;
import java.io.File;
import javax.swing.JPanel;
public class Panel extends JPanel implements KeyListener, ActionListener{
	

	private static final long serialVersionUID = 4891515765093974622L;
	private Timer timer;
	private int delay = 10;
	private int score = 0;
	private double playerX = 310;	
	private int ballposX = 220;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = 2;
	private boolean play = false;
	private Map m;
	int counter = 0;
	int Psize =675;
	
	public Panel(){
		m = new Map(6,10);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		timer = new Timer(delay,this);
		timer.start();	
		addKeyListener(this);			
	}
	public void paint(Graphics g){
		//String word = "HI!!";
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800,700 );
		
		
		m.draw((Graphics2D)g);
		// score
		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", Font.BOLD,20));
		g.drawString("Score"+score,700,20);
		// player 
		g.setColor(Color.decode("#c84848"));
		g.fillRect((int)playerX, Psize-15, 120, 8);
		// ball
		g.setColor(Color.decode("#c84848"));
		g.fillRect(ballposX,ballposY, 8, 8);
			if(score == 300)
			{
			play = false;
			ballXdir =0;
			ballYdir =0;
		    g.setColor(Color.BLUE);	
		    g.setFont(new Font("BoomBox 2", Font.BOLD,40));
			g.drawString("WINNER! ",240,400);
		    g.setFont(new Font("BoomBox 2", Font.BOLD,30));
		    g.drawString("Press Enter to Play Again",75,450);
			}
		// if the ball fall to the pit it game over 
		if(ballposY > Psize){
			play = false;
			ballXdir =0;
			ballYdir =0;
		    g.setColor(Color.BLUE);	
		    g.setFont(new Font("BoomBox 2", Font.BOLD,40));
			g.drawString("Game Over: " + score ,100,400);
		    g.setFont(new Font("BoomBox 2", Font.BOLD,30));
		    g.drawString("Press Enter to Play Again",80,450);

		}
		g.dispose();

	}

	@Override
	public void keyPressed(KeyEvent e) {

		System.out.println(playerX);
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
		if(playerX < 50)
		{
		playerX = 50;
		}
		else
			moveLeft();

		}	
		// set bounds for paddle
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(playerX > 625)
			{
				playerX = 625;
			}
			else
			moveRight();
			}
	// after game allow the user to play again 
		if(e.getKeyCode()== KeyEvent.VK_ENTER){
			// reset everything 
			if(play == false ){
				score =0;
				play = true;
				m = new Map(6,10);
				 playerX = 310;
				 ballposX = 220;
				 ballposY = 450;
				 ballXdir = -1;
				 ballYdir = 2;
				 repaint();
			}
		}
	}


	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();	 

		if(play){
			// this will help me change the angle of my ball after paddle collision
			// ---[--]------   <- portion of the paddle 
			if(new Rectangle(ballposX, ballposY,8,8).intersects(new Rectangle((int)playerX+15, Psize-15,60,8))){
				Random r = new Random();
				int  n = r.nextInt(50);
				Ball_Bounce();		
				ballYdir = -ballYdir;
				System.out.println(-((n %4)+1));
				ballXdir = -((n %4)+1);  

			} // ------[--]---   <- portion of the paddle 
			else if(new Rectangle(ballposX, ballposY,8,8).intersects(new Rectangle((int)playerX+60, Psize-15,60-15,8))){
				Random r = new Random();
				int  n = r.nextInt(50);
				Ball_Bounce();		
				ballYdir = -ballYdir;
				System.out.println(-((n %4)+1));
				ballXdir = +((n %4)+1);  
	
			}// [---]--------   <- portion of the paddle 
			else if(new Rectangle(ballposX, ballposY,8,8).intersects(new Rectangle((int)playerX, Psize-15,15,8))){
				Ball_Bounce();		
				ballYdir = -ballYdir;
				ballXdir = -1;  
			}// --------[---]   <- portion of the paddle 
			else if(new Rectangle(ballposX, ballposY,8,8).intersects(new Rectangle((int)playerX+75, Psize-15,45,8))){
				Ball_Bounce();		
				ballYdir = -ballYdir;
				ballXdir = 1;
			}

			A: for(int i =0; i < m.Map.length; i++)
			{
				for(int j = 0; j < m.Map[0].length; j++){
					if(m.Map[i][j] > 0){
						
						int brickX = j * m.brickWidth+50;
						int brickY = i * m.brickHeight + 220;
						
						int brickWidth = m.brickWidth;
						int brickHeight = m.brickHeight;
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);  
						Rectangle ballRect = new Rectangle(ballposX,ballposY,8,8);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)){
							Block_Destroy_Sound();
							m.setBrickValue(0, i, j);
							score+=5;
									if(ballposX + 19 <= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width){
										ballXdir = -ballXdir;
									}else{
										ballYdir = -ballYdir;
									}

									break A;
									
						}
	
							
					}
				}
			}
			ballposX += ballXdir;
			ballposY += ballYdir;
		 //System.out.println(ballposX + " "+ ballposY);

			if(ballposX < 50){
				Ball_Bounce();
				ballXdir = -ballXdir;
				
			}
			if(ballposY < 165){
				Ball_Bounce();
				ballYdir = -ballYdir;
				
			}
			if(ballposX > 740){
				Ball_Bounce();
				ballXdir = -ballXdir;
				
			}
	
		}
		
		repaint();
	
	}
	// right border 
	public void moveRight()
	{
		play = true;
		playerX +=50;
	}
	// left border
	public void moveLeft()
	{
		play = true;
		playerX -=50;
	}
	// sound functions 
	public static void Block_Destroy_Sound(){
		  try {
	
			   File file = new File("Block_Destroy" + ".wav");
			   Clip clip = AudioSystem.getClip();
			   clip.open(AudioSystem.getAudioInputStream(file));
			   clip.start();
			   Thread.sleep(clip.getMicrosecondLength()/10000);
			  } catch (Exception e) {
			   System.err.println(e.getMessage());
			  }
	}
	
	public static void Ball_Bounce(){
		  try {
	
			   File file = new File("Ball_Bounce" + ".wav");
			   Clip clip = AudioSystem.getClip();
			   clip.open(AudioSystem.getAudioInputStream(file));
			   clip.start();
			   Thread.sleep(clip.getMicrosecondLength()/10000);
			  } catch (Exception e) {
			   System.err.println(e.getMessage());
			  }
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent arg0) {	
	}	

}

