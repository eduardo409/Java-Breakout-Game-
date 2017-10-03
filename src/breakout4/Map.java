package breakout4;

import java.awt.Color;
import java.awt.Graphics2D;

public class Map {
	public int Map[][];
	public int brickWidth;
	public int brickHeight;
	private Color[] colors = {Color.decode("#c84848"), Color.decode("#c66c3a"),Color.decode("#b47a30"),Color.decode("#a2a22a"), Color.decode("#48a048"),Color.decode("#4248c8")};

	// constructor
	public Map(int row, int col)
	{
		Map = new int[row][col];
		for(int i = 0; i < Map.length; i++){
			for(int j = 0; j < Map[0].length; j++){
				Map[i][j] =1;
			}
		}
		brickWidth = 700/col;
		brickHeight = 96/row;
	}

	public void draw(Graphics2D g){
		
		g.setColor(Color.BLACK);
		g.fillRect(0 ,0,800,700);
		// left bar
		g.setColor(Color.decode("#8e8e8e"));
		g.fillRect(0 ,125,50,550);
		 //right bar
		g.setColor(Color.decode("#8e8e8e"));
		g.fillRect(750 ,125, 50,550);
		// top bar
		g.setColor(Color.decode("#8e8e8e"));
		g.fillRect(0 ,125, 800,40);
		// left small rectangle 
		g.setColor(Color.decode("#429e82"));
		g.fillRect(0 ,650, 50,25);
		// left small rectangle 
		g.setColor(Color.decode("#c84848"));
		g.fillRect(750 ,650, 50,25);
		// we will begin to draw the bricks here
		int number = -1;
		for(int i = 0; i < Map.length; i++){
			for(int j = 0; j < Map[0].length; j++){
			if(j == 0)
			{
				number++;
			}
				if(Map[i][j] > 0)
				{	
				Color c = colors[number];
				g.setColor(c);
				g.fillRect(j * brickWidth +50,i * brickHeight +  220, brickWidth,brickHeight);
				}
			}
		}
	}
	public void setBrickValue(int value, int row, int col){
		Map[row][col]  = value;
	}
}
