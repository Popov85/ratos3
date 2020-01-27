package ua.edu.ratos.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import ua.edu.ratos.service.TestAsyncService;
import ua.edu.ratos.service.dto.out.OrganisationOutDto;

import java.util.concurrent.Future;

@Slf4j
@RestController
@AllArgsConstructor
public class TestAsyncController {

    private final TestAsyncService testAsyncService;

    @GetMapping(value = "/test/long")
    public DeferredResult<ResponseEntity<?>> getLongJobDone(@RequestParam Long orgId) {
        log.debug("Inside the controller, thread name = {}",Thread.currentThread().getName());
        DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();
        Future<OrganisationOutDto> future = testAsyncService.getLongJobDone(orgId);
        log.debug("After invoking the async method");
        OrganisationOutDto organisationOutDto;
        try {
            organisationOutDto = future.get();
            log.debug("Got the result out of Future, thread name = {}", Thread.currentThread().getName());
        } catch (Exception e) {
            dr.setErrorResult(e);
            ResponseEntity responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
            dr.setErrorResult(responseEntity);
            return dr;
        }
        dr.setResult(ResponseEntity.ok().body(organisationOutDto));
        log.debug("Set the result to the DeferredResult, working thread = {}", Thread.currentThread().getName());
        return dr;
    }
}
