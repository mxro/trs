package de.mxro.trs.engine;

import java.util.List;

import de.mxro.trs.Command;
import de.mxro.trs.Robot;
import de.mxro.trs.commands.ReportCommand;

/**
 * <p>Provides a simple implementation for rendering the results of a simulation. 
 * 
 */
public class RenderingEngine {

	/**
	 * <p>Renders the results of a simulation.
	 * <p>Both the commands and results need to be provided since the output of the application is
	 * limited to those results which are explicitly requested using the {@link ReportCommand} command.
	 * @param commands The commands for the simulation.
	 * @param results The results of the simulation.
	 * @return A text rendering of the results for the simulation.
	 */
	public static String renderReport(List<Command> commands, List<Robot> results) {
		assert commands.size() == results.size();
	
		StringBuilder res = new StringBuilder();
	
		for (int i = 0; i < commands.size(); i++) {
			// only provide output when the REPORT command was issued
			if (commands.get(i) instanceof ReportCommand) {
				Robot robot = results.get(i);
				res.append(robot.toString());
			}
		}
	
		return res.toString();
	}

}
