package no.interview.tradinginfo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class HTTPRestEndpoint {

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private final String hostURL;
	private final HttpRequestFactory requestFactory;

	public HTTPRestEndpoint(String hostURL) {
		this.hostURL = hostURL;

		requestFactory = HTTP_TRANSPORT.createRequestFactory(request -> {
			request.setParser(new JsonObjectParser(JSON_FACTORY));
		});

	}

	public <T> List<T> fetchObjectsFrom(String resource, Type objectType) throws IOException {
		final GenericUrl resourceURL = new GenericUrl(hostURL + "/" + resource);
		final HttpRequest request = requestFactory.buildGetRequest(resourceURL);

		request.setHeaders(new HttpHeaders().set("x-api-key", "gaqcRZE4bd58gSAJH3XsLYBo1EvwIQo88IfYL1L5"));

		HttpResponse response = request.execute();
		List<T> result = (List<T>) response.parseAs(objectType);

		return result;
	}
}
