package com.raphabarzotto.agro_api.controller.dto;

import com.raphabarzotto.agro_api.entity.Fertilizer;

/**
 * The type Fertilizer dto.
 */
public record FertilizerDto(Long id, String name, String brand, String composition) {

  /**
   * From entity fertilizer dto.
   *
   * @param fertilizer the fertilizer
   * @return the fertilizer dto
   */
  public static FertilizerDto fromEntity(Fertilizer fertilizer) {
    return new FertilizerDto(
        fertilizer.getId(),
        fertilizer.getName(),
        fertilizer.getBrand(),
        fertilizer.getComposition()
    );
  }
}
