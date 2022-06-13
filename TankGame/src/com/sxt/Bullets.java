package com.sxt;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


/**
* @Author:Dawei Xu
* @CreateTime:2022年6月1日上午10:00:18
* @Description:
*/
public class Bullets extends Game{
	
	/** width of bullets */
	int width = 10;
	int height = 10;
	int speed = 7;
	
	Direction direction;
	
	public Bullets(String image, int x, int y, GamePanel gamePanel,Direction direction) {
		super(image, x, y, gamePanel);
		this.direction = direction;
		
	}
	
	public void leftward() {
		x -=speed;
		
	}
	public void rightward() {
		x +=speed;
		
	}
	public void upward() {
		y -=speed;
		
	}
	public void downward() {
		y +=speed;
		
	}
	
	public void shoot() {
		switch (direction) {
		case LEFT:
			leftward();
			break;
		case UP:
			upward();
			break;
		case RIGHT:
			rightward();
			break;
		case DOWN:
			downward();
			break;
		default:
			break;
		}
		this.hitWall();
		this.bulletCollect();
		this.hitHome();
	}

	public void bulletCollect() {
		if(x<0 || x + width> this.gamePanel.getWidth()) {
			this.gamePanel.removeList.add(this);
		}
		if(y < 0 || y + height > this.gamePanel.getHeight()) {
			this.gamePanel.removeList.add(this);
		}
	}
	
	
	public void hitWall() {
		ArrayList<Wall> walls = this.gamePanel.wallList;
		for(Wall wall: walls) {
			if(this.testRectangle().intersects(wall.testRectangle())) {
				this.gamePanel.wallList.remove(wall);
				this.gamePanel.removeList.add(this);
				break;
			}
		}
		
	}
	
	public void hitHome() {
		ArrayList<Home> homeList= this.gamePanel.homeList;
		for(Home home: homeList) {
			if(this.testRectangle().intersects(home.testRectangle())) {
				this.gamePanel.homeList.remove(home);
				this.gamePanel.removeList.add(this);
				break;
			}
		}
		
	}
	public void hitRobot() {
		ArrayList<Robot> robots = this.gamePanel.robotsList;
		for(Robot robot: robots) {
			if(this.testRectangle().intersects(robot.testRectangle())) {
				this.gamePanel.robotsList.remove(robot);
				this.gamePanel.removeList.add(this);
				break;
			}
		}
		
	}

	@Override
	public void selfPaint(Graphics g) {
		g.drawImage(image, x, y, null);
		this.shoot();
		this.hitRobot();
	}

	@Override
	public Rectangle testRectangle() {
		return new Rectangle(x,y,width,height);
	}

	
	

}
