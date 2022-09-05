package br.com.ondetemvagas.webapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;

public class TextUtil {

  private static final String IGNORE_LIST = "de, e, em";

  private static final Map<String, String> replacer =
      Map.of(
          "cnc", "CNC",
          "i", "I",
          "ii", "II",
          "rh", "RH",
          "ti", "TI",
          "web", "WEB");

  public static boolean isIgnore(String word) {
    return IGNORE_LIST.contains(word);
  }

  public static String replace(String word) {
    return replacer.getOrDefault(word, capitalize(word));
  }

  public static String parseJobName(@NonNull String text) {
    List<String> words = List.of(text.split(" "));
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      if (!sb.toString().isEmpty()) {
        sb.append(" ");
      }

      sb.append(capitalize(word));
    }

    return sb.toString();
  }

  public static String capitalize(String text) {
    if (isIgnore(text)) {
      return text;
    }

    return replace(text);
  }

  public static String replaceToPlainText(String text) {
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
      Character newchar = charMap.get(charFor);
      if (newchar == null) {
        sb.append(charFor);
      } else {
        sb.append(newchar);
      }
    }

    return sb.toString();
  }
}
