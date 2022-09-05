package br.com.ondetemvagas.webapp.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckDtoTest {

  @Test
  void getStringPropsTest() {
    CheckDto checkDto = CheckDto.builder()
        .message("OK")
        .release("dev")
        .build();

    Assertions.assertEquals("OK", checkDto.getMessage());
    Assertions.assertEquals("dev", checkDto.getRelease());
  }
}
