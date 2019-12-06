package ua.edu.ratos.service;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.dao.repository.DepartmentRepository;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.out.DepartmentMinOutDto;
import ua.edu.ratos.service.transformer.entity_to_dto.DepartmentMinDtoTransformer;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMinDtoTransformer departmentMinDtoTransformer;

    private final SecurityUtils securityUtils;

    // TODO: CRUD by FAC-ADMIN!

    //-----------------------------------------------Fac. admin (min for drop down)-------------------------------------
    @Transactional(readOnly = true)
    public Set<DepartmentMinOutDto> findAllByFacIdForDropDown() {
        return departmentRepository.findAllByFacIdForDropDown(securityUtils.getAuthStaffId())
                .stream()
                .map(departmentMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }

    //-----------------------------------------------Org. admin (min for drop down)-------------------------------------
    @Transactional(readOnly = true)
    public Set<DepartmentMinOutDto> findAllByFacIdForDropDown(@NonNull final Long facId) {
        return departmentRepository.findAllByFacIdForDropDown(facId)
                .stream()
                .map(departmentMinDtoTransformer::toDto)
                .collect(Collectors.toSet());
    }
}
