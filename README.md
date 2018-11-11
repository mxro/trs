[![Build Status](https://travis-ci.org/mxro/trs.svg?branch=master)](https://travis-ci.org/mxro/trs)

# trs

Exploration of a popular coding puzzle - be welcome to use for inspiration but DON'T COPY :)

Please find the instructions this implementation is based upon [here](https://joneaves.wordpress.com/2014/07/21/toy-robot-coding-test/#comment-792). I won't be repeating the instructions here but they are an excellent documentation artifact for exploring this implementation. Thus if you haven't read these instructions yet I highly recommend to do so.

This readme is split into three parts:

- [Software Development Practices](#software-development-practices): Which provides an overview of the practices followed in developing this implementation.
- [Usage](#usage): Which provides information how to use this application.
- [Development](#development): Which provides information on how to check out and continue developing this application.

## Software Development Practices

In the following sections I will highlight some practices followed in developing this implementation of the toy robot scenario. For each practice, I will first briefly portray it, then give an example how it was used in the implementation and lastly mention some advantages and disadvantages of the practice.

I would like to note that architecting and writing software is such a rich and challenging field and the suggested practices should be by no means considered universal. They are further not the only or best way to go about implementing this coding puzzle - there are doubtless many excellent alternatives. However, I hope that the following practices and their application in this application provide some inspiration for completing this code challenge or other software development work.

### Low Method Complexity

Keeping methods clean and simple is one of the key premises from the famous book ['Clean Code' by 'Bob' Martin](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882). 

This implementation was designed to assure methods are as short as possible and stay within a maximum limit of 10-20 LOC - with most methods falling well below this limit. The following [method](https://mxro.github.io/trs/xref/de/mxro/trs/internal/PlacedRobot.html#L30) is exemplary for most methods in this implementation:

```java
/**
 * Move the robot to the direction it is facing.
 */
public Robot move() {
	return new PlacedRobot(x + direction.getDeltaX(), y + direction.getDeltaY(), direction);
}
```

One major advantage of using short and simple methods is that they are easy to read and understand. They further help to increase modularity and decrease code duplication. One disadvantage of using shorter and less complex methods is that this approach usually requires to create more classes and apply design patterns such as the command pattern with the associated overheads. 

### Immutability

While functional programming has been around for many years, it has so far failed to enter the mainstream of everyday programming - at least in its purest forms such as in Haskell and Clojure. That notwithstanding, in the past 'object-oriented' languages have started to adopt more functional approaches within their established foundations. In Java, for instance, we have the Streams API since Java 8. I was very lucky to have had a lecturer who was - even more than 10 years ago when I took his computer science courses - very enthusiastic about trying to program in Java as if it was Haskell. Ever since, I have tried to adhere to one key principle of functional programming in all my development work: to achieve immutability whenever possible.

The implementations for the two key interfaces of this implementation, [Robot](https://mxro.github.io/trs/apidocs/de/mxro/trs/Robot.html) and [Command](https://mxro.github.io/trs/apidocs/de/mxro/trs/Command.html) are immutable. For instance, see the following excerpt from the [PlacedRobot](https://mxro.github.io/trs/xref/de/mxro/trs/internal/PlacedRobot.html) class. All it's fields are final which renders this class immutable. 

```java
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

One of advantage of immutablity is that it assures thread-safety - which, especially in complex systems, is often difficult to achieve. However immutability also introduces other subtle changes to an application - chiefly that one mutable state is often transformed into a sequence of immutable states. This often makes code clearer and easier to understand. However over the years I have also learned that immutability should not be a dogma. There are many cases in software development where a traditional, iterative and stateful approach leads to easier solutions. Thus a number of algorithms in this implementation are iterative such as the one for running the simulation in [SimulationEngine](https://mxro.github.io/trs/xref/de/mxro/trs/engine/SimulationEngine.html).

### Design Patterns

When I first read the book Design Patterns by the 'Gang of Four' Erich Gamma et al., I was immediately captivated. I thought and still think that these are an amazing way to think about software and teaching better software development practices. Granted, I do not remember every single one of these patterns but I believe to have internalised some of these patterns over the years, such as Composite and Strategy.

This implementation uses various Design Patterns. For instance, the parsing is implemented using the Strategy design pattern - allowing to easily switch the implementation used for parsing later (given the current one is very simple). The Command Design Pattern is used to encapsulate the various commands that can be issued for the toy robot.

```
Command = MoveCommand | PlaceCommand | ReportCommand | TurnLeftCommand | TurnRightCommand
```

The key advantage to using Design Pattern is that they provide shortcuts to finding simple solutions to complex problems. However, like any template, not every place they can be applied is a good place to apply them. Thus the art lies in finding the right design pattern for the problem at hand. 

### Testability

Writing automated tests for software has become so self evident that it barely warrants to be mentioned. However, I think when it comes to writing code which is _easy_ to test, there is still some way to go until we take full advantage for TDD. It's an unfortunate fact of life for any 'enterprise' Java developer that many tests are far too slow and require far too many resources to run. Often, the overhead required for testing a simple piece of code is very large and we are required to use mocks, stubs and other trickery to test code.

This implementation is architected in a way that the individual parts of the application can easily be tested in isolation. For instance, the engine for running the simulation (<code>SimulationEngine</code>) can easily be tested without the components for parsing robot commands and rendering outputs:

```java
@Test
public void test_supplied_case_a() {
	List<Command> commands = Arrays.asList(new PlaceCommand(0, 0, Direction.NORTH), new MoveCommand(),
			new ReportCommand());
	List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

	assertLastResult(new PlacedRobot(0, 1, Direction.NORTH), results);
}
```

Likewise the parsing engine can be tested in isolation without need for the simulation engine:

```java
@Test
public void test_move_command() {
	Assert.assertEquals(Arrays.asList(new MoveCommand()), getParser().parse("MOVE"));
	Assert.assertEquals(Arrays.asList(new MoveCommand()), getParser().parse("MOVE\n"));
}
```

### Information Hiding

One of the worst enemies of designers of large scale software systems is dependency hell, especially when different components are not linked in a strictly hierarchical fashion (dependency between parent and child modules) but in a wild west, spaghetti way in which seemingly every module can be linked with any other module. I believe the only clear way to prevent this is embrace information hiding and encapsulation wherever possible. 

While this project is still based on Java 8, the classes are already arranged in a way which will make it easy to enforce encapsulation on a package level - as provided by OSGi and the Java Module system. Classes are, were possible, organised in packages marked as `internal` - to which users of this code will not required access. For instance, the <code>RegExRobotCommandParser</code> lives in such an internal package and users of the parser and/or simulation do not need to link to this class directly.

The advantages of encapsulation are many, for instances making it easier for users of a component to understand how to use the component (since irrelevant information is hidden form them) but also preventing other components from linking to some classes, which simplifies the network of dependencies. The disadvantage, as with so many other good practices, is that there is slightly more work involved in building components with good encapsulation. There is also less flexibility in using the component of course - I have for instance received many a request for my open source projects to make an attribute or method protected or public which was originally conceived to be beautifully private.

### Build Automation

Apart from being a developer, I have also worked for a few years as a DevOps engineer - so build (and infrastructure) automation is something close to my heart. Software systems nowadays are so complex that assuring quality manually is an almost impossible tasks. Thankfully there are many automated tools which can help us with assuring quality without the need to lift a finger. My favorite book on this topic is [Java Power Tools by John Smart](http://shop.oreilly.com/product/9780596527938.do) - which was written a bit before the advent of CI/CD but still captures the spirit of making better software with less work.

For this project, a CI pipeline is set up using Maven and travis-ci.org, which offers the following:

- Runs [all unit tests](https://mxro.github.io/trs/surefire-report.html)
- Runs [Findbugs](https://mxro.github.io/trs/findbugs.html)
- Runs [Checkstyle](https://mxro.github.io/trs/checkstyle.html) (oh, looks like there are 335 errors for me to fix)
- Runs [Cobertura](https://mxro.github.io/trs/cobertura/index.html) to determine test coverage

The best things about these reports are that they took no more than five minutes to set up - yet provide a great insights into the code quality. The disadvantage of automated quality checks is that they sometimes can create unnecessary additional work - low quality unit and integration tests are frequent offenders. 

## Usage

If you would like to use this simulation, please find some information in the following.

### Requirements

- JRE 1.8+

### Command Line

This project is packaged as a 'fat' jar which can be download from the [releases page of this project](https://github.com/mxro/trs/releases). After downloading this JAR, simple call it as follows:

```
java -jar toy-robot-simulator-0.0.1-SNAPSHOT-distribution.jar [filename]
```

For instance by supplying an input file such as [file1_round_and_round.txt]():

```
java -jar toy-robot-simulator-0.0.1-SNAPSHOT-distribution.jar [file1_round_and_round.txt]
```

This will result in an output such as the following:

```
1,1,EAST
```

### API

The API can best be explored by browsing the JavaDoc for this project. A good place to start are the following classes:

- [RobotCommandParsers](https://mxro.github.io/trs/apidocs/de/mxro/trs/parser/RobotCommandParsers.html)
- [SimulationEngine](https://mxro.github.io/trs/apidocs/de/mxro/trs/engine/SimulationEngine.html)
- [RenderingEngine](https://mxro.github.io/trs/apidocs/de/mxro/trs/engine/RenderingEngine.html)

For instance, the following example loads a small text and then renders the results of the simulation:

```java
List<Command> commands =  RobotCommandParsers.regex().parse("PLACE 0,0,NORTH\n" + "MOVE\n" + "REPORT");
List<Robot> results = SimulationEngine.run(commands, Arrays.asList(Validators.onTable(5, 5)));

System.out.println(RenderingEngine.renderReport(commands, results));
```


## Development

Thanks to the magic of Java and Maven it's quite easy to get started with developing this project. Please find the requirements and steps below. 

### Requirements

- JRE 1.8+
- Maven 3.10+
- Git 1.8+

### Build

To build this project, simple check out from git and then run:

```
mvn install
```

To generate the project reports and Maven site:

```
mvn site
```

The reports and site will be available in the project directory `target/site`. Open `index.html` in this directory as a good starting point to explore the reports.

Note: Do not deploy the `-distribution.jar` or `.war` file after `mvn site` has run. `mvn site` will add instrumentation to the generated classes. Instead, run `mvn install` before deploying files.
  