package ua.edu.ratos.service;

import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ratos.domain.entity.Help;
import ua.edu.ratos.domain.entity.Resource;
import ua.edu.ratos.domain.entity.Staff;
import ua.edu.ratos.domain.repository.HelpRepository;
import ua.edu.ratos.domain.repository.QuestionRepository;
import ua.edu.ratos.domain.repository.ResourceRepository;
import ua.edu.ratos.domain.repository.StaffRepository;
import ua.edu.ratos.service.dto.entity.HelpInDto;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class HelpService {

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public Long save(@NonNull HelpInDto dto) {
        Help help = fromDto(dto);
        return helpRepository.save(help).getHelpId();
    }

    @Transactional
    public void update(@NonNull HelpInDto dto) {
        Help help = fromDto(dto);
        helpRepository.save(help);
    }


    @Transactional
    public void deleteById(@NonNull Long helpId) {
        helpRepository.deleteById(helpId);
    }

    private Help fromDto(HelpInDto dto) {
        Help help = modelMapper.map(dto, Help.class);
        help.setStaff(em.getReference(Staff.class, dto.getStaffId()));
        if (dto.getResourceId()!=0) {
            Set<Resource> resources = new HashSet<>();
            resources.add(em.find(Resource.class, dto.getResourceId()));
            help.setResources(resources);
        } else {
            help.getResources().clear();
        }
        return help;
    }
}
