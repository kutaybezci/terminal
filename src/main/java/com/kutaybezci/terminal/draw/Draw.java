package com.kutaybezci.terminal.draw;

import java.io.IOException;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;

public class Draw extends Thread {
	private static final String HEADER=" RED: %d GREEN: %d BLUE:%d";
	private static final String FOOTER="FOOTER";
	public enum PickedColor{
		RED,GREEN,BLUE
	}
	
	private Screen screen;
	private boolean work;
	private StringBuilder buffer=new StringBuilder();
	private int red,green, blue,x ,y;
	private TextCharacter textCharacter=new TextCharacter(' ', TextColor.ANSI.DEFAULT, new TextColor.RGB(red, green, blue), new SGR[] {});
	private PickedColor pickedColor=PickedColor.RED;
	private Frame frame;

	public Draw(Terminal terminal, TerminalSize terminalSize) throws IOException {
		this.screen = new TerminalScreen(terminal);
		this.screen.startScreen();
		this.screen.setCursorPosition(new TerminalPosition(0, 0));
		this.work = true;
		this.frame=new Frame();
		this.frame.setFooter(FOOTER);
		this.frame.setSize(terminalSize);
		this.frame.setStart(new TerminalPosition(0, 1));
	}
	
	public void setTextCharacter() {
		this.textCharacter=new TextCharacter(' ',TextColor.ANSI.DEFAULT, new TextColor.RGB(red, green,blue),new SGR[] {});
		this.screen.setCharacter(0,0, this.textCharacter);
	}
	
	public void setRGB(int number) {
		if(number>255) {
			number=255;
		}else if(number<0) {
			number=0;
		}
		this.red=number;
		this.green=number;
		this.blue=number;
	}
	

	public void processInput() throws IOException {
		KeyStroke keyStroke = screen.pollInput();
		if (keyStroke == null) {
			return;
		}
		if (keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.EOF) {
			this.work = false;
			return;
		}
		TerminalPosition cursor = screen.getCursorPosition();
		if (keyStroke.getKeyType() == KeyType.ArrowDown) {
			cursor = cursor.withRelativeRow(1);
		}else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
			cursor = cursor.withRelativeRow(-1);
		}else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
			cursor = cursor.withRelativeColumn(1);
		}else if(keyStroke.getKeyType() == KeyType.ArrowLeft) {
			cursor = cursor.withRelativeColumn(-1);
		}else if(keyStroke.getKeyType()==KeyType.Enter) {
			setRGB(255);
		}else if(keyStroke.getKeyType()==KeyType.Delete||keyStroke.getKeyType()==KeyType.Backspace) {
			setRGB(0);
		}else if(keyStroke.getKeyType()==KeyType.Character) {
			if(' '==keyStroke.getCharacter()) {
				this.screen.setCharacter(cursor, textCharacter);
			}else if('R'==keyStroke.getCharacter() ||'r'==keyStroke.getCharacter()) {
				this.pickedColor=PickedColor.RED;
				this.buffer=new StringBuilder();
			}else if('B'==keyStroke.getCharacter() ||'b'==keyStroke.getCharacter()) {
				this.pickedColor=PickedColor.BLUE;
				this.buffer=new StringBuilder();
			}else if('G'==keyStroke.getCharacter() ||'g'==keyStroke.getCharacter()) {
				this.pickedColor=PickedColor.GREEN;
				this.buffer=new StringBuilder();
			}else if(Character.isDigit(keyStroke.getCharacter())) {
				this.buffer.append(keyStroke.getCharacter());
				int color=Integer.parseInt(buffer.toString());
				if(color>255) {
					color=255;
				}
				if(PickedColor.RED==pickedColor) {
					this.red=color;
				}else if(PickedColor.GREEN==pickedColor) {
					this.green=color;
				}else if(PickedColor.BLUE==pickedColor) {
					this.blue=color;
				}
			}
		}
		frame.setHeader(String.format(HEADER, red,green, blue));
		frame.draw(screen);
		this.screen.setCursorPosition(cursor);
		setTextCharacter();
		this.screen.refresh();
		this.screen.doResizeIfNecessary();
	}

	@Override
	public void run() {
		try {
			while (this.work) {
				processInput();
				sleep(100);
			}
			this.screen.stopScreen();
			this.screen.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
