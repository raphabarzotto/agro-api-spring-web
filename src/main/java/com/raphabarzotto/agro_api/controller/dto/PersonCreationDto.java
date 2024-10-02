package com.raphabarzotto.agro_api.controller.dto;

import com.raphabarzotto.agro_api.staff.entity.Person;
import com.raphabarzotto.agro_api.staff.security.Role;

/**
 * The type Person creation dto.
 */
public record PersonCreationDto(
    String username,
    String password,
    String role) {

  public Person toEntity() {
    Role roleEnum = mapRole(role);
    return new Person(username, password, roleEnum);
  }

  private Role mapRole(String role) {
    return switch (role) {
      case "ADMIN" -> Role.ADMIN;
      case "MANAGER" -> Role.MANAGER;
      case "USER" -> Role.USER;
      default -> throw new IllegalArgumentException("Unknown role: " + role);
    };
  }
}
