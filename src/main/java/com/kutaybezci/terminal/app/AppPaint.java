package com.kutaybezci.terminal.app;

import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.kutaybezci.terminal.draw.Draw;

public class AppPaint {

	public static void main(String arg[]) throws IOException {
		DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
		Terminal terminal = defaultTerminalFactory.createTerminal();
		Draw draw = new Draw(terminal, new TerminalSize(60, 20));
		draw.start();
	}
}
