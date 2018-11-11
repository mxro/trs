package de.mxro.trs.parser.internal.pattern;

import de.mxro.trs.Command;
import de.mxro.trs.parser.internal.RegExPattern;

/**
 * <p>Parses a simple command which does not require any parameters.
 *
 */
public class SimpleCommandRegExPattern extends RegExPattern {
	
	private final Command command;
		
	@Override
	public Command parseCommand(String line) {
		return command;
	}
	
	public SimpleCommandRegExPattern(String regex, Command command) {
		super(regex);
		this.command = command;	
	}
	

}
