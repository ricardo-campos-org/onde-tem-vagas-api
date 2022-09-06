package br.com.ondetemvagas.webapp.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TextUtilTest {

  @Test
  void isIgnoreTest() {
    assertTrue(TextUtil.isIgnore("de"));
    assertTrue(TextUtil.isIgnore("e"));
    assertTrue(TextUtil.isIgnore("em"));
  }

  @Test
  void replaceTest() {
    assertEquals("CNC", TextUtil.fixWord("cnc"));
    assertEquals("I", TextUtil.fixWord("i"));
    assertEquals("II", TextUtil.fixWord("ii"));
    assertEquals("TI", TextUtil.fixWord("ti"));
    assertEquals("RH", TextUtil.fixWord("rh"));
    assertEquals("Web", TextUtil.fixWord("web"));
  }

  @Test
  void parseJobNameTest() {
    assertEquals("Dev Frontend", TextUtil.parseJobName("Dev  frontend"));
    assertEquals("Personal Assistant", TextUtil.parseJobName("personal assistant"));
    assertEquals("Professional de Support", TextUtil.parseJobName("professional de support"));
  }

  @Test
  void capitalizeTest() {
    assertEquals("Frontend", TextUtil.parseJobName("frontend"));
    assertEquals("e", TextUtil.parseJobName("e"));
    assertEquals("de", TextUtil.parseJobName("de"));
    assertEquals("em", TextUtil.parseJobName("em"));
  }

  @Test
  void replaceToPlainTextTest() {
    assertEquals("a", TextUtil.replaceToPlainText("á"));
    assertEquals("e", TextUtil.replaceToPlainText("é"));
    assertEquals("i", TextUtil.replaceToPlainText("í"));
    assertEquals("o", TextUtil.replaceToPlainText("ó"));
    assertEquals("u", TextUtil.replaceToPlainText("ú"));
    assertEquals("c", TextUtil.replaceToPlainText("ç"));
    assertEquals("a", TextUtil.replaceToPlainText("ã"));
    assertEquals("a", TextUtil.replaceToPlainText("â"));
    assertEquals("o", TextUtil.replaceToPlainText("ô"));
    assertEquals("A", TextUtil.replaceToPlainText("Á"));
    assertEquals("E", TextUtil.replaceToPlainText("É"));
    assertEquals("I", TextUtil.replaceToPlainText("Í"));
    assertEquals("O", TextUtil.replaceToPlainText("Ó"));
    assertEquals("U", TextUtil.replaceToPlainText("Ú"));
    assertEquals("C", TextUtil.replaceToPlainText("Ç"));
    assertEquals("A", TextUtil.replaceToPlainText("Ã"));
    assertEquals("A", TextUtil.replaceToPlainText("Â"));
    assertEquals("O", TextUtil.replaceToPlainText("Ô"));
    assertEquals("R", TextUtil.replaceToPlainText("R"));
    assertNull(TextUtil.replaceToPlainText(null));
  }
}
