package emarket.metier.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Classe utilitaire pour convertir une chaîne en date SQL et vice-versa.
 */
public class DateUtil {

	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
	public  static final String           DATE_REGEX  = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

	private DateUtil() {}


	/**
	 * Converti une chaîne de caractère sous le format DD/MM/YYYY en date SQL
	 * @param str Chaîne à convertir
	 * @return La date SQL convertie
	 * @throws ParseException Se lance si la date est mal formatée.
	 */
	public static Date stringToDate(String str) throws ParseException {
		return new Date(FORMAT_DATE.parse(str).getTime());
	}

	/**
	 * Convertit une date en chaîne de caractères
	 * @param date Le date à convertir
	 * @return La date sous forme de chaîne
	 */
	public static String dateToString(Date date) {
		return FORMAT_DATE.format(date);
	}


}
