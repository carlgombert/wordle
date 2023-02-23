package com.sandbox.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter implements ActionListener{
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		char ch = e.getKeyChar();
		
		//if the round is over the player cannot continue to type
		if(Frame.newRoundButton.isVisible() == false) {
			//if what the player typed is a letter add that letter
			if ("abcdefghijklmnopqrstuvwxyz".contains(String.valueOf(ch))) {
				Game.addLetter(String.valueOf(ch));
			}
			
			//if key == delete (VK_DELETE was not working)
			if(key == 8) {
				Frame.invalidWord.setVisible(false);
				Game.removeLetter();
			}
			
			//if the key the player typed == enter
			if(key == 10) {
				Game.checkWord();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		//if the player clicks the new round button start a new round
		if(e.getSource() == Frame.newRoundButton) {
			Game.newGame();
		}
		
	}

}
