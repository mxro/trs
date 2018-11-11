package de.mxro.trs.parser;

import de.mxro.trs.parser.internal.RegExRobotCommandParser;

/**
 * <p>
 * Provides an easy way to access different implementations for
 * {@link RobotCommandsParser}.
 * <p>
 * Currently there is only one parser implementation available. This
 * implementation is very simplistic and will likely need to be replaced if
 * operations defined for the robot would become more complex - for instance
 * supporting if statements or loops.
 *
 */
public class RobotCommandParsers {

	/**
	 * <p>
	 * Creates a new instance of the simple, regular expressions based parser
	 * {@link RegExRobotCommandParser}.
	 * 
	 * @return The new parser instance.
	 */
	public static final RobotCommandsParser regex() {
		return new RegExRobotCommandParser();
	}

}
