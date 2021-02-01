package ua.edu.ratos.service.transformer;

import ua.edu.ratos.dao.entity.Staff;
import ua.edu.ratos.service.dto.in.StaffInDto;
import ua.edu.ratos.service.dto.in.StaffUpdInDto;

/**
 * Introduced due to the difficulties of mapping with MapStruct directly
 */
public interface StaffTransformer {

    Staff toEntity(StaffInDto dto);

    Staff toEntity(Staff staff, StaffUpdInDto dto);
}
