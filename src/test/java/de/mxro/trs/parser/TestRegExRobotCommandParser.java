package de.mxro.trs.parser;

import de.mxro.trs.parser.internal.RegExRobotCommandParser;

/**
 * <p>This test asserts the functionality of {@link RegExRobotCommandParser} using the template {@link TestRobotCommandParser}.
 *
 */
public class TestRegExRobotCommandParser extends TestRobotCommandParser {

	@Override
	protected RobotCommandsParser getParser() {
		return RobotCommandParsers.regex();
	}

}
