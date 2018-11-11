# trs

Exploration of a popular coding puzzle - be welcome to use for inspiration but DON'T COPY :)

Please find the instructions this implementation is based upon [here](https://joneaves.wordpress.com/2014/07/21/toy-robot-coding-test/#comment-792). I won't be repeating the instructions here but they are an excellent documentation artifact for exploring this implementation. Thus if you haven't read these instructions yet I highly recommend to do so.

In this documentation I will instead focus on a number of aspects of this implementation I consider desirable practices for developing software. Architecting and writing software is such a rich and challenging field and my recommendations should be by no means considered universal. They are further not the only or best way to go about implementing this coding puzzle - there are doubtless many excellent alternatives. However, I hope that the following practices and their application in this application provide some inspiration for completing this code challenge or other software development work. 

## Low Method Complexity

Keeping methods clean and simple the key premises from the famous book ['Clean Code' by 'Bob' Martin](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882). 

This implementation is designed to assure methods are as short as possible and stay within a maximum limit of 10-20 LOC - with most methods falling well below this limit. The following method is exemplary for most methods in this implementation:

```
	/**
	 * Move the robot to the direction it is facing.
	 */
	public Robot move() {
		return new PlacedRobot(x + direction.getDeltaX(), y + direction.getDeltaY(), direction);
	}
```

One major advantage of using short and simple methods is that they are easy to read and understand. They further help to increase modularity and decrease code duplication. One disadvantage of using shorter and less complex methods is that this approach usually requires to create more classes and apply design patterns such as the command pattern with the associated overheads. 

## Immutability

While functional programming has been around for many years, it has so far failed to enter the mainstream of everyday programming - at least in its purest forms such as in Haskell and Clojure. That notwithstanding, in the past 'object-oriented' languages have started to adopt more functional approaches within their established foundations. In Java, for instance, we have the Streams API since Java 8. I was very lucky to have had a lecturer who was - even more than 10 years ago when I took his copmuter science courses - very enthusiastic about trying to program in Java as if it was Haskell. Ever since, I have tried to adhere to one key principle of functional programming in all my development work: to achieve immutability whenever possible.

The implementations for the two key interfaces of this implementation, <code>Robot</code> and <code>Command</code> are immutable. For instance, see the following excerpt from the <code>Robot</code> class. All it's fields are final which renders this class immutable. 

```
/**
 * The state of the robot when placed on the table.
 *
 */
public class PlacedRobot implements Robot {

	/**
	 * The x coordinate of the robot on the table.
	 */
	public final int x;

	/**
	 * The y coordinate of the robot on the table.
	 */
	public final int y;

	/**
	 * The direction the robot is facing.
	 */
	public final Direction direction;

```

One of advantage of immutablity is that it assures thread-safety - which, especially in complex systems, is often difficult to achieve. However immutability also introduces other subtle changes to an application - chiefly that one mutable state is often transformed into a sequence of immutable states. This often makes code clearer and easier to understand. However over the years I have also learned that immutability should not be a dogma. There are many cases in software development where a traditional, iterative and stateful approach leads to easier solutions. Thus a number of algorithms in this implementation are iterative such as the one for running the simulation in <code>SimulationEngine</code>.

## Design Patterns

When I first read the book Design Patterns by the 'Gang of Four' Erich Gamma et al., I was immediately captivated. I thought and still think that these are an amazing way to think about software and teaching better software development practices. Granted, I do not remember every single one of these patterns but I believe to have internalised some of these patterns over the years, such as Composite and Strategy.

This implementation uses various Design Patterns. For instance, the parsing is implemented using the Strategy design pattern - allowing to easily switch the implementation used for parsing later (given the current one is very simple). The Command Design Pattern is used to encapsulate the various commands that can be issued for the toy robot.

```
Command = MoveCommand | PlaceCommand | ReportCommand | TurnLeftCommand | TurnRightCommand
```

The key advantage to using Design Pattern is that they provide shortcuts to finding simple solutions to complex problems. However, like any template, not every place they can be applied is a good place to apply them. Thus the art lies in finding the right design pattern for the problem at hand. 

## Testability

Writing automated tests for software has become so self evident that it barely warrants to be mentioned. However, I think when it comes to writing code which is _easy_ to test, there is still some way to go until we take full advantage for TDD. It's an unfortunate fact of life for any 'enterprise' Java developer that many tests are far too slow and require far too many resources to run. Often, the overhead required for testing a simple piece of code is very large and we are required to use mocks, stubs and other trickery to test code.

This implementation is architected in a way that the individual parts of the application can easily be tested in isolation. For instance, the engine for running the simulation (<code>SimulationEngine</code>) can easily be tested without the components for parsing robot commands and rendering outputs:

```
@Test
public void test_supplied_case_a() {
	List<Command> commands = Arrays.asList(new PlaceCommand(0, 0, Direction.NORTH), new MoveCommand(),
			new ReportCommand());
	List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

	assertLastResult(new PlacedRobot(0, 1, Direction.NORTH), results);
}
```

Likewise the parsing engine can be tested in isolation without need for the simulation engine:

```
@Test
public void test_move_command() {
	Assert.assertEquals(Arrays.asList(new MoveCommand()), getParser().parse("MOVE"));
	Assert.assertEquals(Arrays.asList(new MoveCommand()), getParser().parse("MOVE\n"));
}
```

## Information Hiding

One of the worst enemies of designers of large scale software systems is dependency hell, especially when different components are not linked in a strictly hierarchical fashion (dependency between parent and child modules) but in a wild west, spaghetti way in which seemingly every module can be linked with any other module. I believe the only clear way to prevent this is embrace information hiding and encapsulation wherever possible. 

While this project is still based on Java 8, the classes are already arranged in a way which will make it easy to enforce encapsulation on a package level - as provided by OSGi and the Java Module system. Classes are, were possible, organised in packages marked as `internal` - to which users of this code will not required access. For instance, the <code>RegExRobotCommandParser</code> lives in such an internal package and users of the parser and/or simulation do not need to link to this class directly.

The advantages of encapsulation are many, for instances making it easier for users of a component to understand how to use the component (since irrelevant information is hidden form them) but also preventing other components from linking to some classes, which simplifies the network of dependencies. The disadvantage, as with so many other good practices, is that there is slightly more work involved in building components with good encapsulation. There is also less flexibility in using the component of course - I have for instance received many a request for my open source projects to make an attribute or method protected or public which was originally conceived to be beautifully private.

## Build Automation

Apart from being a developer, I have also worked for a few years as a DevOps engineer - so build (and infrastructure) automation is something close to my heart.  
  