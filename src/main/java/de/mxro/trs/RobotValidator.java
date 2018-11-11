package de.mxro.trs;

/**
 * <p>Validates whether a {@link Robot} object is in a valid state.
 *
 */
public interface RobotValidator {
	
	/**
	 * <p>Determine if the provided {@link Robot} object is in a valid state.
	 * @param robot The robot that needs to be validated.
	 * @return <code>true</code> if the robot is valid, <code>false</code> otherwise.
	 */
	public boolean validate(Robot robot);
	
}
