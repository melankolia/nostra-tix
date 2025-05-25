package com.tix.nostra.nostra_tix.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tix.nostra.nostra_tix.dto.StudioTypeLovDTO;
import com.tix.nostra.nostra_tix.repository.StudioTypeRepository;
import com.tix.nostra.nostra_tix.service.StudioTypeService;

@Service
public class StudioTypeImpl implements StudioTypeService {

    @Autowired
    private StudioTypeRepository studioTypeRepository;

    @Override
    public List<StudioTypeLovDTO> findAll() {
        List<StudioTypeLovDTO> studioTypes = studioTypeRepository.findAllProjectedBy();

        return studioTypes;
    }
}