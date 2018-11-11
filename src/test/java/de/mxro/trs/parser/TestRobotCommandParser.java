package de.mxro.trs.parser;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.mxro.trs.Command;
import de.mxro.trs.Direction;
import de.mxro.trs.ParseException;
import de.mxro.trs.commands.MoveCommand;
import de.mxro.trs.commands.PlaceCommand;
import de.mxro.trs.commands.ReportCommand;
import de.mxro.trs.commands.TurnLeftCommand;
import de.mxro.trs.commands.TurnRightCommand;

/**
 * <p>This abstract class provides a number of test cases that can be executed against implementations of {@link RobotCommandsParser}.
 * 
 *
 */
public abstract class TestRobotCommandParser {
	
	/**
	 * <p>Used to define the instance of {@link RobotCommandsParser} against which tests are to be executed.
	 * @return The parser instance to be used for running tests.
	 */
	protected abstract RobotCommandsParser getParser();
	
	@Test
	public void test_move_command() {
		Assert.assertEquals(Arrays.asList(new MoveCommand()), getParser().parse("MOVE"));
		Assert.assertEquals(Arrays.asList(new MoveCommand()), getParser().parse("MOVE\n"));
	}

	@Test
	public void test_left_command() {
		Assert.assertEquals(Arrays.asList(new TurnLeftCommand()), getParser().parse("LEFT"));
	}

	@Test
	public void test_right_command() {
		Assert.assertEquals(Arrays.asList(new TurnRightCommand()), getParser().parse("RIGHT"));
	}

	@Test
	public void test_report_command() {
		Assert.assertEquals(Arrays.asList(new ReportCommand()), getParser().parse("REPORT\n"));
	}

	@Test
	public void test_place_command() {
		Assert.assertEquals(Arrays.asList(new PlaceCommand(1, 2, Direction.SOUTH)),
				getParser().parse("PLACE 1,2,SOUTH"));
	}

	/**
	 * <p>
	 * Parsing supplied example case a)
	 */
	@Test
	public void test_sample_a() {
		String input = "PLACE 0,0,NORTH\n" + "MOVE\n" + "REPORT";

		List<Command> commands = getParser().parse(input);

		Assert.assertEquals(
				Arrays.asList(new PlaceCommand(0, 0, Direction.NORTH), new MoveCommand(), new ReportCommand()),
				commands);
	}

	/**
	 * <p>
	 * Parsing supplied example case b)
	 */
	@Test
	public void test_sample_b() {
		String input = "PLACE 0,0,NORTH\n" + "LEFT\n" + "REPORT";

		List<Command> commands = getParser().parse(input);

		Assert.assertEquals(
				Arrays.asList(new PlaceCommand(0, 0, Direction.NORTH), new TurnLeftCommand(), new ReportCommand()),
				commands);
	}

	/**
	 * <p>
	 * Parsing supplied example case c)
	 */
	@Test
	public void test_sample_c() {
		String input = "PLACE 1,2,EAST\n" + "MOVE\n" + "MOVE\n" + "LEFT\n" + "MOVE\n" + "REPORT";

		List<Command> commands = getParser().parse(input);

		Assert.assertEquals(Arrays.asList(new PlaceCommand(1, 2, Direction.EAST), new MoveCommand(), new MoveCommand(),
				new TurnLeftCommand(), new MoveCommand(), new ReportCommand()), commands);
	}
	
	/**
	 * <p>
	 * Parsing invalid command
	 */
	@Test(expected = ParseException.class)
	public void test_invalid_commands() {
		String input = "UPWARD\nONWARD\n";

		getParser().parse(input);

	}
	
	/**
	 * <p>
	 * Parsing invalid place coordinate input
	 */
	@Test(expected = ParseException.class)
	public void test_invalid_place_coordinates() {
		String input = "PLACE 1,notanumber,EAST";

		getParser().parse(input);

	}
	
	/**
	 * <p>
	 * Parsing invalid direction
	 */
	@Test(expected = ParseException.class)
	public void test_invalid_place_direction() {
		String input = "PLACE 1,1,SOUTHWEST";

		getParser().parse(input);

	}

}
