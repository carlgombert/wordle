package com.sandbox.main;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.opencsv.CSVReader;

//game class handles the functionality of the game
public class Game {
	
	public static int score;
	public static int highScore;
	private static int row;
	private static int column;
	private static String word;
	
	private static List<String> records;
	
	private static Color backgroundColor = new Color(36, 36, 36);
	private static Color blankLetterColor = new Color(30, 30, 30);
	private static Color outLineLetterColor = new Color(40, 40, 40);
	private static Color correctLetterColor = new Color(10, 36, 9);
	private static Color goodLetterColor = new Color(145, 127, 6);
	private static Color wrongLetterColor = new Color(102, 12, 9);
	
	public Game() {
		importData();
		word = getWord();
		score = 0;
		highScore = 0;
		row = 0;
		column = 0;
		System.out.println(word);
	}
	
	//method for adding a letter into the current letterBox and shifting forward
	public static void addLetter(String letter) {
		if(column <= 4) {
			Frame.letterBoxes[row][column].setText(letter);
			Frame.letterBoxes[row][column].setHorizontalTextPosition(SwingConstants.CENTER);
			Frame.letterBoxes[row][column].setVerticalTextPosition(SwingConstants.CENTER);
			column++;
		}
	}
	
	//method for removing the letter from the current letterBox and shifting backward
	public static void removeLetter() {
		if(column > 0) {
			column--;
			Frame.letterBoxes[row][column].setText("");
		}
		
	}
	
	//check if the word given is valid, if so check how the letters match up to the word, if not,
	//display invalid word label
	public static void checkWord() {
		String word = "";
		//creating the submitted word from the players input
		for(int i = 0; i < 5; i++) {
			if(Frame.letterBoxes[row][i].getText() != "") {
				word += Frame.letterBoxes[row][i].getText();
			}
		}
		if(validWord(word)) {
			checkLetters();
			checkWin(word);
		}
		else {
			Frame.invalidWord.setVisible(true);
		}
	}
	
	//if the word data set contains the guessed word, return true, else return false
	private static boolean validWord(String word) {
		if(records.contains(word)) {
			return true;
		}
		return false;
	}
	
	//method to check how the letters match up to the word
	private static void checkLetters() {
		String letter;
		for(int i = 0; i < 5; i++) {
			letter = Frame.letterBoxes[row][i].getText();
			//if letter is the right letter in the right spot
			if(Game.word.substring(i, i+1).equals(letter)) {
					Frame.letterBoxes[row][i].setBackground(correctLetterColor);
					Frame.keyMap.get(letter).setBackground(correctLetterColor);
			}
			//if the word contains the letter
			else if(Game.word.contains(letter)) {
					Frame.letterBoxes[row][i].setBackground(goodLetterColor);
					Frame.keyMap.get(letter).setBackground(goodLetterColor);
			}
			//if the word doesn't contain the letter
			else {
				Frame.letterBoxes[row][i].setBackground(wrongLetterColor);
				Frame.keyMap.get(letter).setBackground(wrongLetterColor);
			}
		}
		row++;
		column = 0;
	}
	
	//method to check if the user entered word equals the word, if so, start a new game and add
	//points
	private static void checkWin(String word) {
		if(word.equals(Game.word)) {
			score += 100*(6 - row);
			Frame.scoreLabel.setText("SCORE: " + score);
			if(score > highScore) {
				highScore = score;
				Frame.highScoreLabel.setText(("HIGH SCORE: " + highScore));
			}
			Frame.resultLabel.setText("GREAT JOB");
			Frame.resultLabel.setForeground(correctLetterColor);
			Frame.newRoundButton.setVisible(true);
		}
		else if(row > 5) {
			failRound();
		}
	}
	
	//set points to zero and start a new round
	private static void failRound() {
		Frame.resultLabel.setText("<html>THE WORD WAS:<br/>" + word + "</html>");
		Frame.resultLabel.setForeground(wrongLetterColor);
		score = 0;
		Frame.scoreLabel.setText("SCORE: " + score);
		Frame.newRoundButton.setVisible(true);
	}
	
	//clear all of the letters and colors off of the board, grab a new word and set current
	//letter box to the first one
	public static void newGame(){
		Frame.resultLabel.setText("");
		Frame.newRoundButton.setVisible(false);
		char[] charArr = "qwertyuiopasdfghjklzxcvbnm".toCharArray();
		row = 0;
		column = 0;
		word = getWord();
		//Iterate through each letter box of each row and set the next to nothing and the color to
		//the default letter box color
		for(int i = 0; i < 6; i++) {
			for(int n = 0; n < 5; n++) {
				Frame.letterBoxes[i][n].setBackground(blankLetterColor);
				Frame.letterBoxes[i][n].setText("");
			}
		}
		//grab all key labels from map and set their color to the default color
		for(int i = 0; i < charArr.length; i++) {
			Frame.keyMap.get(String.valueOf(charArr[i])).setBackground(blankLetterColor);
		}
	}
	
	//grab random word from data set
	private static String getWord() {
		Random rand = new Random();
		return records.get(rand.nextInt((records.size()) + 1));
	}
	
	//import data from data set into array list
	private static void importData() {
		records = new ArrayList<String>();
    	try (CSVReader csvReader = new CSVReader(new FileReader("res/fiveWordDataSet.csv"))) {
    	    String[] values = null;
    	    while ((values = csvReader.readNext()) != null) {
    	        records.add(values[0]);
    	        
    	    }
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	//getters and setters for the colors
	public static Color getBackgroundColor() {
		return backgroundColor;
	}

	public static void setBackgroundColor(Color backgroundColor) {
		Game.backgroundColor = backgroundColor;
	}

	public static Color getWrongLetterColor() {
		return wrongLetterColor;
	}

	public static void setWrongLetterColor(Color wrongLetterColor) {
		Game.wrongLetterColor = wrongLetterColor;
	}

	public static Color getGoodLetterColor() {
		return goodLetterColor;
	}

	public static void setGoodLetterColor(Color goodLetterColor) {
		Game.goodLetterColor = goodLetterColor;
	}

	public static Color getCorrectLetterColor() {
		return correctLetterColor;
	}

	public static void setCorrectLetterColor(Color correctLetterColor) {
		Game.correctLetterColor = correctLetterColor;
	}

	public static Color getOutLineLetterColor() {
		return outLineLetterColor;
	}

	public static void setOutLineLetterColor(Color outLineLetterColor) {
		Game.outLineLetterColor = outLineLetterColor;
	}

	public static Color getBlankLetterColor() {
		return blankLetterColor;
	}

	public static void setBlankLetterColor(Color blankLetterColor) {
		Game.blankLetterColor = blankLetterColor;
	}


}
