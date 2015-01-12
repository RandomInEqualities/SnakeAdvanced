
package snake.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.*;
import java.util.*;

import snake.model.*;

public class ViewBoard extends JPanel implements Observer {
	
	private Game game;
	private View view;
	private JButton playAgainButton, menuButton;
	private static final long serialVersionUID = 9109362543987437505L;

	int widthPopup, heightPopup, xPopup, yPopup;
	int snakeRed, snakeGreen, snakeBlue;
	
	public ViewBoard(Game game, View view) {
		super();

		if (game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.view = view;
		game.addObserver(this);
		setBackground(Colors.PANEL_COLOUR);
		
		playAgainButton = new JButton(new ImageIcon(Images.BUTTON_PLAY_AGAIN));
		view.getViewMenu().setCommonButtonParameters(playAgainButton);
		menuButton = new JButton(new ImageIcon(Images.BUTTON_MENU));
		view.getViewMenu().setCommonButtonParameters(menuButton);
		
		//Default colour
		this.snakeRed = 84;
		this.snakeGreen = 216;
		this.snakeBlue = 81;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (game.getState() == Game.State.LOST || game.getState() == Game.State.WON) {
			this.add(playAgainButton);
			this.add(menuButton);
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		drawBackground(context2D);
		drawBoard(context2D);
		drawFood(context2D);
		drawSnake(context2D);
		
		if (game.getState() == Game.State.LOST) {
			drawGameLost(context2D);
		} 
		else if (game.getState() == Game.State.PAUSED) {
			drawPaused(context2D);
		} 
		else if (game.getState() == Game.State.WON){
			drawGameWon(context2D);
		}
	}

	private void drawBoard(Graphics2D context) {
		context.setColor(Colors.BOARD_COLOUR);
		context.fill(getRectangleForBoard());
	}

	private void drawSnake(Graphics2D context) {
		Snake snake = game.getSnake();
		int lastRow = game.getBoard().getHeight()-1;
		int lastColumn = game.getBoard().getWidth()-1;
		
		//Draw body
		for (int i = 1; i<snake.getPositions().size()-1; i++){ //run through every body piece
			Rectangle bodyRectangle = getRectangleForField(snake.getPositions().get(i)); //the piece' position
			Field current = snake.getPositions().get(i);
			Field front = snake.getPositions().get(i-1);
			Field behind = snake.getPositions().get(i+1);
			BufferedImage body = null;

			if (current.getRow() == front.getRow() && current.getRow() == behind.getRow()){ //if current piece and front and behind are in the same row 
				body = Images.SNAKE_HORIZONTAL;
			} else if (current.getColumn() == front.getColumn() && current.getColumn()==behind.getColumn()){ //if current piece and front and behind are in the same column
				body = Images.SNAKE_VERTICAL;
				//Corner pieces
			} else if (current.getColumn()+1 == front.getColumn() && current.getRow()+1 == behind.getRow()//piece in the middle of the board
					||current.getColumn()+1 == behind.getColumn() && current.getRow()+1 == front.getRow()
					||current.getRow() == lastRow && current.getColumn()+1 == front.getColumn() && behind.getRow() == 0 //piece in the bottom row
					||current.getRow() == lastRow && current.getColumn()+1 == behind.getColumn() && front.getRow() == 0
					||current.getColumn() == lastColumn && current.getRow()+1 == front.getRow() && behind.getColumn() == 0 //piece in the last column
					||current.getColumn() == lastColumn && current.getRow()+1 == behind.getRow() && front.getColumn() == 0
					||current.getColumn() == lastColumn && current.getRow() == lastRow && front.getColumn() == 0 && behind.getRow() == 0 //piece in corner
					||current.getColumn() == lastColumn && current.getRow() == lastRow && behind.getColumn() == 0 && front.getRow() == 0){
				body = Images.SNAKE_CORNER_TL;
			} else if (current.getColumn()-1 == front.getColumn() && current.getRow()+1 == behind.getRow() //piece in the middle of the board
					||current.getColumn()-1 == behind.getColumn() && current.getRow()+1 == front.getRow()
					||current.getRow() == lastRow && current.getColumn()-1 == front.getColumn() && behind.getRow() == 0 //piece in the bottom row
					||current.getRow() == lastRow && current.getColumn()-1 == behind.getColumn() && front.getRow() == 0
					||current.getColumn() == 0 && current.getRow()+1 == front.getRow() && behind.getColumn() == lastColumn //piece in the first column
					||current.getColumn() == 0 && current.getRow()+1 == behind.getRow() && front.getColumn() == lastColumn
					||current.getColumn()==0 && current.getRow() == lastRow && front.getRow() == 0 && behind.getColumn() == lastColumn
					||current.getColumn()==0 && current.getRow() == lastRow && behind.getRow() == 0 && front.getColumn() == lastColumn){
				body = Images.SNAKE_CORNER_TR;
			} else if (current.getColumn()-1 == front.getColumn() && current.getRow()-1 == behind.getRow() //piece in the middle of the board
					||current.getColumn()-1 == behind.getColumn() && current.getRow()-1 == front.getRow()
					||current.getRow() == 0 && current.getColumn()-1 == front.getColumn() && behind.getRow() == lastRow //piece in the first row
					||current.getRow() == 0 && current.getColumn()-1 == behind.getColumn() && front.getRow() == lastRow
					||current.getColumn() == 0 && current.getRow()-1 == front.getRow() && behind.getColumn() == lastColumn //piece in the first column
					||current.getColumn() == 0 && current.getRow()-1 == behind.getRow() && front.getColumn() == lastColumn
					||current.getColumn() == 0 && current.getRow() == 0 && front.getColumn() == lastColumn && behind.getRow() == lastRow //piece in corner
					||current.getColumn() == 0 && current.getRow() == 0 && behind.getColumn() == lastColumn && front.getRow() == lastRow){
				body = Images.SNAKE_CORNER_BR;
			} else {
				body = Images.SNAKE_CORNER_BL;
			}
			BufferedImage bodyColoured = colourSnake(body, snakeRed, snakeGreen, snakeBlue);
			Image bodyscaled = bodyColoured.getScaledInstance(getFieldSideLength(), getFieldSideLength(), Image.SCALE_SMOOTH);
			context.drawImage(bodyscaled, bodyRectangle.x, bodyRectangle.y, null);
		}
			
		// Draw tail
		Rectangle tailRectangle = getRectangleForField(snake.getTail());
		BufferedImage tail = null;
		Field tailPiece = snake.getTail();
		Field beforeTail = snake.getPositions().get(snake.getSize()-2);
		if (tailPiece.getColumn()+1 == beforeTail.getColumn()||tailPiece.getColumn() == lastColumn && beforeTail.getColumn()==0){
			tail = Images.SNAKE_TAIL_RIGHT;
		} else if (tailPiece.getColumn()-1 == beforeTail.getColumn() || tailPiece.getColumn()==0 && beforeTail.getColumn()==lastColumn){
			tail = Images.SNAKE_TAIL_LEFT;
		} else if (tailPiece.getRow()-1 == beforeTail.getRow() || tailPiece.getRow() == 0 && beforeTail.getRow()==lastRow){
			tail = Images.SNAKE_TAIL_UP;
		} else {
			tail = Images.SNAKE_TAIL_DOWN;
		}
		BufferedImage tailColoured = colourSnake(tail, snakeRed, snakeGreen, snakeBlue);
		Image tailscaled = tailColoured.getScaledInstance(getFieldSideLength(), getFieldSideLength(), Image.SCALE_SMOOTH);
		context.drawImage(tailscaled, tailRectangle.x, tailRectangle.y, null);
		
		
		// Draw head
		Rectangle headRectangle = getRectangleForField(snake.getHead());
		BufferedImage head = null;
		switch (snake.getHeadDirection()) {
		case UP:
			head = Images.SNAKE_HEAD_UP;
			break;
		case DOWN:
			head = Images.SNAKE_HEAD_DOWN;
			break;
		case LEFT:
			head = Images.SNAKE_HEAD_LEFT;
			break;
		case RIGHT:
			head = Images.SNAKE_HEAD_RIGHT;
			break;
		}
		
		BufferedImage headColoured = colourSnake(head, snakeRed, snakeGreen, snakeBlue);
		Image headScaled = headColoured.getScaledInstance(headRectangle.width, headRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(headScaled, headRectangle.x, headRectangle.y, null);
	}

	private void drawFood(Graphics2D context) {
		Rectangle foodRectangle = getRectangleForField(game.getFood().getPosition());
		Image scaledApple = Images.APPLE.getScaledInstance(foodRectangle.width, foodRectangle.height, Image.SCALE_SMOOTH);
		context.drawImage(scaledApple, foodRectangle.x, foodRectangle.y, null);
	}
	
	private void drawPopup(Graphics2D context) {
		// Behind
		context.setColor(Colors.TRANSPARENT_BLACK);
		context.fillRect(0, 0, getWidth(), getHeight());

		// Popup
		widthPopup = getSize().width;
		heightPopup = 200;
		xPopup = 0;
		yPopup = getRectangleForBoard().y + getRectangleForBoard().height/2 - heightPopup/2;
		context.setColor(Colors.POPUP_COLOUR);
		context.fillRect(xPopup, yPopup, widthPopup, heightPopup);
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

	}
	private void drawGameLost(Graphics2D context) {
		drawGameOver(context);
		// Title
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width/2 - Images.TITLE_GAME_OVER.getWidth()/2;
		int y2 = yPopup;
		context.drawImage(Images.TITLE_GAME_OVER, x2, y2, null);
		
	}
	private void drawGameWon(Graphics2D context){
		drawGameOver(context);
		// Title
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width/2 - Images.TITLE_GAME_WON.getWidth()/2;
		int y2 = yPopup;
		context.drawImage(Images.TITLE_GAME_WON, x2, y2, null);
	}
	
	private void drawGameOver(Graphics2D context) {
		view.getHeader().hideScore();
		drawPopup(context);

		// Text
		String scoreTxt = "Final Score: " + game.getScore();
		int x3 = getRectangleForBoard().x + getRectangleForBoard().width/2 - scoreTxt.length()*10/2;
		int y3 = getRectangleForBoard().y + getRectangleForBoard().height/2 + 5;
		context.setColor(Colors.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(scoreTxt, x3, y3);

		// Buttons
		int buttonWidth = 140;
		int buttonHeight = 50;
		int xPlayAgain = getRectangleForBoard().x + getRectangleForBoard().width/2 - buttonWidth/2 - 100;
		int xMenu = getRectangleForBoard().x + getRectangleForBoard().width/2 - buttonWidth/2 + 100;
		int yPlayAgain = yPopup + heightPopup - buttonHeight - 20;

		playAgainButton.setBounds(xPlayAgain, yPlayAgain, buttonWidth, buttonHeight);
		menuButton.setBounds(xMenu, yPlayAgain, buttonWidth, buttonHeight);
	}

	private void drawPaused(Graphics2D context) {
		drawPopup(context);

		// Title
		int x2 = getRectangleForBoard().x + getRectangleForBoard().width/2 - Images.TITLE_PAUSED.getWidth()/2;
		int y2 = yPopup;
		context.drawImage(Images.TITLE_PAUSED, x2, y2, null);

		// Text
		String pauseMessage = "Press 'P' to resume game";
		int x3 = getRectangleForBoard().x + getRectangleForBoard().width/2 - pauseMessage.length()*10/2;
		int y3 = getRectangleForBoard().y + getRectangleForBoard().height/2 + 25;
		context.setColor(Colors.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 20));
		context.drawString(pauseMessage, x3, y3);
	}
	
	public void drawBackground(Graphics2D context) {
		for (int x = 0; x < getWidth(); x += Images.BACKGROUND.getWidth()) {
			for (int y = 0; y < getHeight(); y += Images.BACKGROUND.getHeight()) {
				context.drawImage(Images.BACKGROUND, x, y, this);
			}
		}
	}
	
	public JButton getPlayAgainButton() {
		return playAgainButton;
	}
	
	public JButton getMenuButton() {
		return menuButton;
	}
	
	public void removeButtons() {
		remove(playAgainButton);
		remove(menuButton);
	}
	
	public Rectangle getRectangleForField(Field position) {
		Rectangle boardRectangle = getRectangleForBoard();
		int fieldSideLength = getFieldSideLength();
		Rectangle rectangle = new Rectangle(
			position.getColumn()* fieldSideLength + boardRectangle.x, 
			position.getRow() * fieldSideLength + boardRectangle.y, 
			fieldSideLength,
			fieldSideLength
		);
		return rectangle;
	}

	public Rectangle getRectangleForBoard() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoard().getDimension();
		int fieldSideLength = getFieldSideLength();

		int offsetHeight = 10;
		int offsetWidth = (windowSize.width - fieldSideLength * gameSize.width) / 2;

		Rectangle rectangle = new Rectangle(
			offsetWidth, 
			offsetHeight, 
			fieldSideLength * gameSize.width, 
			fieldSideLength * gameSize.height
		);
		return rectangle;
	}
	
	public int getFieldSideLength() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoard().getDimension();
		int fieldWidth = windowSize.width / gameSize.width;
		int fieldHeight = (windowSize.height-20) / gameSize.height;
		if (fieldWidth >= fieldHeight) {
			fieldWidth = fieldHeight;
		} else {
			fieldHeight = fieldWidth;
		}
		return fieldWidth;
	}
	
	public void setSnakeColour(int red, int green, int blue){
		this.snakeRed = red;
		this.snakeGreen = green;
		this.snakeBlue = blue;
	}
	
	 private BufferedImage colourSnake(BufferedImage image, int red, int green, int blue) {
	    WritableRaster raster = image.getRaster();
	    for (int x = 0; x < image.getWidth(); x++) {
	    	for (int y = 0; y < image.getHeight(); y++) {
	    		if (image.getRGB(x, y) != -13547430){ //don't colour the eye pixels
	    			int[] colour = raster.getPixel(x, y, (int[]) null);
	            	colour[0] = red;
	            	colour[1] = green;
	            	colour[2] = blue;
	            	raster.setPixel(x, y, colour);
            	}
            }
        }
	    return image;
	 }
}
