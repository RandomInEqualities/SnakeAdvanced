
package snake.view;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import snake.control.ControlGame;
import snake.control.ControlKeys;
import snake.control.ControlTimer;
import snake.model.*;
import snake.model.Game.State;

public class View extends JFrame{

	private Menu menu;
	private BoardPanel boardPanel;
	private Singleplayer splayer;
	private Header header;
	private Game game;
	private BoardPanel board;
	
	public View(Game game) {
		super();			
		//Menu
		this.header = new Header();
		this.menu = new Menu();
		this.splayer = new Singleplayer(this);
		this.boardPanel = null;
		getContentPane().add(header, BorderLayout.NORTH);
		getContentPane().add(menu, BorderLayout.CENTER);
		
		setTitle("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(550, 650));
		pack();
		setLocationRelativeTo(null);
	}

	public Header getHeader(){
		return header;
	}
	
	public Menu getMenu() {
		return menu;
	}

	public Singleplayer getSingleplayer(){
		return splayer;
	}
	
	public BoardPanel getBoard() {
		return boardPanel;
	}
	
	public void startGame(int width, int height) {
		game = new Game(width, height);
		boardPanel = new BoardPanel(game, menu);
		boardPanel.setSize(boardPanel.getPreferredSize());
		ScorePanel scorePanel = new ScorePanel(game, boardPanel);
		getContentPane().add(scorePanel, BorderLayout.NORTH);
		getContentPane().add(boardPanel, BorderLayout.CENTER);
		this.revalidate();
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.requestFocus();
		ControlTimer control = new ControlTimer(game, this);
		ControlKeys controlKeys = new ControlKeys(game, this);
		ControlGame controlButton = new ControlGame(game, this);
	}
}








