/*
 * Copyright 2013 Dittmar Steiner
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dittmarsteiner.utils.test;

/**
 * @author <a href="mailto:dittmar.steiner@gmail.com">Dittmar Steiner</a>
 */
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.dittmarsteiner.util.Entities;

/**
 * @author dittmar
 *
 */
@SuppressWarnings("unused")
public class EntitiesTest extends TestCase {

	String xhtml = "<html><body>\"äöüÄÖÜß\u00AD\" & ''</body></html>";
	String escapedHtml = 
			"&lt;html&gt;&lt;body&gt;&quot;&auml;&ouml;&uuml;&Auml;&Ouml;&Uuml;&szlig;&shy;&quot; &amp; &apos;&apos;&lt;/body&gt;&lt;/html&gt;";
	String escapedXml = 
			"&lt;html&gt;&lt;body&gt;&quot;äöüÄÖÜß&#173;&quot; &amp; &apos;&apos;&lt;/body&gt;&lt;/html&gt;";
	String escapedAsciiXml =
			"&lt;html&gt;&lt;body&gt;&quot;&#228;&#246;&#252;&#196;&#214;&#220;&#223;&#173;&quot; &amp; &apos;&apos;&lt;/body&gt;&lt;/html&gt;";
	
	public void testEncodeHtmlString() {
		String result = Entities.encodeHtml(xhtml);
		System.out.println("source  : " + xhtml);
		System.out.println("expected: " + escapedHtml);
		System.out.println("result  : " + result);
		System.out.println();
		assertEscapedXml(result);
		assertEquals(escapedHtml, result);
	}
	
	public void testEncodeXmlString() {
		String result = Entities.encodeXml(xhtml);
		System.out.println("source  : " + xhtml);
		System.out.println("expected: " + escapedXml);
		System.out.println("result  : " + result);
		System.out.println();
		assertEscapedXml(result);
		assertEquals(escapedXml, result);
	}
	
	public void testToAsciiXmlString() {
		String result = Entities.encodeAsciiXml(xhtml);
		System.out.println("source  : " + xhtml);
		System.out.println("expected: " + escapedAsciiXml);
		System.out.println("result  : " + result);
		System.out.println();
		assertEscapedXml(result);
		assertEquals(escapedAsciiXml, result);
	}
	
	public void testDecodeHtml() {
		String ml = Entities.decode(escapedHtml);
		assertEquals(xhtml, ml);
	}
	
	public void testDecodeXml() {
		String ml = Entities.decode(escapedXml);
		assertEquals(xhtml, ml);
	}
	
	public void testDecodeAsciiXml() {
		String ml = Entities.decode(escapedAsciiXml);
		assertEquals(xhtml, ml);
	}
	
	public void testDecodeNothing() {
		String ml = Entities.decode("");
		assertEquals("", ml);
	}
	
	public void testDecodeNone() {
		String str = "abcABC";
		String ml = Entities.decode(str);
		assertEquals(str, ml);
		assertTrue(str == ml);
	}
	
	public void testDecodeAuml() {
		String markup = "&#x00E4;";
		String str = Entities.decode(markup);
		assertEquals("ä", str);
	}
	
	public void testDecodeNotAnEntity() {
		String markup = "&#x00\fe4;"; //  with \f
		String str = Entities.decode(markup);
		assertFalse("ä".equals(str));
		assertEquals(markup, str);
	}
	
	public void testDecodeNotAHexEntity() {
		String markup = "&#00e4;"; // missing x after #
		String str = Entities.decode(markup);
		assertFalse("ä".equals(str));
		assertEquals(markup, str);
	}
	
	public void testDecodeTooLargeHexEntity() {
		String markup = "&#x0000000e4;"; // too long
		String str = Entities.decode(markup);
		assertFalse("ä".equals(str));
		assertEquals(markup, str);
	}
	
	public void testDecodeLargestHexEntity() {
		String markup = "&#x000e4;";
		String str = Entities.decode(markup);
		assertEquals("ä", str);
	}
	
	public void testDecodeLargestNamedEntity() {
		String markup = "&thetasym;"; // allowed
		String str = Entities.decode(markup);
		assertEquals("ϑ", str);
		assertEquals(977, str.charAt(0));
	}
	
	public void testDecodeInvalids() {
		String ml = Entities.decode("&amp;&#;");
		assertEquals("&&#;", ml);
		
		ml = Entities.decode("&amp;&#");
		assertEquals("&&#", ml);
		
		ml = Entities.decode("&amp;&;");
		assertEquals("&&;", ml);
		
		ml = Entities.decode("&amp;&");
		assertEquals("&&", ml);
		
		ml = Entities.decode("&&;");
		assertEquals("&&;", ml);
		
		ml = Entities.decode("&;");
		assertEquals("&;", ml);
	}
	
	private void assertEscapedXml(String str) {
		// Predefined XML entities
		assertFalse(str.contains("<"));
		assertFalse(str.contains(">"));
		assertFalse(str.contains("\""));
		assertFalse(str.contains("'"));
		assertTrue(str.contains("&quot;"));
		assertTrue(str.contains("&amp;"));
		assertTrue(str.contains("&apos;"));
		assertTrue(str.contains("&lt;"));
		assertTrue(str.contains("&gt;"));
	}
}
