package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Resource;
import ua.edu.ratos.service.dto.in.ResourceInDto;

public interface ResourceTransformer {

    Resource toEntity(ResourceInDto dto);
}
