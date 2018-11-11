package de.mxro.trs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import de.mxro.trs.engine.RenderingEngine;
import de.mxro.trs.engine.SimulationEngine;
import de.mxro.trs.parser.RobotCommandParsers;

/**
 * <p>The facade class to interact with this library/application.
 *
 */
public class App {
	
	public static void main(String[] args) {
		if (args.length != 2 || !new File(args[1]).exists()) {
			System.err.println("This application expects one parameter with a valid file path.");
		}
		
		// parsing inputs and performing simulation
		String results = run(new File(args[1]), Arrays.asList(Validators.onTable(5, 5)));
		
		// printing results to stdout
		System.out.println(results);
	}
	
	
	private static String run(File commands, List<RobotValidator> validators) {
		
		// read file contents into string
		String input;
		try {
			input = new String(Files.readAllBytes(commands.toPath()), "UTF-8");
			
		} catch (IOException e) {
			throw new RuntimeException("Cannot read commands from file: "+commands, e);
		}
		
		// parse commands from string
		List<Command> commandsList = RobotCommandParsers.regex().parse(input);
		
		// run simulation and render the report for the simulation
		return RenderingEngine.renderReport(commandsList, SimulationEngine.run(commandsList, validators));
	}

}
