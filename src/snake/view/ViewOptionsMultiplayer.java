package snake.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;
import javax.swing.border.LineBorder;

import snake.model.*;


public class ViewOptionsMultiplayer extends ViewOptions {
	private JButton green, blue, red, yellow, green2, blue2, red2, yellow2;
	public ViewOptionsMultiplayer(View view, GameMultiplayer game){
		super(view, game.getBoard());
		
		//Colour buttons
		green = new JButton(new ImageIcon (Images.BUTTON_GREEN));
		setColourButtonParameters(green);
		green.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		blue = new JButton(new ImageIcon (Images.BUTTON_BLUE));
		setColourButtonParameters(blue);
		red = new JButton(new ImageIcon (Images.BUTTON_RED));
		setColourButtonParameters(red);
		yellow = new JButton(new ImageIcon(Images.BUTTON_YELLOW));
		setColourButtonParameters(yellow);
		green2 = new JButton(new ImageIcon(Images.BUTTON_GREEN));
		setColourButtonParameters(green2);
		green2.setBorder(new LineBorder(Colors.PANEL_COLOUR, 3));
		blue2 = new JButton(new ImageIcon(Images.BUTTON_BLUE));
		setColourButtonParameters(blue2);
		red2 = new JButton(new ImageIcon(Images.BUTTON_RED));
		setColourButtonParameters(red2);
		yellow2 = new JButton(new ImageIcon(Images.BUTTON_YELLOW));
		setColourButtonParameters(yellow2);
		
		// Add new buttons
		super.panelOptions.add(green);
		super.panelOptions.add(blue);
		super.panelOptions.add(red);
		super.panelOptions.add(yellow);
		super.panelOptions.add(green2);
		super.panelOptions.add(blue2);
		super.panelOptions.add(red2);
		super.panelOptions.add(yellow2);
	}
	
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		drawColourButtons(context2D);
	}
	
	public void drawColourButtons(Graphics2D context){
		context.setColor(Colors.PANEL_COLOUR);
		context.setFont(new Font("Sans_Serif", Font.BOLD, 12));
		context.drawString("Player 1", getWidth()/2-175, super.getPanel().getY()+180);
		context.drawString("Player 2", getWidth()/2+25, super.getPanel().getY()+180);
		
		int gap = 10;
		int shift = 100;
		int sizeColour = 30;
		int xBlue = super.getPanel().getWidth()/2-sizeColour-gap/2;
		int yColour = 190;
		green.setBounds(xBlue-gap-sizeColour-shift, yColour, sizeColour, sizeColour);
		blue.setBounds(xBlue-shift, yColour, sizeColour, sizeColour);
		red.setBounds(xBlue+gap+sizeColour-shift, yColour, sizeColour, sizeColour);
		yellow.setBounds(xBlue+2*gap+2*sizeColour-shift, yColour, sizeColour, sizeColour);
		green2.setBounds(xBlue-gap-sizeColour+shift, yColour, sizeColour, sizeColour);
		blue2.setBounds(xBlue+shift, yColour, sizeColour, sizeColour);
		red2.setBounds(xBlue+gap+sizeColour+shift, yColour, sizeColour, sizeColour);
		yellow2.setBounds(xBlue+2*gap+2*sizeColour+shift, yColour, sizeColour, sizeColour);
	}
	
	public JButton getGreenButton() {
		return green;
	}
	
	public JButton getBlueButton() {
		return blue;
	}
	
	public JButton getRedButton() {
		return red;
	}
	
	public JButton getYellowButton() {
		return yellow;
	}
	
	public JButton getGreenButton2() {
		return green2;
	}
	
	public JButton getBlueButton2() {
		return blue2;
	}
	
	public JButton getRedButton2() {
		return red2;
	}
	
	public JButton getYellowButton2() {
		return yellow2;
	}
}