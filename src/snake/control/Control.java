
package snake.control;

import java.awt.event.*;

import javax.swing.JButton;

import snake.view.*;


public class Control extends KeyAdapter implements ActionListener{
	
	private View view;
	
	//@SuppressWarnings("unused")
	//private ControlBoardSingleplayer boardControl;
	@SuppressWarnings("unused")
	private ControlMenu menuControl;
	//@SuppressWarnings("unused")
	//private ControlMenuSingleplayer menuSingleplayerControl;
	//@SuppressWarnings("unused")
	//private ControlMenuMultiplayer menuMultiplayerControl;
	@SuppressWarnings("unused")
	private ControlMenuControls menuControlsControl;
	private JButton soundButton;
	
	public Control(View view) {
		if (view == null) {
			throw new NullPointerException();
		}
		this.view = view;
		view.addKeyListener(this);
		
		//boardControl = new ControlBoardSingleplayer(game, view, view.getViewBoard());
		menuControl = new ControlMenu(view);
		menuControlsControl = new ControlMenuControls(view);
		
		this.soundButton = view.getHeader().getSoundButton();
		soundButton.addActionListener(this);
		soundButton.setActionCommand("mute");
	}

	@Override
	public void keyPressed(KeyEvent event) {
		
		if (event == null) {
			throw new NullPointerException();
		}
		
		switch (event.getKeyCode()) {
			case KeyEvent.VK_M:
				toggleSound();
				break;
			case KeyEvent.VK_ESCAPE:
				view.showMenu();
				break;
			case KeyEvent.VK_T:
				view.getViewMenu().repaint();
			default:
				break;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "mute"){
			toggleSound();
		}
	}
	
	private void toggleSound(){
		Audio audio = view.getAudio();
		audio.setMuted(!audio.isMuted());
		view.getHeader().repaint();
		view.requestFocus();
	}
}
