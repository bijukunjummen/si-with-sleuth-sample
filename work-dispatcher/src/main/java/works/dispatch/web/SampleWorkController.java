package works.dispatch.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import works.dispatch.service.WorkUnitGateway;
import works.dispatch.domain.WorkUnit;
import works.dispatch.domain.WorkUnitResponse;

@Controller
public class SampleWorkController {

    @Autowired
    private WorkUnitGateway workUnitGateway;

    @RequestMapping("/generateWork")
    @ResponseBody
    public WorkUnitResponse generateWork(WorkUnit workUnit) {
        return workUnitGateway.generate(workUnit);
    }
}
