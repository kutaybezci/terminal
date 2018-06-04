package com.kutaybezci.terminal.draw;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;

public class Draw extends Thread {
	private Screen screen;
	private boolean work;

	public Draw(Terminal terminal) throws IOException {
		this.screen = new TerminalScreen(terminal);
		this.screen.startScreen();
		this.screen.setCursorPosition(new TerminalPosition(0, 0));
		this.work = true;
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
		}
		if (keyStroke.getKeyType() == KeyType.ArrowUp) {
			cursor = cursor.withRelativeRow(-1);
		}
		if (keyStroke.getKeyType() == KeyType.ArrowRight) {
			cursor = cursor.withRelativeColumn(1);
		}
		if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
			cursor = cursor.withRelativeColumn(-1);
		}
		this.screen.setCursorPosition(cursor);
		if (keyStroke.getKeyType() == KeyType.Character) {
			TextCharacter textCharacter = screen.getBackCharacter(cursor);
			if (keyStroke.isAltDown()) {
				KeyColor keyColor = KeyColor.convert(keyStroke.getCharacter());
				if (keyColor != null) {
					textCharacter = textCharacter.withBackgroundColor(keyColor.color);
				}
			} else {
				textCharacter = textCharacter.withCharacter(keyStroke.getCharacter());
			}
			this.screen.setCharacter(cursor, textCharacter);
		}

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
