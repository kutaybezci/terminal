package com.kutaybezci.terminal.draw;

import com.googlecode.lanterna.TextColor;

public enum KeyColor {
	BLACK(TextColor.ANSI.BLACK, 'B'), //
	RED(TextColor.ANSI.RED, 'R'), //
	GREEN(TextColor.ANSI.GREEN, 'G'), //
	YELLOW(TextColor.ANSI.YELLOW, 'Y'), //
	BLUE(TextColor.ANSI.BLUE, 'b'), //
	MAGENTA(TextColor.ANSI.MAGENTA, 'M'), //
	CYAN(TextColor.ANSI.CYAN, 'C'), //
	WHITE(TextColor.ANSI.WHITE, 'G'), //
	DEFAULT(TextColor.ANSI.DEFAULT, 'D');

	TextColor.ANSI color;
	char c;

	KeyColor(TextColor.ANSI color, char c) {
		this.color = color;
		this.c = c;
	}

	public static KeyColor convert(char c) {
		for (KeyColor k : KeyColor.values()) {
			if (k.c == c) {
				return k;
			}
		}
		return null;
	}
}
