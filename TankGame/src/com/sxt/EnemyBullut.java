package com.sxt;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
* @Author:Dawei Xu
*/
public class EnemyBullut extends Bullets{

	public EnemyBullut(String image, int x, int y, GamePanel gamePanel, Direction direction) {
		super(image, x, y, gamePanel, direction);
		
	}
	
	public void hitPlayer() {
		ArrayList<Tank> players = this.gamePanel.playerList;
		for(Tank player: players) {
			if(this.testRectangle().intersects(player.testRectangle())) {
				this.gamePanel.playerList.remove(player);
				this.gamePanel.removeList.add(this);
				player.alive = false;
				break;
			}
		}
		
	}
	public void selfPaint(Graphics g) {
		g.drawImage(image, x, y, null);
		this.shoot();
		this.hitPlayer();
	}

	public Rectangle testRectangle() {
		return new Rectangle(x,y,width,height);
	}
	

}
