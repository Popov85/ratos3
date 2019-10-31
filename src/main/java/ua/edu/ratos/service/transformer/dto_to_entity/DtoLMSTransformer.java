package ua.edu.ratos.service.transformer.dto_to_entity;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.ratos.dao.entity.Organisation;
import ua.edu.ratos.dao.entity.lms.LMS;
import ua.edu.ratos.dao.entity.lms.LTICredentials;
import ua.edu.ratos.dao.entity.lms.LTIVersion;
import ua.edu.ratos.security.SecurityUtils;
import ua.edu.ratos.service.dto.in.LMSInDto;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DtoLMSTransformer {

    @PersistenceContext
    private EntityManager em;

    private SecurityUtils securityUtils;

    @Autowired
    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    public LMS toEntity(@NonNull final LMSInDto dto) {
        LMS lms = new LMS();
        lms.setLmsId(dto.getLmsId());
        lms.setName(dto.getName());
        LTICredentials credentials = new LTICredentials();
        credentials.setKey(dto.getKey());
        credentials.setSecret(dto.getSecret());
        lms.setCredentials(credentials);
        lms.setLtiVersion(em.getReference(LTIVersion.class, dto.getVersionId()));
        lms.setOrganisation(em.getReference(Organisation.class, securityUtils.getAuthOrgId()));
        return lms;
    }
}
