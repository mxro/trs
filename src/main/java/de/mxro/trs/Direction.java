package de.mxro.trs;

/**
 * <p>
 * The direction that the toy robot is facing.
 * <p>
 * For the user of the API angles are expressed in degrees keeping with the
 * language used in the specification.
 *
 */
public enum Direction {
	EAST(0), NORTH(90), WEST(180), SOUTH(270);

	/**
	 * <p>
	 * The direction expressed as a 360 degree angle within a circle.
	 */
	public final int angle;

	/**
	 * @return The delta value to be applied to the robots x position if moving in
	 *         this direction.
	 */
	public final int getDeltaX() {
		return calculateDeltaX(angle);
	}

	/**
	 * @return The delta value to be applied to the robots y position if moving in
	 *         this direction.
	 */
	public final int getDeltaY() {
		return calculateDeltaY(angle);
	}

	/**
	 * <p>
	 * Creates a new {@link Direction} object from a given angle.
	 * 
	 * @param angle
	 *            The angle that this direction corresponds to.
	 * @return A new {@link Direction} object
	 */
	public static Direction fromAngle(int angle) {
		int deltaX = calculateDeltaX(angle);
		int deltaY = calculateDeltaY(angle);
		for (Direction d : Direction.values()) {

			if (d.getDeltaX() == deltaX && d.getDeltaY() == deltaY) {
				return d;
			}
		}
		throw new IllegalArgumentException("Invalid angle: " + angle + ". Please use a right or straight angle.");
	}

	/**
	 * <p>
	 * Creates a new {@link Direction} object from a textual representation of a
	 * direction.
	 * 
	 * <p>
	 * The following inputs are supported:
	 * 
	 * <pre>
	 EAST
	 WEST
	 NORTH
	 SOUTH
	 * </pre>
	 * 
	 * @param text
	 * @return
	 */
	public static Direction fromText(String text) {
		for (Direction d : Direction.values()) {
			if (d.toString().equals(text)) {
				return d;
			}
		}

		throw new ParseException("Invalid text to describe direction: " + text);
	}

	Direction(int angle) {
		assert angle % 90 == 0;

		this.angle = angle;
	}

	/**
	 * <P>
	 * Used to calculate a movement delta from an angle for horizontal movements.
	 * 
	 * @param angle
	 *            The angle for which the delta is to be calculated for.
	 * @return The delta for a horizontal movement.
	 */
	private final static int calculateDeltaX(int angle) {
		return Math.toIntExact(Math.round(Math.cos(Math.toRadians(angle))));
	}

	/**
	 * <p>
	 * Used to calculate a movement delta from an angle for vertical movements.
	 * 
	 * @param angle
	 *            The angle for which the delta is to be calculated for.
	 * @return The delta for a vertical movement.
	 */
	private final static int calculateDeltaY(int angle) {
		return Math.toIntExact(Math.round(Math.sin(Math.toRadians(angle))));
	}

}
