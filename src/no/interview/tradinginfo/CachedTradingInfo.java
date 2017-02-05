package no.interview.tradinginfo;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

public class CachedTradingInfo {

	private static final String HOST = "https://fvjkpkflnc.execute-api.us-east-1.amazonaws.com";

	private List<Trader> traders;
	private List<Transaction> transactions;

	private final HTTPRestEndpoint httpRestEndpoint;

	public CachedTradingInfo() {
		httpRestEndpoint = new HTTPRestEndpoint(HOST);
	}

	public List<Trader> fetchAllTraders() throws IOException {
		if (traders == null) {
			final TypeReference<List<Trader>> typeReference = new TypeReference<List<Trader>>() {
			};

			final List<Trader> response = httpRestEndpoint.fetchObjectsFrom("/prod/traders", typeReference.getType());

			traders = Collections.unmodifiableList(response);
		}

		return traders;
	}

	public List<Transaction> fetchAllTransactions() throws IOException {
		if (transactions == null) {
			TypeReference<List<Transaction>> typeReference = new TypeReference<List<Transaction>>() {
			};

			final List<Transaction> response = httpRestEndpoint.fetchObjectsFrom("/prod/transactions",
					typeReference.getType());
			transactions = Collections.unmodifiableList(response);
		}

		return transactions;
	}
}
