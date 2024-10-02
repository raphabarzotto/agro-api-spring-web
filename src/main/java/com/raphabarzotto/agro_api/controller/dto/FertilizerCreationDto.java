package com.raphabarzotto.agro_api.controller.dto;

import com.raphabarzotto.agro_api.entity.Fertilizer;

/**
 * The type Fertilizer creation dto.
 */
public record FertilizerCreationDto(String name, String brand, String composition) {

  /**
   * To entity fertilizer.
   *
   * @return the fertilizer
   */
  public Fertilizer toEntity() {
    return new Fertilizer(name, brand, composition);
  }
}
