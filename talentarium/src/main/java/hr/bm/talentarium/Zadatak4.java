package hr.bm.talentarium;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Zadatak4 {

	public static void main(String[] args) {
		String[] zaporke = new String[] { "asdFghjk", "asd3fGhjk", "asdfGhjk4", "123aSdfghjk", "asd6H" };

		for (String password : zaporke) {
			System.out.println(password + " - " + checkPassword(password));
		}
	}

	/**
	 * Metoda provjerava da li je zaporka ok.
	 * @param password
	 * @return
	 */
	private static boolean checkPassword(String password) {
		if (containsBigLetter(password) && checkSizeAndNumPos(password)) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda provjerava da li zaporka sadrzi veliko slovo.
	 * @param password
	 * @return
	 */
	private static boolean containsBigLetter(String password) {
		Pattern pattern = Pattern.compile("[A-ZŠĐČĆŽ]");
		Matcher matcher = pattern.matcher(password);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda provjerava da li zaporka ima 8 ili vise znakova, te provjerava
	 * da li postoji broj koji dijeli zaporku u dva niza znakova.
	 * @param password
	 * @return
	 */
	private static boolean checkSizeAndNumPos(String password) {
		int size = password.length();
		if (size < 8) {
			// zaporka nije ok jer ima manje od 8 znakova
			return false;
		}

		Pattern pattern = Pattern.compile("[a-zA-ZšđčćžŠĐČĆŽ]+[0-9][a-zA-ZšđčćžŠĐČĆŽ]+");
		Matcher matcher = pattern.matcher(password);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
}
