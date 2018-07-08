package com.kutaybezci.terminal.draw;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Frame {


	private TerminalPosition start;
	private TerminalSize size;
	private String header;
	private String footer;
	
	public boolean isInFrame(TerminalPosition terminalPosition) {
		int diffColumn=terminalPosition.getColumn()-start.getColumn();
		int diffRow=terminalPosition.getRow()-start.getRow();
		if(diffColumn>size.getColumns()) {
			return false;
		}
		if(diffColumn<0) {
			return false;
		}
		
		if(diffRow>size.getRows()) {
			return false;
		}
		
		if(diffRow<0) {
			return false;
		}
		return true;
	}
	
	public void draw(Screen screen) {
		TextGraphics textGraphics=screen.newTextGraphics();
		textGraphics.putString(this.start.getColumn(), this.start.getRow()-1, this.header);
		textGraphics.drawRectangle(this.start, this.size, 'X');
		textGraphics.putString(this.start.getColumn(), this.start.getRow()+this.size.getRows()+1, this.footer);
	}
	
	public TerminalPosition getStart() {
		return start;
	}

	public void setStart(TerminalPosition start) {
		this.start = start;
	}

	public TerminalSize getSize() {
		return size;
	}

	public void setSize(TerminalSize size) {
		this.size = size;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

}
