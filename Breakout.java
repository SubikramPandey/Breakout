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
	

	private static final Color[] colorArray = new Color[] 
			{Color.RED, Color.orange, Color.yellow, Color.green, Color.BLUE};

	
	/** Animation delay or paust time between ball moves */   
    private static final int DELAY = 10;
    
    private boolean ballInPlay;
    private int bricksLeft;
	private static double BALL_START_X = (WIDTH / 2) - BALL_RADIUS;
	private static double BALL_START_Y = (APPLICATION_HEIGHT / 2) - BALL_RADIUS;
	
/* Method: run() 
 * SetUpGame puts brick, paddle and ball on screen
 * PlayGame starts a game of three turns
 * 
 * */
/** Runs the Breakout program. */
	public void run() {
		SetUpGameInitialy();
		playGame();
	}
	
	
	// starts 3 round game. Displays if user won or lost. Play again
	private void playGame() {
		int roundsLeft = NTURNS;
		while (roundsLeft > 0) {
			setupNewRound();
			playRound();
			roundsLeft -= 1;
			println("end round");
		}
		println("End of Game");
		showEndGameScreen();
	}
	
	//shows if won or lost, gives option to play new game
	private void showEndGameScreen() {
		
	}
	
	// start round conditions 
	// starts round on user click,
	// state vars like bricks left
	private void setupNewRound() {
		ballInPlay = true;
		bricksLeft = NBRICK_ROWS * NBRICKS_PER_ROW;
		putBallInCenter();
		println("new round");
	}
	
	private void putBallInCenter() {
		ball.setLocation(BALL_START_X, BALL_START_Y);
	}
	
	// bounces ball
	// Deletes bricks hit by the ball
	// paddle moves when user moves mouse
	// ends when if ball hits the bottom wall, or there are no more bricks
	private void playRound() {
		println("Click to start new round");
		waitForClick();
		intializeBallVelocity();
		ballInPlay = true;
		while(ballInPlay) {
			moveBall();
			pause(DELAY);	// slow ball movement update so game is playable
		}
	}
	
	private void intializeBallVelocity(){
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		vy = 3;
	}
	
	
	private void moveBall() {
		ball.move(vx, vy);
		double ballX = ball.getX();
		double ballY = ball.getY();
		bounceOffWall(ballX, ballY);
		collisions(ballX, ballY);
	}
	
	
	private void bounceOffWall(double ballX, double ballY) {
		// bounces off sides of wall
		if ((ballX < 0 ) | (ballX + BALL_RADIUS) > APPLICATION_WIDTH) {
			vx = -vx;
		}
		// bounce off top of the screen
		if (ballY < 0)  {vy = -vy;}
		// ball out of play if it hits the bottom of the screen
		else if (ballY + BALL_RADIUS > APPLICATION_HEIGHT) {
			ballInPlay = false;
		}
	}
	
	// check 4 points if colliding with paddle or brick
	// Delete brick
	// Paddle: reveres direction of ball
	private void collisions(double ballX, double ballY) {
		double ball_diameter = 2 * BALL_RADIUS;
		if (getCollidingObject(ballX, ballY) != null) {
			collisionEffects(ballX, ballY);
		}
		
		else if (getCollidingObject(ballX + ball_diameter, ballY) != null) {
			collisionEffects(ballX + ball_diameter, ballY);
		}
		
		else if (getCollidingObject(ballX, ballY + ball_diameter) != null) {
			collisionEffects(ballX, ballY + ball_diameter);
		}
		
		else if (getCollidingObject(ballX + ball_diameter, 
				ballY + ball_diameter) != null) {
			collisionEffects(ballX + ball_diameter, 
					ballY + ball_diameter);
		}
	}
	
	private GObject getCollidingObject(double x, double y) {
		GObject collider = (getElementAt(ball.getX(),ball.getY()));
		return(collider);
	}
	
	private void collisionEffects(double x, double y) {
		GObject collider = getCollidingObject(x, y);
		vy = -vy;
		if (collider != paddle) {
			remove(collider);
			bricksLeft -= 1;}
		println(bricksLeft);
	}

	
	// PlaceGameObjects creates and places bricks, paddle and ball on screen
	// ResetGameStats sets turnsLeft to 0 
	private void SetUpGameInitialy() {
		// set screen size
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		PlaceGameObjects();
		addMouseListeners();
	}
	
	// calls helper functions that create and place each of the game objects
	private void PlaceGameObjects() {
		PlaceWall();
		PlacePaddle();
		PlaceBall();
	}
	
	/*
	 * Puts bricks of varying color in rows at the top of the screen based on constants
	 * centerd using window size
	 *  repeat 10 times - changing color every 2 times
	 */
	private void PlaceWall() {
		for (int currentRowNum=0; currentRowNum < NBRICK_ROWS; currentRowNum++ ){
			makeRow(currentRowNum);
		}
	}
	
	// find offset for first brick
	// places colored bricks for n_bricks-row
	private void makeRow(int currentRowNum) {
		// Getting initial values for row
		double xBrick = getFirstBrickOffset();
		int yBrick = getBrickY(currentRowNum);
		// color changes every other row
		Color rowColor = colorArray[currentRowNum / 2 ]; 
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			add(placeRect(xBrick, yBrick,BRICK_WIDTH , BRICK_HEIGHT, rowColor));
			xBrick += BRICK_WIDTH + BRICK_SEP;
		}
	}
	
	private GRect placeRect(double xBrick, double yBrick, int RectWidth,
			int RectHeight, Color rectColor) {
		GRect rect = new GRect(xBrick, yBrick, RectWidth, RectHeight);
		rect.setFilled(true);
	    rect.setColor(rectColor);
		return (rect);
	}
	
	/*
	 * used to make the entire wall centered on the screen, 
	 * because the application window is larger then the wall
	 */
	private double getFirstBrickOffset() {
		double totalBrickRowWidth = NBRICKS_PER_ROW * (BRICK_WIDTH + BRICK_SEP);
		double extraSpaceInRow = APPLICATION_WIDTH - totalBrickRowWidth;
		double firstBrickOffset = (extraSpaceInRow / 2);
		return(firstBrickOffset);
	}
	
	// gives y value for bricks in the given row
	private int getBrickY(int currentRowNum) {
		return (currentRowNum * (BRICK_HEIGHT + BRICK_SEP) + BRICK_Y_OFFSET);
	}
	
	
	private void PlacePaddle() {
		double xPaddle = getPaddleX();
		double yPaddle = getPaddleY();
		paddle = placeRect(xPaddle, yPaddle, PADDLE_WIDTH, PADDLE_HEIGHT, Color.black);
		add(paddle);
	}	
	  
	private double getPaddleX() {
		double screenMidpoint = WIDTH / 2;
		double halfPaddleLength =  PADDLE_WIDTH / 2;
		double xPaddle = screenMidpoint - halfPaddleLength;
		return (xPaddle);
	}
	
	private double getPaddleY() {
		return(HEIGHT - PADDLE_Y_OFFSET);
	}
	
	private void PlaceBall() {
		ball = new GOval(BALL_START_X, BALL_START_Y, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}
	
	// moves the paddle horizontally 
	// so the the center of paddle is to mouse location 
	// prevents paddle form moving off screen
	public void mouseMoved(MouseEvent e) {
		double paddleCenter = e.getX();
		// stop paddle from going off left side of screen
		if(paddleCenter - (PADDLE_WIDTH / 2) < 0 ) {
			paddleCenter = (PADDLE_WIDTH / 2);
		}
		// stop paddle from going off screen right side
		else if (paddleCenter +  (PADDLE_WIDTH / 2) > APPLICATION_WIDTH) {
			paddleCenter = APPLICATION_WIDTH - (PADDLE_WIDTH / 2);
		}
		double xPaddle = paddleCenter - (PADDLE_WIDTH / 2);
		paddle.setLocation(xPaddle, (HEIGHT - PADDLE_Y_OFFSET));
		
	}
	
	/*** instance variables */
	private double vx, vy;
	private  GRect paddle;
	private GOval ball;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	

}
