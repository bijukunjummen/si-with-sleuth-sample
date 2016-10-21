package works.handler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import works.handler.domain.WorkUnit;
import works.handler.domain.WorkUnitResponse;

@Service
public class WorkHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkHandler.class);

    public WorkUnitResponse process(WorkUnit workUnit) {
        if (workUnit.isThrowException()) {
            LOGGER.info("Throwing a deliberate exception for work unit - id: {}, definition: {}", workUnit.getId(), workUnit.getDefinition());
            throw new RuntimeException("Throwing a deliberate exception");
        }
        LOGGER.info("Handling work unit - id: {}, definition: {}", workUnit.getId(), workUnit.getDefinition());
        return new WorkUnitResponse(workUnit.getId(), workUnit.getDefinition(), true);
    }
}
