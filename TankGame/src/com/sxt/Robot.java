package com.sxt;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

/**
* @Author:Dawei Xu
*/
public class Robot extends Tank{
	/** steps robot moved */
	int moveTime = 0;

	public Robot(String image, int x, int y, GamePanel gamePanel, String upImage, String leftImage, String rightImage,
			String downImage) {
		super(image, x, y, gamePanel, upImage, leftImage, rightImage, downImage);
		
	}

	public Direction getRandomDir() {
		Random random = new Random();
		int rnum = random.nextInt(4);
		switch (rnum) {
		case 0:
			return Direction.LEFT;
		case 1:
			return Direction.RIGHT;
		case 2:
			return Direction.UP;
		case 3:
			return Direction.DOWN;
			default:
				return null;
			
		}
		
	}
	
	public void go() {
		attack();
		if(moveTime >= 20) {
			direction = getRandomDir();
			moveTime = 0;
			
		}else {
			moveTime+=1;
		}
		
		switch (direction) {
			case LEFT:
				leftward();
				break;
			case RIGHT:
				rightward();
				break;
			case UP:
				upward();
				break;
			case  DOWN:
				downward();
				break;
		}
		
	}
	
	public void attack() {
		Point point = getHeadPoint();
		Random random = new Random();
		int rnum = random.nextInt(400);
		if(rnum <4) {
			this.gamePanel.bulletsList.add(
					new EnemyBullut("images/bullet-enemy.png",point.x, point.y, this.gamePanel, direction));
		}
		
	}
	@Override
	public void selfPaint(Graphics g) {
		g.drawImage(image, x, y, null);
		go();
	}

	@Override
	public Rectangle testRectangle() {
		return new Rectangle(x,y,width,height);
	}
	

	
}
