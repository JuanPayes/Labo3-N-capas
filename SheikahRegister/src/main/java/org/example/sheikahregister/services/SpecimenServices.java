package org.example.sheikahregister.services;

import org.example.sheikahregister.domain.dto.request.specimen.CreateSpecimenRequest;
import org.example.sheikahregister.domain.dto.request.specimen.UpdateSpecimenRequest;
import org.example.sheikahregister.domain.dto.response.specimen.SpecimenResponse;

import java.util.List;
import java.util.UUID;

public interface SpecimenServices {
    public SpecimenResponse createSpecimen(CreateSpecimenRequest request);
    public List<SpecimenResponse> getAllSpecimens();
    public SpecimenResponse getSpecimenById(UUID id);
    public SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest request);
    public SpecimenResponse deleteSpecimen(UUID id);
}
