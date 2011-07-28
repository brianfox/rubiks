package com.seefoxrun.options;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OptionManagerTest {

	File f;
	OptionManager o1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		f = File.createTempFile("OMT", null);
		o1 = new OptionManager();
		o1.addOption(null, "IntMin",  new OptionInt(Integer.MIN_VALUE));
		o1.addOption(null, "IntMax",  new OptionInt(Integer.MAX_VALUE));
		o1.addOption(null, "IntZero", new OptionInt(0));
		o1.addOption(null, "FloatMin",  new OptionFloat(Float.MIN_VALUE));
		o1.addOption(null, "FloatMax",  new OptionFloat(Float.MAX_VALUE));
		o1.addOption(null, "FloatZero", new OptionFloat(0F));
		o1.addOption(null, "BooleanTrue",   new OptionBoolean(Boolean.TRUE));
		o1.addOption(null, "BooleanFalse",  new OptionBoolean(Boolean.FALSE));
		o1.addOption(null, "StringTest", new OptionString("Test String"));
		o1.addOption(null, "StringNull", new OptionString(null));
		o1.save(f);
	}

	@After
	public void tearDown() throws Exception {
		f.delete();
	}
	
	@Test
	public void testOptionManager() {
		fail("Not yet implemented");
	}

	@Test
	public void testOptionManagerString() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddOption() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoad() {
		fail("Not yet implemented");
	}

	@Test
	public void testFileRoundTrip() {
		try {
			OptionManager o2 = new OptionManager();
			o2.load(f);
			assertTrue(o1.compareTo(o2) == 0);
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (OptionException e) {
			fail(e.getMessage());
		}
	}

}
