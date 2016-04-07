package hr.bm.utils;

import java.io.IOException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Hello world!
 */
public class MyStock {

	private MyStock() {}

	public static Stock getStock(String symbol) throws IOException {
		return YahooFinance.get(symbol);
	}

}
