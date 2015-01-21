package snake.view;

import java.awt.*;
import javax.swing.*;
import java.util.*;

import snake.model.*;

public class BoardPanel extends JPanel implements Observer {
	
	private static final long serialVersionUID = 9109362543987437505L;
	private static final Color SNAKE_COLOUR = new Color(0.153f, 0.68f, 0.38f);
	private static final Color FOOD_COLOUR = new Color(0.953f, 0.68f, 0.38f);
	private static final Color BACKGROUND_COLOUR = new Color(0.7451f, 0.7647f,0.78f);
	
	private Game game;

	public BoardPanel(Game game) {
		super();

		if (game == null) {
			throw new NullPointerException();
		}
		game.addObserver(this);
		this.game = game;
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}

	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		//Tile background
		drawLevel(context2D);
		drawFood(context2D);
		drawSnake(context2D);
	}

	private void drawLevel(Graphics2D context) {
		// Draw a level outline.
		context.setColor(BACKGROUND_COLOUR);
		context.fill(getWindowBoard());
	}
	
	private void drawSnake(Graphics2D context) {
		Snake snake = game.getSnake();

		// Draw the whole snake.
		context.setColor(SNAKE_COLOUR);
		for (Field position : snake.getPositions()) {
			context.fill(getWindowRectangle(position));
		}

	}

	private void drawFood(Graphics2D context) {
		Rectangle foodRectangle = getWindowRectangle(game.getFood().getPosition());
		context.setColor(FOOD_COLOUR);
		context.fill(foodRectangle);
	}

	public Rectangle getWindowRectangle(Field position) {
		Dimension patchSize = getPatchSize();
		Rectangle rectangle = new Rectangle(
				position.getColumn() * patchSize.width + getWindowBoard().x, 
				position.getRow() * patchSize.height + getWindowBoard().y,
				patchSize.width, patchSize.height
		);
		return rectangle;
	}

	public Rectangle getWindowBoard() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoardSize();
		Dimension patchSize = getPatchSize();
		int offsetWidth = (windowSize.width - patchSize.width * gameSize.width)/2;
		int offsetHeight = (windowSize.height - patchSize.height * gameSize.height)/2;
		Rectangle rectangle = new Rectangle(
				offsetWidth, 
				offsetHeight, 
				windowSize.width - 2*offsetWidth, 
				windowSize.height - 2*offsetHeight
		);
		return rectangle;
	}
	
	public Dimension getPatchSize() {
		Dimension windowSize = getSize();
		Dimension gameSize = game.getBoardSize();
		int patchWidth = windowSize.width / gameSize.width;
		int patchHeight = windowSize.height / gameSize.height;
		return new Dimension(patchWidth, patchHeight);
	}
	
}
