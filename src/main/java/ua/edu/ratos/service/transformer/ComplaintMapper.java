package ua.edu.ratos.service.transformer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ua.edu.ratos.dao.entity.Complaint;
import ua.edu.ratos.service.dto.out.ComplaintOutDto;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ComplaintMapper {

    @Mapping(target = "question", source = "entity.question.question")
    @Mapping(target = "qtype", source = "entity.question.type.typeId")
    @Mapping(target = "complaint", source = "entity.complaintType.name")
    ComplaintOutDto toDto(Complaint entity);
}
