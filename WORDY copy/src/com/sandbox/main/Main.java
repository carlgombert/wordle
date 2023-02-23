package com.sandbox.main;

import java.awt.event.*;

public class Main extends KeyAdapter{
	
	static int width = (int) (640*1.5);
	static int height = width/12 * 9;
	
	public static void main(String[]args) {
		Frame frame = new Frame(width, height, "res/GladlyAcceptRegular-Wyjov.ttf");
		Game game = new Game();
	}
}