package com.raphabarzotto.agro_api.controller;

import com.raphabarzotto.agro_api.controller.dto.CropDto;
import com.raphabarzotto.agro_api.controller.dto.FertilizerDto;
import com.raphabarzotto.agro_api.entity.Crop;
import com.raphabarzotto.agro_api.exception.CropNotFoundException;
import com.raphabarzotto.agro_api.exception.FertilizerNotFoundException;
import com.raphabarzotto.agro_api.service.CropService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Crop controller.
 */
@RestController
@RequestMapping(value = "/crops")
public class CropController {
  private final CropService cropService;

  @Autowired
  public CropController(CropService cropService) {
    this.cropService = cropService;
  }

  /**
   * Gets all crops.
   *
   * @return the all crops
   */
  @GetMapping
  @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
  @ResponseStatus(HttpStatus.OK)
  public List<CropDto> getAllCrops() {
    List<Crop> allCrops = cropService.findAll();

    return allCrops.stream()
        .map(CropDto::fromEntity)
        .toList();
  }

  /**
   * Gets all crops.
   *
   * @return the all crops
   */
  @GetMapping("/search")
  @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
  @ResponseStatus(HttpStatus.OK)
  public List<CropDto> getAllCrops(
      @RequestParam(required = false) String start,
      @RequestParam(required = false) String end
  ) {
    List<Crop> allCrops;

    if (start != null && end != null) {
      LocalDate startDate = LocalDate.parse(start);
      LocalDate endDate = LocalDate.parse(end);
      allCrops = cropService.findAllByDateRange(startDate, endDate);
    } else {
      allCrops = cropService.findAll();
    }

    return allCrops.stream()
        .map(CropDto::fromEntity)
        .toList();
  }

  /**
   * Gets crop by id.
   *
   * @param id the id
   * @return the crop by id
   * @throws CropNotFoundException the crop not found exception
   */
  @GetMapping("/{id}")
  @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
  @ResponseStatus(HttpStatus.OK)
  public CropDto getCropById(@PathVariable Long id) throws CropNotFoundException {
    return CropDto.fromEntity(
        cropService.cropById(id)
    );
  }

  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  @ResponseStatus(HttpStatus.CREATED)
  public String addCropFertilizer(
      @PathVariable Long cropId,
      @PathVariable Long fertilizerId
  ) throws CropNotFoundException, FertilizerNotFoundException {
    return cropService.addCropFertilizer(cropId, fertilizerId);
  }

  /**
   * Gets fertilizers by crop id.
   *
   * @param id the id
   * @return the fertilizers by crop id
   * @throws CropNotFoundException the crop not found exception
   */
  @GetMapping("/{id}/fertilizers")
  @Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
  @ResponseStatus(HttpStatus.OK)
  public List<FertilizerDto> getFertilizersByCropId(
      @PathVariable Long id
  ) throws CropNotFoundException {
    return cropService.findFertilizersByCropId(id).stream()
        .map(FertilizerDto::fromEntity)
        .toList();
  }
}
