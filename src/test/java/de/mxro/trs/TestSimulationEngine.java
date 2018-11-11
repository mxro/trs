package de.mxro.trs;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.mxro.trs.commands.MoveCommand;
import de.mxro.trs.commands.PlaceCommand;
import de.mxro.trs.commands.ReportCommand;
import de.mxro.trs.commands.TurnLeftCommand;
import de.mxro.trs.engine.RenderingEngine;
import de.mxro.trs.engine.SimulationEngine;
import de.mxro.trs.internal.OffTableRobot;
import de.mxro.trs.internal.PlacedRobot;

/**
 * 
 * <p>
 * This test asserts the functionality for the core simulation engine.
 * <p>
 * Due to the importance and complexity of this aspect of the application, a
 * larger number of tests is provided, including various edge cases.
 *
 */
public class TestSimulationEngine {

	/**
	 * <p>
	 * Supplied test case:
	 * 
	 * <pre>
	    PLACE 0,0,NORTH
		MOVE
		REPORT
		Output: 0,1,NORTH
	 * </pre>
	 * 
	 */
	@Test
	public void test_supplied_case_a() {
		List<Command> commands = Arrays.asList(new PlaceCommand(0, 0, Direction.NORTH), new MoveCommand(),
				new ReportCommand());
		List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

		assertLastResult(new PlacedRobot(0, 1, Direction.NORTH), results);
	}

	/**
	 * <p>
	 * Supplied test case:
	 * 
	 * <pre>
	    PLACE 0,0,NORTH
		LEFT
		REPORT
		Output: 0,0,WEST
	 * </pre>
	 * 
	 */
	@Test
	public void test_supplied_case_b() {
		List<Command> commands = Arrays.asList(new PlaceCommand(0, 0, Direction.NORTH), new TurnLeftCommand(),
				new ReportCommand());
		List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

		assertLastResult(new PlacedRobot(0, 0, Direction.WEST), results);
	}

	/**
	 * <p>
	 * Supplied test case:
	 * 
	 * <pre>
	    PLACE 1,2,EAST
		MOVE
		MOVE
		LEFT
		MOVE
		REPORT
		Output: 3,3,NORTH
	 * </pre>
	 * 
	 */
	@Test
	public void test_supplied_case_c() {
		List<Command> commands = Arrays.asList(new PlaceCommand(1, 2, Direction.EAST), new MoveCommand(),
				new MoveCommand(), new TurnLeftCommand(), new MoveCommand(), new ReportCommand());
		List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

		assertLastResult(new PlacedRobot(3, 3, Direction.NORTH), results);
	}

	/**
	 * <p>
	 * Test moving the robot off the table.
	 * <p>
	 * The commands moving the robot off the table should be ignored.
	 */
	@Test
	public void test_move_off_table() {
		// south
		{
			List<Command> commands = Arrays.asList(new PlaceCommand(1, 1, Direction.SOUTH), new MoveCommand(),
					new MoveCommand(), new MoveCommand(), new ReportCommand());
			List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

			assertLastResult(new PlacedRobot(1, 0, Direction.SOUTH), results);
		}
		// north
		{
			List<Command> commands = Arrays.asList(new PlaceCommand(4, 4, Direction.NORTH), new MoveCommand(),
					new MoveCommand(), new MoveCommand(), new ReportCommand());
			List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

			assertLastResult(new PlacedRobot(4, 5, Direction.NORTH), results);
		}

		// west
		{
			List<Command> commands = Arrays.asList(new PlaceCommand(4, 4, Direction.EAST), new MoveCommand(),
					new MoveCommand(), new MoveCommand(), new ReportCommand());
			List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

			assertLastResult(new PlacedRobot(5, 4, Direction.EAST), results);
		}

		// east
		{
			List<Command> commands = Arrays.asList(new PlaceCommand(1, 1, Direction.WEST), new MoveCommand(),
					new MoveCommand(), new MoveCommand(), new ReportCommand());
			List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

			assertLastResult(new PlacedRobot(0, 1, Direction.WEST), results);
		}

	}

	/**
	 * <p>
	 * Test placing the robot off the table.
	 * <p>
	 * Commands placing the robot off the table should be ignored.
	 */
	@Test
	public void test_place_off_table() {
		{
			List<Command> commands = Arrays.asList(new PlaceCommand(-1, -1, Direction.WEST), new ReportCommand());
			List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

			Assert.assertEquals(Arrays.asList(new OffTableRobot(), new OffTableRobot()), results);
		}

		{
			List<Command> commands = Arrays.asList(new PlaceCommand(6, 5, Direction.WEST), new ReportCommand());
			List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

			Assert.assertEquals(Arrays.asList(new OffTableRobot(), new OffTableRobot()), results);
		}

		{
			List<Command> commands = Arrays.asList(new PlaceCommand(6, 1, Direction.EAST), new ReportCommand());
			List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

			Assert.assertEquals(Arrays.asList(new OffTableRobot(), new OffTableRobot()), results);
		}

		{
			List<Command> commands = Arrays.asList(new PlaceCommand(0, -1, Direction.WEST), new ReportCommand());
			List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

			Assert.assertEquals(Arrays.asList(new OffTableRobot(), new OffTableRobot()), results);
		}

	}

	@Test
	public void test_multiple_place_commands() {
		List<Command> commands = Arrays.asList(new PlaceCommand(-1, -1, Direction.WEST),
				new PlaceCommand(1, 1, Direction.EAST), new ReportCommand());
		List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

		Assert.assertEquals(new OffTableRobot(), results.get(0));
		assertLastResult(new PlacedRobot(1, 1, Direction.EAST), results);

	}

	private static <Item> void assertLastResult(Item robot, List<Item> results) {
		Assert.assertEquals(robot, results.get(results.size() - 1));
	}

}
