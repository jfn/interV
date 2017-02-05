package no.interview.tradinginfo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public class TradingRunner {

	final static CachedTradingInfo tradingInfo = new CachedTradingInfo();
	static Command[] commands;

	public static void main(String[] args) throws IOException {

		setupCommands();

		while (true) {
			System.out.println("=== MENU ===");
			System.out.println("Please select one of the below actions");

			int commandIndex = 1;
			for (Command c : commands) {
				System.out.printf("%d. %s\n", commandIndex++, c.title);
			}

			int choice = Utils.readChar() - '1';
			if (choice >= 0 && choice < commands.length) {
				try {
					commands[choice].action.act();
				} catch (Exception ex) {
					System.err.printf("Failed to evaluate <<%s>> : %s\n", commands[choice].title, ex);
				}
			}

			System.out.println("-------------------------------");
		}
	}

	private static void setupCommands() {
		final int startEpoch_2016 = Utils.getEpochOfYearStart(2016);
		final int startEpoch_2017 = Utils.getEpochOfYearStart(2017);

		//@formatter:off
		commands = new Command[]{
			new Command("Find all transactions from Singapore and sort them by name", () -> {
				final List<Trader> traders = tradingInfo.fetchAllTraders();

				traders.stream().filter(t -> t.getCity().equals("Singapore"))
						.sorted((first, second) -> first.getName().compareTo(second.getName()))
						.collect(Collectors.toList())
						.forEach(t -> System.out.println(t));
			}),

			new Command("Find the transaction with the highest value", () -> {
				final List<Transaction> transactions = tradingInfo.fetchAllTransactions();

				final Optional<Transaction> result = transactions.stream()
						.max((first, second) -> Double.compare(first.getValue(), second.getValue()));

				if (result.isPresent()) {
					System.out.println(result.get());
				} else {
					System.out.println("Empty data received, no maximum couldn't be found");
				}
			}),

			new Command("Find all transactions in the year 2016 and sort them by value (high to small)", () -> {
				final List<Transaction> transactions = tradingInfo.fetchAllTransactions();

				transactions.stream()
					.filter(transaction -> transaction.getTimestamp() >= startEpoch_2016 && transaction.getTimestamp() < startEpoch_2017)
					.sorted((first, second) -> Double.compare(second.getValue(), first.getValue()))
					.collect(Collectors.toList())
					.forEach(t -> System.out.println(t));
			}),

			new Command("Find the average of transactions' values from the traders living in Beijing", () -> {
				final List<Trader> traders = tradingInfo.fetchAllTraders();
				final List<Transaction> transactions = tradingInfo.fetchAllTransactions();

				final Set<String> bejingTraderIds = traders.stream().filter(trader -> trader.getCity().equals("Beijing"))
						.map(Trader::getId).collect(Collectors.toSet());

				OptionalDouble result = transactions.stream()
										       .filter(transaction -> bejingTraderIds.contains(transaction.getTraderId()))
										       .mapToDouble(Transaction::getTimestamp)
										       .average();
				
				if (result.isPresent()) {
					System.out.println("Average of Beijing transactions is " + result.getAsDouble());
				} else {
					System.out.println("Empty data received, no average could be calculated");
				}
			}),

			new Command("Exit", () -> System.exit(0))

		};
		//@formatter:on
	}
}
