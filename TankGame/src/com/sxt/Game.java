package com.sxt;
/**
* @Author:Dawei Xu
* @CreateTime:2022年5月31日下午10:16:35
* @Description:
*/

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


public abstract class Game {
	
	public Image image;
	public int x;
	public int y;
	
	public GamePanel gamePanel;
	/**
	 * constructor with following params
	 * @param image image got from images directory
	 * @param x x position
	 * @param y y position
	 * @param gamePanel game panel
	 */
	public Game(String image,int x, int y, GamePanel gamePanel) {
		this.image = java.awt.Toolkit.getDefaultToolkit().getImage(image);
		this.x = x;
		this.y = y;
		this.gamePanel = gamePanel;
	}

	/**
	 * to paint itself
	 * @param g graphics
	 */
	public abstract void selfPaint(Graphics g);
	
	/**
	 * to test if two images get contact or not such as bullets and tank
	 * @return rectangle to test if two rectangle get contact
	 */
	public abstract Rectangle testRectangle();
	
}
