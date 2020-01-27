package ua.edu.ratos.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import ua.edu.ratos.service.dto.out.OrganisationOutDto;

import java.util.concurrent.Future;

@Slf4j
@Service
public class TestAsyncService {

    @Async
    public Future<OrganisationOutDto> getLongJobDone(@NonNull final Long orgId) {
        try {
            Thread.sleep(5000);
            OrganisationOutDto dto =
                    new OrganisationOutDto()
                            .setOrgId(orgId)
                            .setName("Test name");
            log.debug("Finished async task!DTO = {}", dto);
            return new AsyncResult(dto);
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to do the async task!", e);
        }
    }
}
