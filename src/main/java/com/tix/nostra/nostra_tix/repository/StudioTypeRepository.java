package com.tix.nostra.nostra_tix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tix.nostra.nostra_tix.domain.StudioType;
import com.tix.nostra.nostra_tix.dto.StudioTypeLovDTO;

public interface StudioTypeRepository extends JpaRepository<StudioType, Long> {

    List<StudioTypeLovDTO> findAllProjectedBy();
}
