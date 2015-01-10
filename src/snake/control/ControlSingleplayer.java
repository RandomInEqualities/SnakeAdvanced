package snake.control;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import snake.view.*;

public class ControlSingleplayer implements ActionListener {
	private View view;

	public ControlSingleplayer(View view) {
		this.view = view;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		int inputWidth = getInput(view.getSingleplayer().getWidthInput());
		int inputHeight = getInput(view.getSingleplayer().getHeightInput());
		if (inputWidth >= 5 && inputWidth <= 100 && inputHeight >= 5
				&& inputHeight <= 100) {
			view.remove(view.getSingleplayer());
			view.remove(view.getHeader());
			view.startGame(inputWidth, inputHeight);
		} else { // if input is invalid
			view.getSingleplayer().setValid(false);
			view.getSingleplayer().repaint();
		}
	}
	
	/*public void mouseClicked(MouseEvent e) {
		if (view.getSingleplayer().getPlay_btn().contains(e.getX(), e.getY())) {
			int inputWidth = getInput(view.getSingleplayer().getWidthInput());
			int inputHeight = getInput(view.getSingleplayer().getHeightInput());
			if (inputWidth >= 5 && inputWidth <= 100 && inputHeight >= 5
					&& inputHeight <= 100) {
				view.remove(view.getSingleplayer());
				view.remove(view.getHeader());
				view.startGame(inputWidth, inputHeight);
			} else { // if input is invalid
				view.getSingleplayer().setValid(false);
				view.getSingleplayer().repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (view.getSingleplayer().getPlay_btn().contains(e.getX(), e.getY())) {
			view.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}*/

	// Get input
	public int getInput(JFormattedTextField input) {
		String in = input.getText();
		String out = "";

		// Remove whitespace
		for (int i = 0; i < in.length(); i++) {
			if (!Character.isWhitespace(in.charAt(i))) {
				out += in.charAt(i);
			}
		}
		int value = Integer.parseInt(out);

		return value;
	}
}