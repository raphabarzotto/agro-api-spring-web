package com.raphabarzotto.agro_api.controller;

import com.raphabarzotto.agro_api.controller.dto.CropCreationDto;
import com.raphabarzotto.agro_api.controller.dto.CropDto;
import com.raphabarzotto.agro_api.controller.dto.FarmCreationDto;
import com.raphabarzotto.agro_api.controller.dto.FarmDto;
import com.raphabarzotto.agro_api.entity.Farm;
import com.raphabarzotto.agro_api.exception.FarmNotFoundException;
import com.raphabarzotto.agro_api.service.FarmService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Farm controller.
 */
@RestController
@RequestMapping(value = "/farms")
public class FarmController {
  private final FarmService farmService;

  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  /**
   * Create farm farm dto.
   *
   * @param farmCreationDto the farm creation dto
   * @return the farm dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FarmDto createFarm(@RequestBody FarmCreationDto farmCreationDto) {
    return FarmDto.fromEntity(
        farmService.create(farmCreationDto.toEntity())
    );
  }

  /**
   * Gets farm by id.
   *
   * @param id the id
   * @return the farm by id
   * @throws FarmNotFoundException the farm not found exception
   */
  @GetMapping("/{id}")
  @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN"})
  @ResponseStatus(HttpStatus.OK)
  public FarmDto getFarmById(@PathVariable Long id) throws FarmNotFoundException {
    return FarmDto.fromEntity(
        farmService.farmById(id)
    );
  }

  /**
   * Gets all farms.
   *
   * @return the all farms
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN"})
  public List<FarmDto> getAllFarms() {
    List<Farm> allFarms = farmService.findAll();

    return allFarms.stream()
        .map(FarmDto::fromEntity)
        .toList();
  }

  /**
   * Create crop crop dto.
   *
   * @param id              the id
   * @param cropCreationDto the crop creation dto
   * @return the crop dto
   * @throws FarmNotFoundException the farm not found exception
   */
  @PostMapping("/{id}/crops")
  @ResponseStatus(HttpStatus.CREATED)
  public CropDto createCrop(@PathVariable Long id, @RequestBody CropCreationDto cropCreationDto)
      throws FarmNotFoundException {
    return CropDto.fromEntity(
        farmService.createCrop(id, cropCreationDto.toEntity())
    );
  }

  /**
   * Find all crops list.
   *
   * @param id the id
   * @return the list
   * @throws FarmNotFoundException the farm not found exception
   */
  @GetMapping("/{id}/crops")
  @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN"})
  @ResponseStatus(HttpStatus.OK)
  public List<CropDto> findAllCrops(@PathVariable Long id) throws FarmNotFoundException {
    return farmService.farmById(id).getCrops().stream()
        .map(CropDto::fromEntity)
        .toList();
  }
}
