package no.interview.tradinginfo;

import com.google.api.client.util.Key;

public class Transaction {
	@Key
	private int timestamp;

	@Key
	private String traderId;

	@Key
	private double value;

	public int getTimestamp() {
		return timestamp;
	}

	public String getTraderId() {
		return traderId;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("Transaction[traderId = %s, value = %f, timestamp = %d]", traderId, value, timestamp);
	}
}
