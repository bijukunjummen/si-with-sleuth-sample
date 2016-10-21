package works.dispatch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.messaging.MessagingException;
import works.dispatch.domain.WorkUnit;
import works.dispatch.domain.WorkUnitResponse;

@Configuration
public class WorksOutbound {

    @Autowired
    private RabbitConfig rabbitConfig;

    @Bean
    public IntegrationFlow toOutboundQueueFlow() {
        return IntegrationFlows.from("worksChannel")
                .transform(Transformers.toJson())
                .log()
                .handle(Amqp.outboundGateway(rabbitConfig.worksRabbitTemplate()))
                .transform(Transformers.fromJson(WorkUnitResponse.class))
                .get();
    }

    @Bean
    public IntegrationFlow handleErrors() {
        return IntegrationFlows.from("errorChannel")
                .transform((MessagingException e) -> e.getFailedMessage().getPayload())
                .transform(Transformers.fromJson(WorkUnit.class))
                .transform((WorkUnit failedWorkUnit) -> new WorkUnitResponse(failedWorkUnit.getId(), failedWorkUnit.getDefinition(), false))
                .get();
    }

}