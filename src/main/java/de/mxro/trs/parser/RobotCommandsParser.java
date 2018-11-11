package de.mxro.trs.parser;

import java.util.List;

import de.mxro.trs.Command;

/**
 * <p>
 * Provides a simple interface against which parsers can be implemented to read
 * out the language of robot commands.
 *
 */
public interface RobotCommandsParser {

	/**
	 * <p>
	 * Parses a text input with commands for a robot and generates a list of
	 * {@link Command} objects.
	 * 
	 * @param input
	 *            A text with commands.
	 * @return The list of commands defined in the text.
	 */
	public List<Command> parse(String input);

}
