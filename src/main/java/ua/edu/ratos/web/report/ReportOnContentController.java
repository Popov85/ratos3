package ua.edu.ratos.web.report;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ratos.service.dto.in.report.ReportOnContentInDto;
import ua.edu.ratos.service.dto.out.report.ReportOnContent;
import ua.edu.ratos.service.report.ReportOnContentService;
import ua.edu.ratos.service.validator.ReportOnContentInDtoValidator;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
public class ReportOnContentController {

    private final ReportOnContentService reportOnContentService;

    private final ReportOnContentInDtoValidator validator;

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(validator);
    }


    // Universal endpoint. Use it for any report!
    @PostMapping(value = "/dep-admin/report-on-content", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportOnContent getReportOnContent(@Valid @RequestBody ReportOnContentInDto dto) {
        return reportOnContentService.getReportOnContent(dto);
    }

    //-------------------------------------------------For future references--------------------------------------------

    @PostMapping(value = "/dep-admin/report-on-content-by-dep", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportOnContent getReportOnContentByDep(@Valid @RequestBody ReportOnContentInDto dto) {
        return reportOnContentService.getReportOnContentByDep(dto);
    }

    @PostMapping(value = "/fac-admin/report-on-content-by-fac", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportOnContent getReportOnContentByFac(@Valid @RequestBody ReportOnContentInDto dto) {
        return reportOnContentService.getReportOnContentByFac(dto);
    }

    @PostMapping(value = "/org-admin/report-on-content-by-org", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportOnContent getReportOnContentByOrg(@Valid @RequestBody ReportOnContentInDto dto) {
        return reportOnContentService.getReportOnContentByOrg(dto);
    }

    @PostMapping(value = "/global-admin/report-on-content-by-org", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportOnContent getReportOnContentByRatos(@Valid @RequestBody ReportOnContentInDto dto) {
        return reportOnContentService.getReportOnContentByRatos(dto);
    }

}
