package com.raphabarzotto.agro_api.controller.dto;

import com.raphabarzotto.agro_api.entity.Farm;

/**
 * The type Farm creation dto.
 */
public record FarmCreationDto(String name, Double size) {

  public Farm toEntity() {
    return new Farm(name, size);
  }
}
