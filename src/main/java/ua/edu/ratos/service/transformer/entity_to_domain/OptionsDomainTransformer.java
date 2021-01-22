package ua.edu.ratos.service.transformer.entity_to_domain;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Options;
import ua.edu.ratos.service.domain.OptionsDomain;

@Deprecated
@Slf4j
@Component
public class OptionsDomainTransformer {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OptionsDomain toDomain(@NonNull final Options entity) {
        return modelMapper.map(entity, OptionsDomain.class);
    }
}
