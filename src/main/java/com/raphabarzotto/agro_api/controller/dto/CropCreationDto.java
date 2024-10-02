package com.raphabarzotto.agro_api.controller.dto;

import com.raphabarzotto.agro_api.entity.Crop;
import java.time.LocalDate;

/**
 * The type Crop creation dto.
 */
public record CropCreationDto(
    String name,
    double plantedArea,
    LocalDate plantedDate,
    LocalDate harvestDate
) {
  public Crop toEntity() {
    return new Crop(name, plantedArea, plantedDate, harvestDate);
  }
}
