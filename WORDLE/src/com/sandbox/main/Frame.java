package com.sandbox.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

//frame class handles visual aspects of the program
public class Frame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private Font font;
	private Font font2;
	private static JPanel homePanel;
	public static JButton newRoundButton;
	public static JLabel resultLabel;
	public static JLabel invalidWord;
	public static JLabel scoreLabel;
	public static JLabel highScoreLabel;
	public static JLabel[][] letterBoxes;
	public static HashMap<String, JLabel> keyMap;
	
	//Constructor
	public Frame(int width, int height, String fontFile) {
		
		//the spacing of my frame has been broken up into units each is 1/15th of the width
		int unit = width / 15;
		
		this.width = width;
		this.height = height;
		this.font = makeFont(fontFile, 40f);
		this.font2 = makeFont(fontFile, 22f);
		
		//setting JFrame preferences 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setSize(width, height);
		this.addKeyListener(new KeyInput());
		
		//creating JLabels
		invalidWord = createLabel(invalidWord, "INVALID WORD", unit*5, 20, unit*5, unit, this.font);
		invalidWord.setForeground(Game.getWrongLetterColor());
		invalidWord.setVisible(false);
		
		scoreLabel = createLabel(scoreLabel, "SCORE: 0", 0, 20, unit*5, unit, this.font2);
		scoreLabel.setForeground(new Color(50, 50, 50));
		
		highScoreLabel = createLabel(highScoreLabel, "HIGH SCORE: 0", unit*10, 20, unit*5, unit, this.font2);
		highScoreLabel.setForeground(new Color(50, 50, 50));
		
		//result label stores either good job if the player wins or what the word was if the player
		//looses
		resultLabel = createLabel(highScoreLabel, "", unit*11, unit*4, unit*3, unit, this.font2);
		
		//setting the look and feel to be the cross platform version so that the program does not
		//look different on each system
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch(Exception e){
			  e.printStackTrace(); 
		}
		
		//creating the home panel which everything else will be on top of
		homePanel = new JPanel();
		homePanel.setBounds(0, 0, width, height);
		homePanel.setLayout(null);
		homePanel.setSize(width, height);
		homePanel.setBackground(Game.getBackgroundColor());
		
		//creating the panel to start new round
		newRoundButton = new JButton("NEW ROUND");
		newRoundButton.setFont(font2);
		newRoundButton.setBounds(unit*11, unit*5, unit*3, unit);
		newRoundButton.setBackground(Game.getOutLineLetterColor());
		newRoundButton.setBorderPainted(false);
		newRoundButton.setFocusPainted(false);
		newRoundButton.addActionListener(new KeyInput());
		newRoundButton.setFocusable(false);
		
		//adding newRoundPanel to home panel and setting invisible
		homePanel.add(newRoundButton);
		newRoundButton.setVisible(false);
		
		//adding labels to home panel
		homePanel.add(invalidWord);
		homePanel.add(scoreLabel);
		homePanel.add(highScoreLabel);
		homePanel.add(resultLabel);
		
		//creating and saving data for key labels and letter boxes
		Frame.letterBoxes = createLetterBoxes();
		Frame.keyMap = createKeyBoard();
		
		//adding home panel onto the jframe
		this.add(homePanel);
		
		//setting the JFrame visible
		this.setVisible(true);
		
	}
	
	//creating 2d array of the letter boxes and adding them to the home panel
	public JLabel[][] createLetterBoxes() {
		JLabel[][] arr = new JLabel[6][5];
		int unit = width / 15;
		int top = unit;
		int side = unit * 5;
		for(int i = 0; i < 6; i++) {
			for(int n = 0; n < 5; n++) {
				arr[i][n] = createLabel(new JLabel(), "", side, top, unit, unit, font, Game.getBlankLetterColor(), Game.getOutLineLetterColor());
				homePanel.add(arr[i][n]);
				side += unit;
			}
			side = unit*5;
			top += unit;
		}
		return arr;
	}
	
	
	//method to create the key board tiles a the bottom of the screen and then map letters to them
	//so when I need to change the color of a specific letters tile I can
	public HashMap<String, JLabel> createKeyBoard() {
		String letter;
		char[] charArr = "qwertyuiopasdfghjklzxcvbnm".toCharArray();
		HashMap<String, JLabel> keyMap = new HashMap<>();
		int unit = width / 15;
		int top = (int) (unit*7.5);
		int side = (int)(unit*2.5);
		for(int i = 0; i < 10; i++) {
			letter = String.valueOf(charArr[i]);
			keyMap.put(letter, createLabel(new JLabel(), letter, side, top, unit, unit, font, Game.getBlankLetterColor(), Game.getOutLineLetterColor()));
			homePanel.add(keyMap.get(letter));
			side += unit;
		}
		side = unit*3;
		top += unit;
		for(int i = 10; i < 19; i++) {
			letter = String.valueOf(charArr[i]);
			keyMap.put(letter, createLabel(new JLabel(), letter, side, top, unit, unit, font, Game.getBlankLetterColor(), Game.getOutLineLetterColor()));
			homePanel.add(keyMap.get(letter));
			side += unit;
		}
		side = unit*4;
		top += unit;
		for(int i = 19; i < 26; i++) {
			letter = String.valueOf(charArr[i]);
			keyMap.put(letter, createLabel(new JLabel(), letter, side, top, unit, unit, font, Game.getBlankLetterColor(), Game.getOutLineLetterColor()));
			homePanel.add(keyMap.get(letter));
			side += unit;
		}
		return keyMap;
	}
	
	//method for creating a basic label
	public JLabel createLabel(JLabel label, String text, int x, int y, int width, int height, Font font) {
		label = new JLabel(text, SwingConstants.CENTER);
		label.setLocation(x, y);
		label.setSize(width, height);
		label.setFont(font);
		return label;
	}
	
	//method for creating a more complicated label with background and border
	public JLabel createLabel(JLabel label, String text, int x, int y, int width, int height, Font font, Color backgroundColor, Color borderColor) {
		label = new JLabel(text, SwingConstants.CENTER);
		label.setLayout(new BorderLayout());
		label.setBounds(x, y, width, height);
		label.setFont(font);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createLineBorder(borderColor));
		label.setBackground(backgroundColor);
		return label;
	}
	
	//private method that makes font out of ttf file.
	private Font makeFont(String file, float size) {
		Font customFont = null;
		
		//creating the font to use
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File(file)).deriveFont(size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //registering the font
		    ge.registerFont(customFont);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return customFont;
	}
}
