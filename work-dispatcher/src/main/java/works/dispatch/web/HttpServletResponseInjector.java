package works.dispatch.web;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanInjector;

import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseInjector implements SpanInjector<HttpServletResponse> {

	@Override
    public void inject(Span span, HttpServletResponse carrier) {
		if (span == null) {
			return;
		}
		if (!carrier.containsHeader(Span.SPAN_ID_NAME)) {
			carrier.addHeader(Span.SPAN_ID_NAME, Span.idToHex(span.getSpanId()));
			carrier.addHeader(Span.TRACE_ID_NAME, Span.idToHex(span.getTraceId()));
		}
	}

}