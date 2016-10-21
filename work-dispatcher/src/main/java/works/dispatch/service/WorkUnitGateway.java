package works.dispatch.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import works.dispatch.domain.WorkUnit;
import works.dispatch.domain.WorkUnitResponse;

@MessagingGateway(errorChannel = "errorChannel")
public interface WorkUnitGateway {
	@Gateway(requestChannel = "worksChannel")
	WorkUnitResponse generate(WorkUnit workUnit);

}
