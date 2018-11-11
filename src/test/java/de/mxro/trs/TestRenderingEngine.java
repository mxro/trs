package de.mxro.trs;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.mxro.trs.commands.MoveCommand;
import de.mxro.trs.commands.PlaceCommand;
import de.mxro.trs.commands.ReportCommand;
import de.mxro.trs.engine.RenderingEngine;
import de.mxro.trs.engine.SimulationEngine;

public class TestRenderingEngine {

	@Test
	public void test_rendering_supplied_case_a() {
		List<Command> commands = Arrays.asList(new PlaceCommand(0, 0, Direction.NORTH), new MoveCommand(),
				new ReportCommand());
		List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));
		
		Assert.assertEquals("0,1,NORTH", RenderingEngine.renderReport(commands, results));
	}
	
}
