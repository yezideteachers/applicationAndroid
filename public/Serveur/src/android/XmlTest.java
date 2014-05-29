package android;

import static org.junit.Assert.*;

import java.util.List;

import org.jdom2.*;
import org.junit.Test;

public class XmlTest {
	Xml file = new Xml();

	@Test
	public void testRead() {
		List<Element> epr = file.read(
				"parcourTipe.xml" );
		assertEquals(1,epr.size());
	}

	@Test
	public void testNex() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCoordoner() {
		assertEquals("43.7159297 # 7.2638325",file.getCoordoner());
	}

}
