package com.sxt;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
* @Author:Dawei Xu
* @CreateTime:2022??6??5??????5:35:26
* @Description:
*/
public class Home extends Game{
	
	public int length = 50;

	public Home(String image, int x, int y, GamePanel gamePanel) {
		super(image, x, y, gamePanel);
		
	}

	@Override
	public void selfPaint(Graphics g) {
		g.drawImage(image, x, y, null);
	}

	@Override
	public Rectangle testRectangle() {
		return new Rectangle(x,y,length,length);
	}
	

}
