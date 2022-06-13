/**
 * 
 */
package com.sxt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;



/**
 * Class GamePanel to show the main entry panel of game
 * 
 * @Author:Dawei Xu
 */
public class GamePanel extends JFrame {
	/** width of panel */
	int width = 800;
	/** height of panel */
	int height = 700;

	Image selectImage = java.awt.Toolkit.getDefaultToolkit().getImage("images/tankSelector.png");
	Image offScreenImage = null;
	/** initial y position of selector */
	int y = 250;
	/** state of game mode£º 1 means single player 2 means double players */
	int status = 0;
	int keyValue = 1;
	/** robot number */
	int robotCount = 0;
	/** times of robot repaint */
	int robotGraphicCount = 0;
	/** list to store all bullets */
	ArrayList<Bullets> bulletsList = new ArrayList<Bullets>();
	ArrayList<Robot> robotsList = new ArrayList<Robot>();
	/** bullets need to be removed when hit tanks */
	ArrayList<Bullets> removeList = new ArrayList<Bullets>();
	ArrayList<Tank> playerList = new ArrayList<Tank>();
	ArrayList<Wall> wallList = new ArrayList<Wall>();
	ArrayList<Home> homeList = new ArrayList<Home>();
	Playerone playerone = new Playerone("images/playone/mytankUP.png", 125, 510, this, "images/playone/mytankUP.png", "images/playone/mytankLeft.png",
			"images/playone/mytankRight.png", "images/playone/mytankDown.png");
	PlayerTwo playerTwo = new PlayerTwo("images/playtwo/mytankUP.png", 625, 510, this, "images/playtwo/mytankUP.png", "images/playtwo/mytankLeft.png",
			"images/playtwo/mytankRight.png", "images/playtwo/mytankDown.png");
	/**
	 * method to launch game panel
	 */
	public void launch() {
		setTitle("Classic Tank Game");// set title of game panel
		setSize(width, height); // set size of panel
		setLocationRelativeTo(null);// set location of panel to be middle of screen
		setDefaultCloseOperation(3);// set close event that program will stop running when panel is closed
		setResizable(false);// set the size of panel not to be changed
		setVisible(true); // set the panel to be visible

		this.addKeyListener(new GamePanel.KeyMonitor());
		for(int i =0; i <14; i ++) {
			wallList.add(new Wall("images/wall-earth.png", i*60, 170, this));
			wallList.add(new Wall("images/wall-steel.png", i*60, 370, this));
		}
		wallList.add(new Wall("images/wall-earth.png", 305, 620, this));
		wallList.add(new Wall("images/wall-earth.png", 305, 560, this));
		wallList.add(new Wall("images/wall-earth.png", 365, 560, this));
		wallList.add(new Wall("images/wall-earth.png", 425, 560, this));
		wallList.add(new Wall("images/wall-earth.png", 425, 620, this));
		homeList.add(new Home("images/home.png", 375, 630, this));
		while (true) {
			if(robotsList.size() == 0 && robotCount == 10) {
				status = 5;
			}
			if(playerList.size() == 0 && (status == 1 || status ==2) || homeList.size() ==0) {
				status = 4;
			}
			if(robotGraphicCount % 100 == 1 && robotCount <10 && (status ==1 || status ==2)) {
				Random random = new Random();
				/** robots created in random X position */
				int randomX = random.nextInt(700);
				robotsList.add(new Robot("images/robot/1.down.png", randomX, 110, this, "images/robot/1.up.png", "images/robot/1.left.png", 
						"images/robot/1.left.png", "images/robot/1.down.png"));
				robotCount++;
			}
			
			repaint();
			try {
				Thread.sleep(25);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * keyboard listener
	 * 
	 * @author Dawei Xu
	 *
	 */
	class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_1:
				keyValue = 1;
				y = 250;
				break;
			case KeyEvent.VK_2:
				keyValue = 2;
				y = 350;
				break;
			case KeyEvent.VK_ENTER:
				status = keyValue;
				playerList.add(playerone);
				if(status == 2) {
					playerList.add(playerTwo);
				}
				playerone.alive = true;
				playerTwo.alive = true;
				break;
			case KeyEvent.VK_P:
				if(status != 3) {
					keyValue = status;
					status = 3;
				}else {
					status = keyValue;
					if(keyValue == 0) {
						keyValue =1;
					}
				}

			default:
				playerone.keyPressed(e);
				playerTwo.keyPressed(e);
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			playerone.keyReleased(e);
			playerTwo.keyReleased(e);
		}
	}

	@Override
	public void paint(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(width, height);
		}
		Graphics gGraphics = offScreenImage.getGraphics();
		gGraphics.setColor(Color.gray);
		gGraphics.fillRect(0, 0, width, height);
		gGraphics.setColor(Color.blue);
		gGraphics.setFont(new Font(null, Font.BOLD, 60));
		if (status == 0) {
			gGraphics.drawString("Choose Game Mode", 150, 200);
			gGraphics.drawString("1 Player", 260, 300);
			gGraphics.drawString("2 Players", 260, 400);
			gGraphics.drawString("press 1 or 2 to choose mode", 60, 500);
			gGraphics.drawString("press enter to start game", 60, 600);

			gGraphics.drawImage(selectImage, 150, y, null);
		} else if (status == 1 || status == 2) {
			gGraphics.setFont(new Font("Arial", Font.BOLD, 20));
			gGraphics.setColor(Color.red);
			gGraphics.drawString("Enemy left: " + robotsList.size(), 15, 60);
			for(Tank player: playerList) {
				player.selfPaint(gGraphics);
			}
			
			for(Bullets bullet:bulletsList) {
				bullet.selfPaint(gGraphics);
			}
			bulletsList.removeAll(removeList);
			for(Robot robot: robotsList){
				robot.selfPaint(gGraphics);
			}
			
			for(Wall wall: wallList) {
				wall.selfPaint(gGraphics);
			}
			for(Home home: homeList) {
				home.selfPaint(gGraphics);
			}
			robotGraphicCount++;
		}
		
		else if(status ==3) {
			gGraphics.drawString("Pause", 300, 200);
		}
		else if(status ==4) {
			gGraphics.drawString("You Are Fu**", 200, 400);
		}
		else if(status ==5) {
			gGraphics.drawString("You Win", 300, 200);
		}
		g.drawImage(offScreenImage, 0, 0, null);

	}

	public int getWidth() {
		return this.width;
		
	}
	
	public int getHeight() {
		return this.height;
		
	}
	public static void main(String[] args) {
		GamePanel gamePanel = new GamePanel();
		gamePanel.launch();
	}

}
