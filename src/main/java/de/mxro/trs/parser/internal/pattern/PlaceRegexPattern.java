package de.mxro.trs.parser.internal.pattern;

import java.util.regex.Matcher;

import de.mxro.trs.Command;
import de.mxro.trs.Direction;
import de.mxro.trs.ParseException;
import de.mxro.trs.commands.PlaceCommand;
import de.mxro.trs.parser.internal.RegExPattern;

/**
 * <p>Parses a {@link PlaceCommand} command.
 */
public class PlaceRegexPattern extends RegExPattern {

	@Override
	public Command parseCommand(String line) {
		 Matcher m = pattern.matcher(line);
		 m.find();
			 
		 try {
			 return new PlaceCommand(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2)), Direction.fromText(m.group(3)));
		 } catch (NumberFormatException ex) {
			 throw new ParseException("Invalid PLACE declaration: "+line, ex);
		 }
	}

	public PlaceRegexPattern() {
		super("^PLACE ([^,]*),([^,]*),(.*)$");
		
	}

}
