package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.FacultyRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.out.FacultyMinOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.FacultyMinDtoTransformer;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final FacultyMinDtoTransformer facultyMinDtoTransformer;

    private final SecurityUtils securityUtils;

    // TODO: CRUD for ORG-ADMIN!

    //-------------------------------------------------Staff (min for drop down)----------------------------------------
    @Transactional(readOnly = true)
    public Set<FacultyMinOutDto> findAllByOrgIdForDropDown() {
        return facultyRepository.findAllByOrgIdForDropDown(securityUtils.getAuthOrgId())
                .stream()
                .map(facultyMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    //---------------------------------------------Global admin (min for drop down)-------------------------------------

    @Transactional(readOnly = true)
    public Set<FacultyMinOutDto> findAllByOrgIdForDropDown(@NonNull final Long orgId) {
        return facultyRepository.findAllByOrgIdForDropDown(orgId)
                .stream()
                .map(facultyMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }
}
