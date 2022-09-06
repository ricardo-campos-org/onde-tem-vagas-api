package br.com.ondetemvagas.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** This class contains a message and a release. */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckDto {

  private String message;
  private String release;
}
