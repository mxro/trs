package de.mxro.trs;

import de.mxro.trs.commands.MoveCommand;

/**
 * 
 * <p>A command for the toy robot.
 *
 */
public interface Command {
	
	/**
	 * <p>This method applies this command for the specified robot such as {@link MoveCommand}.
	 * <p>The state of the passed {@link Robot} object is not changed. Instead, a new {@link Robot} object is created.
	 *  
	 * @param robot The robot for which the command is to be applied.
	 * @return A new {@link Robot} object with the new state after the command is applied.
	 */
	public Robot apply(Robot robot);
	
}
