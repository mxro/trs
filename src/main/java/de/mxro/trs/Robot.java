package de.mxro.trs;

/**
 * <p>A toy robot which might move around a playing field.
 * <p>All methods for this object are not applied to the object directly. Instead,
 * new {@link Robot} objects are created. This is to assure greater immutability. 
 *
 */
public interface Robot {
		
	/**
	 * <p>Moves the robot forward when on a field.
	 * @return The robot with the new state after the movement.
	 */
	public Robot move();
	
	/**
	 * <p>Turns the robot to the right when on a field.
	 * @return The robot with the new state after the movement.
	 */
	public Robot turnRight();
	
	/**
	 * <p>Turns the robot to the left when on a field.
	 * @return The robot with the new state after the movement.
	 */
	public Robot turnLeft();
	
}
