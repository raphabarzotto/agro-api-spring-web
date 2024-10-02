package com.raphabarzotto.agro_api.repository;

import com.raphabarzotto.agro_api.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Farm repository.
 */
@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

}
