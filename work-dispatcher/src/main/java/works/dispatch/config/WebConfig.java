package works.dispatch.config;

import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import works.dispatch.web.HttpResponseInjectingTraceFilter;
import works.dispatch.web.HttpServletResponseInjector;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 4)
    public HttpResponseInjectingTraceFilter responseInjectingTraceFilter(Tracer tracer) {
        return new HttpResponseInjectingTraceFilter(tracer, httpServletResponseSpanInjector());
    }

    @Bean
    public SpanInjector<HttpServletResponse> httpServletResponseSpanInjector() {
        return new HttpServletResponseInjector();
    }

}
