package ua.edu.ratos.service.transformer.entity_to_dto;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Complaint;
import ua.edu.ratos.service.dto.out.ComplaintOutDto;

@Deprecated
@Component
public class ComplaintDtoTransformer {

    public ComplaintOutDto toDto(@NonNull final Complaint entity) {
        return new ComplaintOutDto()
                .setComplaintId(entity.getComplaintId())
                .setQuestion(entity.getQuestion().getQuestion())
                .setQtype(entity.getQuestion().getType().getTypeId())
                .setComplaint(entity.getComplaintType().getName())
                .setLastComplained(entity.getLastComplained())
                .setTimesComplained(entity.getTimesComplained());
    }
}
