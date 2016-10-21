package works.dispatch.web;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Responsible for injecting Trace related headers in the response back to the caller.
 */
public class HttpResponseInjectingTraceFilter extends GenericFilterBean {

    private final Tracer tracer;
    private final SpanInjector<HttpServletResponse> spanInjector;

    public HttpResponseInjectingTraceFilter(Tracer tracer, SpanInjector<HttpServletResponse> spanInjector) {
        this.tracer = tracer;
        this.spanInjector = spanInjector;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Span currentSpan = this.tracer.getCurrentSpan();
        this.spanInjector.inject(currentSpan, response);
        filterChain.doFilter(request, response);
    }
}
