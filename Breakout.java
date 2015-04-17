/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */


import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	
	private static final Color[] colorArray = new Color[] {Color.RED, Color.orange, 
		Color.yellow, Color.green, Color.BLUE};

/* Method: run() 
 * SetUpGame puts brick, paddle and ball on screen
 * PlayGame starts a game of three turns
 * 
 * */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		SetUpGameInitialy();
		
	}

	
	// PlaceGameObjects creates and places bricks, paddle and ball on screen
	// ResetGameStats sets turnsLeft to 0 
	private void SetUpGameInitialy() {
		PlaceGameObjects();
		ResetGameStats();
	}
	
	
	// ResetGameStats set turnsLeft to 0 
	private int ResetGameStats() {
		int turnsLeft = 3;
		return (turnsLeft);
	}
	
	// calls helper functions that create and place each of the game objects
	private void PlaceGameObjects() {
		PlaceWall();
		PlaceBall();
		PlacePaddle();
	}
	
	/*
	 * Puts bricks of varying color in rows at the top of the screen based on constants
	 * centerd using window size
	 */
	private void PlaceWall() {
		// make row
		// repeat 10 times - changing color every 2 times
		
		/* 
		 * Initial wall traits TICKET
		 * TICKET where should color array go?
		 */
		for (int currentRowNum=0; currentRowNum < (NBRICK_ROWS -1); currentRowNum++ ){
			makeRow(currentRowNum);
		}
	}
	
	// find offset for first brick
	// places colored bricks for n_bricks-row
	private void makeRow(int currentRowNum) {
		// intial values for row
		double xBrick = getFirstBrickOffset();
		int yBrick = currentRowNum * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET;
		
		Color rowColor = colorArray[currentRowNum / 2 ];
		
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			putNextBrick(xBrick, yBrick, rowColor);
			xBrick += BRICK_WIDTH + BRICK_SEP;
		}
		
	}
	
	private void putNextBrick(double xBrick, int yBrick, Color rowColor) {
		GRect rect = new GRect(xBrick, yBrick, BRICK_WIDTH , BRICK_HEIGHT);
		rect.setFilled(true);
	    rect.setColor(rowColor);
		add(rect);
	}
	
	
		private double getFirstBrickOffset() {
			double totalBrickRowWidth = NBRICKS_PER_ROW * (BRICK_WIDTH + BRICK_SEP);
			double extraSpaceInRow = APPLICATION_WIDTH - totalBrickRowWidth;
			double firstBrickOffset = extraSpaceInRow / 2;
			return(firstBrickOffset);
	}
	
	private void PlaceBall() {
		
	}
	
	private void PlacePaddle() {
		
	}
}
