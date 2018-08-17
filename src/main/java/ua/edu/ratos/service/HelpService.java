package ua.edu.ratos.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.repository.HelpRepository;
import ua.edu.ratos.service.dto.entity.HelpInDto;
import ua.edu.ratos.service.dto.transformer.DtoHelpTransformer;
import java.util.List;

@Service
public class HelpService {

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private DtoHelpTransformer transformer;


    @Transactional
    public Long save(@NonNull HelpInDto dto) {
        Help help = transformer.fromDto(dto);
        return helpRepository.save(help).getHelpId();
    }

    @Transactional(readOnly = true)
    public List<Help> findByStaff(@NonNull Long staId) {
        return helpRepository.findByStaffWithResources(staId);
    }

    @Transactional(readOnly = true)
    public List<Help> findByDepartment(@NonNull Long depId) {
        return helpRepository.findByDepartmentWithResources(depId);
    }

    @Transactional
    public void update(@NonNull HelpInDto dto) {
        if (dto.getHelpId()==null || dto.getHelpId()==0)
            throw new RuntimeException("Invalid ID");
        Help help = transformer.fromDto(dto);
        helpRepository.save(help);
    }

    @Transactional
    public void deleteById(@NonNull Long helpId) {
        helpRepository.deleteById(helpId);
    }
}
