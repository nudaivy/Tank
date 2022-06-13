package com.sxt;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;



/**
* @Author:Dawei Xu
* @CreateTime:2022年5月31日下午10:35:13
* @Description:
*/


public abstract class Tank extends Game{
	public int width = 40;
	public int height = 50;
	public int speed = 3;
	
	public Direction direction = Direction.UP;
	
	public String upImage;
	public String leftImage;
	public String rightImage;
	public String downImage;
	public boolean alive = false;
	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean down = false;
	/** tank gun status, it need time to cool down after each attack */
	public boolean attackCoolSts = true;
	/** time the gun need to cool down and attack next time */
	public int attackCoolTime = 1000;
	
	public Tank(String image, int x, int y, GamePanel gamePanel,
			String upImage,String leftImage, String rightImage,String downImage) {
		super(image, x, y, gamePanel);
		this.upImage = upImage;
		this.leftImage = leftImage;
		this.rightImage = rightImage;
		this.downImage = downImage;
	}
	
	public void leftward() {
		direction = Direction.LEFT;
		setImg(leftImage);
		if(!touchWall(x-speed, y) && !touchWall(x-speed, y)) {
			
			this.x -= speed;
		}
		
	}
	public void rightward() {
		direction = Direction.RIGHT;
		setImg(rightImage);
		if(!touchWall(x+speed, y) && !moveToBorder(x+speed, y)) {
			
			this.x += speed;
		}
		
	}
	public void upward() {
		direction = Direction.UP;
		setImg(upImage);
		if(!touchWall(x, y-speed) && !moveToBorder(x, y-speed)) {
			
			this.y -= speed;
		}
		
	}
	public void downward() {
		direction = Direction.DOWN;
		setImg(downImage);
		if(!touchWall(x, y+speed) && !touchWall(x, y+speed)) {
			
			this.y += speed;
		}
		
	}
	
	public void attack() {
		if(attackCoolSts && alive) {
			Point point = this.getHeadPoint();
			Bullets bullets = new Bullets("images/bullet.png",point.x, point.y, this.gamePanel, direction);
			this.gamePanel.bulletsList.add(bullets);
			new AttachCD().start();
		}
		
		
	}
	
	public boolean moveToBorder(int x, int y) {
		if(x<0) {
			return true;
		}else if (x + width > this.gamePanel.getWidth()) {
			return true;
			
		}else if (y + height > this.gamePanel.getHeight()) {
			return true;
			
		}else if (y < 0) {
			return true;
			
		}
		return false;
		
	}
	class AttachCD extends Thread{
		public void run() {
			attackCoolSts = false;
			try {
				Thread.sleep(attackCoolTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			attackCoolSts = true;
			this.stop();
		}
	}
	public Point getHeadPoint() {
		switch (direction) {
		case LEFT:
			return new Point(x, y+height/2);
		case RIGHT:
			return new Point(x+width,y+ height/2);
		case UP:
			return new Point(x+2*width/3,y);
		case DOWN:
			return new Point(x+2*width/3, y+height);
		default:
			return null;
		}
		
	}
	
	public boolean touchWall(int x, int y) {
		ArrayList<Wall> walls = this.gamePanel.wallList;
		Rectangle nextStep = new Rectangle(x,y,width,height);
		for(Wall wall: walls) {
			if(nextStep.intersects(wall.testRectangle())) {
				return true;
			}
		}
		return false;
		
	}
	public void setImg(String image) {
		this.image = Toolkit.getDefaultToolkit().getImage(image);
		
	}
	@Override
	public abstract void selfPaint(Graphics g);
	
	@Override
	public abstract Rectangle testRectangle() ;
}
