package ua.edu.ratos.web.report;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.dto.in.report.ReportOnResultsInDto;
import ua.edu.ratos.service.dto.out.report.ReportOnResults;
import ua.edu.ratos.service.report.ReportOnResultsService;

@Slf4j
@RestController
@AllArgsConstructor
public class ReportOnResultsController {

    private final ReportOnResultsService reportOnResultsService;

    @PostMapping(value = "/department/report-on-results", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportOnResults findAllForReportBySpec(@RequestBody ReportOnResultsInDto specs) {
        //log.debug("Specs = {}", specs);
        return reportOnResultsService.getReportOnResults(specs);
    }
}
