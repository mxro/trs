package de.mxro.trs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.TemporaryFolder;

/**
 * <p>
 * These tests assert that input can be read from files and all parts of the
 * application work together.
 * <p>
 * The test cases do not test a wide range of inputs and scenarios. This is done
 * in {@link TestSimulationEngine}, {@link TestPlacedRobot}, and
 * {@link TestRenderingEngine}
 *
 */
public class TestApp {

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	/**
	 * <p>
	 * Read a file which lets a robot go around in a circle.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test_run_file_1() throws IOException {

		File tempFile = testFolder.newFile("file1.txt");
		copyFromResourceToFile("/file1_round_and_round.txt", tempFile);

		App.main(new String[] { "", tempFile.getAbsolutePath() });

		Assert.assertEquals("1,1,EAST\n", systemOutRule.getLog());
	}

	/**
	 * <p>
	 * Read a file which lets a robot run against the edge of the table.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test_run_file_2() throws IOException {

		File tempFile = testFolder.newFile("file1.txt");
		copyFromResourceToFile("/file2_the_wall.txt", tempFile);

		App.main(new String[] { "", tempFile.getAbsolutePath() });

		Assert.assertEquals("5,4,EAST\n", systemOutRule.getLog());
	}

	/**
	 * <p>
	 * Copies the content of a Java resource to a local temporary file.
	 * <p>
	 * We don't work with the 'file' in the resources folder directly since,
	 * depending on how this application is run we may not have access to the
	 * resources as file.
	 * 
	 * @param resourcePath
	 * @param targetFile
	 * @throws IOException
	 */
	private final static void copyFromResourceToFile(String resourcePath, File targetFile) throws IOException {
		InputStream inputStream = TestSimulationEngine.class.getResourceAsStream(resourcePath);

		Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

}
