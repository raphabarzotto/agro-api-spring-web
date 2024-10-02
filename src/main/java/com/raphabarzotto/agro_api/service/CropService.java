package com.raphabarzotto.agro_api.service;

import com.raphabarzotto.agro_api.entity.Crop;
import com.raphabarzotto.agro_api.entity.Fertilizer;
import com.raphabarzotto.agro_api.exception.CropNotFoundException;
import com.raphabarzotto.agro_api.exception.FertilizerNotFoundException;
import com.raphabarzotto.agro_api.repository.CropRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Crop service.
 */
@Service
public class CropService {
  private final CropRepository cropRepository;

  private final FertilizerService fertilizerService;

  @Autowired
  public CropService(CropRepository cropRepository, FertilizerService fertilizerService) {
    this.cropRepository = cropRepository;
    this.fertilizerService = fertilizerService;
  }

  public List<Crop> findAll() {
    return cropRepository.findAll();
  }

  public Crop cropById(Long id) throws CropNotFoundException {
    return cropRepository.findById(id)
        .orElseThrow(CropNotFoundException::new);
  }

  /**
   * Find all by date range list.
   *
   * @param startDate the start date
   * @param endDate   the end date
   * @return the list
   */
  public List<Crop> findAllByDateRange(LocalDate startDate, LocalDate endDate) {
    List<Crop> allCrops = cropRepository.findAll();
    return allCrops.stream()
        .filter(crop -> crop.getHarvestDate().isAfter(startDate))
        .filter(crop -> crop.getHarvestDate().isBefore(endDate))
        .toList();
  }

  /**
   * Add crop fertilizer string.
   *
   * @param cropId       the crop id
   * @param fertilizerId the fertilizer id
   * @return the string
   * @throws CropNotFoundException       the crop not found exception
   * @throws FertilizerNotFoundException the fertilizer not found exception
   */
  public String addCropFertilizer(Long cropId, Long fertilizerId)
      throws CropNotFoundException, FertilizerNotFoundException {
    Crop crop = cropById(cropId);
    Fertilizer fertilizer = fertilizerService.fertilizerById(fertilizerId);

    crop.getFertilizers().add(fertilizer);
    cropRepository.save(crop);

    return "Fertilizante e plantação associados com sucesso!";
  }

  public List<Fertilizer> findFertilizersByCropId(Long id) throws CropNotFoundException {
    Crop crop = cropById(id);
    return crop.getFertilizers();
  }
}
