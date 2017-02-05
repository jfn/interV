package no.interview.tradinginfo;

import javax.annotation.Nonnull;

import com.google.api.client.util.Key;

public class Trader {
	@Key
	private String name;

	@Key
	private String city;

	@Key
	private String id;

	public Trader() {
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("Trader[name = %s, city = %s, id = %s]", name, city, id);
	}
}
