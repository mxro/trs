package de.mxro.trs.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import de.mxro.trs.Command;
import de.mxro.trs.Robot;
import de.mxro.trs.RobotValidator;
import de.mxro.trs.internal.OffTableRobot;

/**
 * <p>
 * Performs simulations of robot movements on the table.
 *
 */
public class SimulationEngine {

	/**
	 * <p>
	 * Runs a simulation based on a sequence of commands and validators defining the
	 * constraints for the commands.
	 * 
	 * @param commands
	 *            The list of commands to be executed. Only valid commands will be
	 *            executed.
	 * @param validators
	 *            The validators commands are to be validated against.
	 * @return A list of states for the Robot. The last item in the list represents
	 *         the state of the robot at the end of the simulation.
	 */
	public static List<Robot> run(List<Command> commands, List<RobotValidator> validators) {

		List<Robot> results = new ArrayList<Robot>(commands.size());
		// Simulation always starts with the robot being off the table.
		Robot robot = new OffTableRobot();
		// Looping through the commands rather than using a more 'functional' approach
		// since this problem itself nicely to an iterative solution (alternatively
		// recursion could be applied, of course).
		for (Command command : commands) {
			// Determine the new state of the robot after a command is applied.
			final Robot newState = command.apply(robot);

			// Assure the new state is valid. Only then record the new state. Otherwise
			// record the previous state of the robot.
			Stream<Boolean> validationResults = validators.stream().map(v -> v.validate(newState));
			Stream<Boolean> notValid = validationResults.filter(b -> !b);
			if (notValid.findAny().isPresent() == false) {
				robot = newState;
			}
			results.add(robot);

		}
		return results;
	}

}
