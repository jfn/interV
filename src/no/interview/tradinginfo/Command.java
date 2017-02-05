package no.interview.tradinginfo;

import java.io.IOException;

public class Command {
	public final String title;
	public final Action action;

	public Command(String title, Action action) {
		this.title = title;
		this.action = action;
	}
}

@FunctionalInterface
interface Action {
	void act() throws IOException;
}
