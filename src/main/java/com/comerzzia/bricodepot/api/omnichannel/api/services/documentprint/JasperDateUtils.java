package com.comerzzia.bricodepot.api.omnichannel.api.services.documentprint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

// En vez de importarla de dependencia la copio

/**
 * Utility helpers exposed to Jasper reports to safely convert values to dates.
 */
public final class JasperDateUtils {

	private static final String[] SIMPLE_DATE_PATTERNS = { "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy", "yyyyMMddHHmmss" };

	private JasperDateUtils() {
		// utility class
	}

	/**
	 * Attempts to normalize the supplied value into a {@link Date} instance. Supported inputs include {@link Date},
	 * {@link Calendar}, {@link Number}, {@link CharSequence} (ISO instants, offsets and a few well-known legacy
	 * patterns) and any {@link Instant}-convertible object. The method never throws and will return {@code null} when
	 * no conversion is possible.
	 *
	 * @param value
	 *            arbitrary date representation (may be {@code null})
	 * @return a {@link Date} instance or {@code null}
	 */
	public static Date toDate(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Date) {
			return (Date) value;
		}

		if (value instanceof Calendar) {
			return ((Calendar) value).getTime();
		}

		if (value instanceof Instant) {
			return Date.from((Instant) value);
		}

		if (value instanceof Number) {
			long epochMillis = ((Number) value).longValue();
			return new Date(epochMillis);
		}

		if (value instanceof CharSequence) {
			return parseFromString(value.toString().trim());
		}

		if (value instanceof OffsetDateTime) {
			return Date.from(((OffsetDateTime) value).toInstant());
		}

		if (value instanceof ZonedDateTime) {
			return Date.from(((ZonedDateTime) value).toInstant());
		}

		if (value instanceof LocalDateTime) {
			return Date.from(((LocalDateTime) value).atZone(ZoneId.systemDefault()).toInstant());
		}

		if (value instanceof LocalDate) {
			return Date.from(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant());
		}

		return parseFromString(value.toString().trim());
	}

	private static Date parseFromString(String text) {
		if (text.isEmpty()) {
			return null;
		}

		// epoch millis
		if (text.chars().allMatch(Character::isDigit)) {
			try {
				return new Date(Long.parseLong(text));
			}
			catch (NumberFormatException ignore) {
				// fall through to other parsers
			}
		}

		// ISO instant or offset date-time
		Date parsed = parseIsoInstant(text);
		if (parsed != null) {
			return parsed;
		}

		for (String pattern : SIMPLE_DATE_PATTERNS) {
			parsed = parseWithPattern(text, pattern);
			if (parsed != null) {
				return parsed;
			}
		}

		// java.util.Date#toString() style (Tue Oct 28 09:33:21 CET 2025)
		parsed = parseWithPattern(text, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US, TimeZone.getDefault());
		if (parsed != null) {
			return parsed;
		}

		return null;
	}

	private static Date parseIsoInstant(String text) {
		try {
			return Date.from(Instant.parse(text));
		}
		catch (DateTimeParseException ignore) {
			// continue
		}

		try {
			return Date.from(OffsetDateTime.parse(text).toInstant());
		}
		catch (DateTimeParseException ignore) {
			// continue
		}

		try {
			return Date.from(ZonedDateTime.parse(text).toInstant());
		}
		catch (DateTimeParseException ignore) {
			// continue
		}

		try {
			LocalDateTime localDateTime = LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		}
		catch (DateTimeParseException ignore) {
			// continue
		}

		try {
			LocalDate localDate = LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
			return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
		catch (DateTimeParseException ignore) {
			// continue
		}

		return null;
	}

	private static Date parseWithPattern(String text, String pattern) {
		return parseWithPattern(text, pattern, Locale.getDefault(), TimeZone.getDefault());
	}

	private static Date parseWithPattern(String text, String pattern, Locale locale, TimeZone timeZone) {
		Objects.requireNonNull(pattern, "pattern");

		SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
		format.setLenient(true);
		format.setTimeZone(timeZone);

		try {
			return format.parse(text);
		}
		catch (ParseException exception) {
			return null;
		}
	}

	/**
	 * Formats the supplied value using the provided pattern once it has been converted to a {@link Date}.
	 *
	 * @param value
	 *            arbitrary date representation
	 * @param pattern
	 *            output pattern compatible with {@link SimpleDateFormat}
	 * @return the formatted string or an empty string when the value cannot be converted
	 */
	public static String format(Object value, String pattern) {
		Date date = toDate(value);
		if (date == null || pattern == null) {
			return "";
		}

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		formatter.setLenient(true);
		return formatter.format(date);
	}

	/**
	 * Convenience wrapper that renders the date using the {@code dd/MM/yyyy} pattern.
	 *
	 * @param value
	 *            arbitrary date representation
	 * @return the formatted string or an empty string when the value cannot be converted
	 */
	public static String formatDayMonthYear(Object value) {
		return format(value, "dd/MM/yyyy");
	}
}
