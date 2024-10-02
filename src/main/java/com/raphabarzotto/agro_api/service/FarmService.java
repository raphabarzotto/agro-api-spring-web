package com.raphabarzotto.agro_api.service;

import com.raphabarzotto.agro_api.entity.Crop;
import com.raphabarzotto.agro_api.entity.Farm;
import com.raphabarzotto.agro_api.exception.FarmNotFoundException;
import com.raphabarzotto.agro_api.repository.CropRepository;
import com.raphabarzotto.agro_api.repository.FarmRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Farm service.
 */
@Service
public class FarmService {
  private final FarmRepository farmRepository;
  private final CropRepository cropRepository;

  @Autowired
  public FarmService(
      FarmRepository farmRepository,
      CropRepository cropRepository
  )  {
    this.farmRepository = farmRepository;
    this.cropRepository = cropRepository;
  }

  public Farm create(Farm farm) {
    return farmRepository.save(farm);
  }

  public Farm farmById(Long id) throws FarmNotFoundException {
    return farmRepository.findById(id)
        .orElseThrow(FarmNotFoundException::new);
  }

  public List<Farm> findAll() {
    return farmRepository.findAll();
  }

  /**
   * Create crop crop.
   *
   * @param id   the id
   * @param crop the crop
   * @return the crop
   * @throws FarmNotFoundException the farm not found exception
   */
  public Crop createCrop(Long id, Crop crop) throws FarmNotFoundException {
    Farm farm = farmById(id);

    crop.setFarm(farm);

    return cropRepository.save(crop);
  }

  public List<Crop> findAllCrops(Long id) throws FarmNotFoundException {
    Farm farm = farmById(id);
    return farm.getCrops();
  }
}
