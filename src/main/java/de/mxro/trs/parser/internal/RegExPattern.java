package de.mxro.trs.parser.internal;

import java.util.regex.Pattern;

import de.mxro.trs.Command;

/**
 * <p>A pattern which can be applied against a line of input with commands for moving the robot. 
 *
 */
public abstract class RegExPattern {
	
	protected final Pattern pattern;
	
	/**
	 * <p>Checks if this particular pattern applies to a line of input.
	 * @param line A line with a command.
	 * @return <code>true</code> If the pattern applies to the input, <code>false</code> otherwise.
	 */
	public boolean match(String line) {
		return pattern.matcher(line).find();
	}
	
	/**
	 * <p>Creates a new {@link Command} based on a line of input which contains the definiton of this command.
	 * <p>This method should only be called after a {@link RegExPattern#match(String)} is asserted.
	 * 
	 * @param line The line of input describing the command.
	 * @return The newly created instance of {@link Command}
	 */
	public abstract Command parseCommand(String line);

	public RegExPattern(String regex) {
		super();
		this.pattern = Pattern.compile(regex);
	}

	
	
}
