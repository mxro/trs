package de.mxro.trs.parser.internal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.mxro.trs.Command;
import de.mxro.trs.ParseException;
import de.mxro.trs.commands.MoveCommand;
import de.mxro.trs.commands.ReportCommand;
import de.mxro.trs.commands.TurnLeftCommand;
import de.mxro.trs.commands.TurnRightCommand;
import de.mxro.trs.parser.RobotCommandsParser;
import de.mxro.trs.parser.internal.pattern.PlaceRegexPattern;
import de.mxro.trs.parser.internal.pattern.SimpleCommandRegExPattern;

/**
 * <p>
 * Provides a very simple implementation to parse text containing commands for
 * the robot.
 * <p>
 * This implementation is based on applying simple regular expressions against
 * input lines.
 *
 */
public class RegExRobotCommandParser implements RobotCommandsParser {

	private final List<RegExPattern> patterns;

	@Override
	public List<Command> parse(String input) {

		String[] lines = input.split("\n");

		// parse each line of the input
		return Stream.of(lines).map(line -> {
			// determine the pattern which matches this line
			List<RegExPattern> matchingPatterns = patterns.stream().filter(pattern -> pattern.match(line))
					.collect(Collectors.toList());

			// expects exactly one pattern to match
			if (matchingPatterns.size() != 1) {
				throw new ParseException("Invalid line in input: '" + line + "'");
			}

			return matchingPatterns.get(0).parseCommand(line);

		}).collect(Collectors.toList());
	}

	public RegExRobotCommandParser() {
		super();
		// Create the pattern instances used to parse the text.
		// This list can easily be easily be extended when new commands are required.
		// However, if the complexity of the grammar increases, it would be recommended
		// to use a more sophisticated approach for parsing the input such as using 
		// ANTLR.
		this.patterns = Arrays.asList(new SimpleCommandRegExPattern("^MOVE$", new MoveCommand()),
				new SimpleCommandRegExPattern("^LEFT$", new TurnLeftCommand()),
				new SimpleCommandRegExPattern("^RIGHT$", new TurnRightCommand()),
				new SimpleCommandRegExPattern("^REPORT$", new ReportCommand()), new PlaceRegexPattern());
	}

}
