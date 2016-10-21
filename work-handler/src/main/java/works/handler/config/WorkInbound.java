package works.handler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.Transformers;
import works.handler.domain.WorkUnit;

@Configuration
public class WorkInbound {

    @Autowired
    private RabbitConfig rabbitConfig;

    @Bean
    public IntegrationFlow inboundFlow() {
        return IntegrationFlows.from(
                Amqp.inboundGateway(rabbitConfig.workListenerContainer()))
                .transform(Transformers.fromJson(WorkUnit.class))
                .log()
                .filter("(headers['x-death'] != null) ? headers['x-death'][0].count < 3: true", f -> f.discardChannel("nullChannel"))
                .handle("workHandler", "process")
                .transform(Transformers.toJson())
                .get();
    }

}
