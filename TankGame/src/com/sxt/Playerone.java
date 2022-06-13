package com.sxt;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * @Author:Dawei Xu
 * @CreateTime:2022��5��31������11:04:52
 * @Description:
 */
public class Playerone extends Tank {

	public Playerone(String image, int x, int y, GamePanel gamePanel, String upImage, String leftImage,
			String rightImage, String downImage) {
		super(image, x, y, gamePanel, upImage, leftImage, rightImage, downImage);
	}

	public void keyPressed(KeyEvent ke) {
		int key = ke.getKeyCode();
		switch (key) {
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;

		default:
			break;
		}
	}
		
		public void keyReleased(KeyEvent ke) {
			int key = ke.getKeyCode();
			switch (key) {
			case KeyEvent.VK_A:
				left = false;
				break;
			case KeyEvent.VK_W:
				up = false;
				break;
			case KeyEvent.VK_S:
				down = false;
				break;
			case KeyEvent.VK_D:
				right = false;
				break;
			case KeyEvent.VK_SPACE:
					attack();

			default:
				break;
			}
		}
	

	public void move() {
		if (left) {
			leftward();
		} else if (down) {
			downward();
		} else if (right) {
			rightward();
		} else if (up) {
			upward();
		}
	}

	@Override
	public void selfPaint(Graphics g) {
		g.drawImage(image, x, y, null);
		move();
	}

	@Override
	public Rectangle testRectangle() {
		return new Rectangle(x,y,width,height);
	}

}
