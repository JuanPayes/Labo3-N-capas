package org.example.sheikahregister.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.sheikahregister.common.SpecimenMapper;
import org.example.sheikahregister.domain.dto.request.specimen.CreateSpecimenRequest;
import org.example.sheikahregister.domain.dto.request.specimen.UpdateSpecimenRequest;
import org.example.sheikahregister.domain.dto.response.PageableResponse;
import org.example.sheikahregister.domain.dto.response.specimen.SpecimenResponse;
import org.example.sheikahregister.domain.entities.Specimen;
import org.example.sheikahregister.exceptions.ResourceNotFoundException;
import org.example.sheikahregister.repositories.SpecimenRepository;
import org.example.sheikahregister.services.SpecimenServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecimenServicesImpl implements SpecimenServices {
    private final SpecimenMapper specimenMapper;
    private final SpecimenRepository specimenRepository;

    @Override
    @Transactional
    public SpecimenResponse createSpecimen(CreateSpecimenRequest request) {
        return specimenMapper.toDto(
                specimenRepository.save(specimenMapper.toEntityCreate(request))
        );
    }

    @Override
    public PageableResponse getAllSpecimens(int page, int size, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Specimen> specimenPage = specimenRepository.findAll(pageable);

        if (specimenPage.isEmpty())
            throw new ResourceNotFoundException("No specimens are registered in Hyrule");

        return specimenMapper.toDtoPage(specimenPage);
    }

    @Override
    public SpecimenResponse getSpecimenById(UUID id) {
        return specimenMapper.toDto(specimenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specimen not found in Hyrule Records"))
        );
    }

    @Override
    @Transactional
    public SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest request) {
        this.getSpecimenById(id);
        return specimenMapper.toDto(specimenRepository.save(specimenMapper.toEntityUpdate(request, id)));
    }

    @Transactional
    public SpecimenResponse deleteSpecimen(UUID id) {
        SpecimenResponse existSpecimen = this.getSpecimenById(id);
        specimenRepository.deleteById(id);
        return existSpecimen;
    }

}
