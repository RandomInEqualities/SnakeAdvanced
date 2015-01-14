package snake.control;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import snake.model.*;
import snake.view.*;

public class ControlMenuSingleplayer extends ControlMenuOptions {
	private GameSinglePlayer game;
	private View view;
	private ViewMenuSingleplayer viewMenuSingleplayer;
	private JButton green, blue, red, yellow;
	private Border thickBorder;
	
	public ControlMenuSingleplayer(GameSinglePlayer game, View view) {
		super(view);
		this.game = game;
		this.view = view;
		this.viewMenuSingleplayer = view.getMenuSingleplayer();
		this.view.addKeyListener(this);
		
		green = this.viewMenuSingleplayer.getGreenButton();
		blue = this.viewMenuSingleplayer.getBlueButton();
		red = this.viewMenuSingleplayer.getRedButton();
		yellow = this.viewMenuSingleplayer.getYellowButton();
		game.disableTimedMovement(); //default difficulty = kindergarten
		
		green.addActionListener(this);
		blue.addActionListener(this);
		red.addActionListener(this);
		yellow.addActionListener(this);

		green.setActionCommand("green");
		blue.setActionCommand("blue");
		red.setActionCommand("red");
		yellow.setActionCommand("yellow");
		
		thickBorder = new LineBorder(Colors.PANEL_COLOUR, 3);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		
		if (event.getActionCommand() == "play") {
			playGame();
		}  
		else if (event.getActionCommand() == "green") {
			setActiveButton(green, blue, red, yellow);
			view.getViewBoard().setSnakeColor(new Color(84, 216, 81));
		} 
		else if (event.getActionCommand() == "blue") {
			setActiveButton(blue, green, red, yellow);
			view.getViewBoard().setSnakeColor(new Color(80, 152, 218));
		} 
		else if (event.getActionCommand() == "red"){
			setActiveButton(red, green, blue, yellow);
			view.getViewBoard().setSnakeColor(new Color(237, 75, 66));
		} 
		else if (event.getActionCommand() == "yellow"){
			setActiveButton(yellow, green, blue, red);
			view.getViewBoard().setSnakeColor(new Color(243, 196, 67));
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event == null) {
			throw new NullPointerException();
		}
		
		if (view.getViewState() != View.State.IN_MENU_SINGLEPLAYER) {
			return;
		}

		switch (event.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE:
				view.showMenu();
				viewMenuSingleplayer.setValid(true);
				viewMenuSingleplayer.setFilled(true);
				break;
			case KeyEvent.VK_ENTER:
				playGame();
				break;
			default:
				break;
		}
	}
	
	public void playGame() {
		String inputW = getInput(viewMenuSingleplayer.getWidthInput());
		String inputH = getInput(viewMenuSingleplayer.getHeightInput());

		if (inputW.isEmpty() || inputH.isEmpty()) { // if no input
			viewMenuSingleplayer.setFilled(false);
			viewMenuSingleplayer.repaint();
		} 
		else {
			// if input is correct
			int inputWidth = Integer.parseInt(inputW);
			int inputHeight = Integer.parseInt(inputH);
			if (inputWidth >= Board.MIN_WIDTH && inputWidth <= Board.MAX_WIDTH && inputHeight >= Board.MIN_HEIGHT && inputHeight <= Board.MAX_HEIGHT) {
				GameSinglePlayer game = new GameSinglePlayer(inputWidth, inputHeight);
				view.showGame();
				viewMenuSingleplayer.setValid(true);
				viewMenuSingleplayer.setFilled(true);
			} 
			else {
				// input is invalid
				viewMenuSingleplayer.setValid(false);
				viewMenuSingleplayer.repaint();
			}
		}
	}

	// Get input without whitespace
	public String getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = in.replace(" ", "");
		return out;
	}	
}
