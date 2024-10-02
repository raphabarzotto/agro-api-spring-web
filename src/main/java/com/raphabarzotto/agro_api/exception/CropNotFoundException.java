package com.raphabarzotto.agro_api.exception;

/**
 * The type Crop not found exception.
 */
public class CropNotFoundException extends NotFoundException {
  public CropNotFoundException() {
    super("Plantação não encontrada!");
  }
}
