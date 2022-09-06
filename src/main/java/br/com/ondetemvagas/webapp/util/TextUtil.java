package br.com.ondetemvagas.webapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.NonNull;
import org.springframework.util.StringUtils;

/** This class holds utilities for text handling. */
public class TextUtil {

  private static final String IGNORE_LIST = "de, e, em";

  private static final Map<String, String> replacer =
      Map.of(
          "cnc", "CNC",
          "i", "I",
          "ii", "II",
          "rh", "RH",
          "ti", "TI",
          "web", "Web");

  /**
   * Check if a given word should be ignored.
   *
   * @param word the word to be checked
   * @return true if it should be ignored, false otherwise
   */
  public static boolean isIgnore(String word) {
    return IGNORE_LIST.contains(word);
  }

  /**
   * Replace a given word for another equivalent.
   *
   * @param word the word to be replaced
   * @return a capitalized string that replaces the original one
   */
  public static String fixWord(String word) {
    return replacer.getOrDefault(word, word);
  }

  /**
   * Breaks a job name into words and capitalize each word.
   *
   * @param text the job name to be parsed
   * @return a string containing all capitalized words
   */
  public static String parseJobName(@NonNull String text) {
    List<String> words = List.of(text.split(" "));
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      if (word.isBlank()) {
        continue;
      }
      if (!sb.toString().isEmpty()) {
        sb.append(" ");
      }

      sb.append(capitalize(word));
    }

    return sb.toString();
  }

  /**
   * Capitalize a given piece of text.
   *
   * @param text the text to be capitalized
   * @return a string capitalized
   */
  public static String capitalize(String text) {
    if (isIgnore(text)) {
      return text;
    }

    return StringUtils.capitalize(text);
  }

  /**
   * Replace special characters by plain text.
   *
   * @param text the word to be replaced
   * @return an equivalent string in plain text
   */
  public static String replaceToPlainText(String text) {
    if (Objects.isNull(text)) {
      return null;
    }

    Map<Character, Character> charMap = new HashMap<>();
    charMap.put('á', 'a');
    charMap.put('é', 'e');
    charMap.put('í', 'i');
    charMap.put('ó', 'o');
    charMap.put('ú', 'u');
    charMap.put('ç', 'c');
    charMap.put('ã', 'a');
    charMap.put('â', 'a');
    charMap.put('ô', 'o');
    charMap.put('Á', 'A');
    charMap.put('É', 'E');
    charMap.put('Í', 'I');
    charMap.put('Ó', 'O');
    charMap.put('Ú', 'U');
    charMap.put('Ç', 'C');
    charMap.put('Ã', 'A');
    charMap.put('Â', 'A');
    charMap.put('Ô', 'O');

    StringBuilder sb = new StringBuilder();
    for (Character charFor : text.toCharArray()) {
      Character newChar = charMap.getOrDefault(charFor, charFor);
      sb.append(newChar);
    }

    return sb.toString();
  }
}
