package org.cryptomator.sanitizer.utils;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static org.cryptomator.sanitizer.utils.StringUtils.cutOfAtEnd;
import static org.cryptomator.sanitizer.utils.StringUtils.cutOfAtStart;
import static org.cryptomator.sanitizer.utils.StringUtils.repeat;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameUtil {

	private static final Pattern PATTERN = Pattern.compile("^(([A-Z2-7]{8}){3,}[A-Z2-7=]{1,8}).*", CASE_INSENSITIVE);

	public static Optional<String> decryptablePartOfName(String name) {
		String result = name;
		result = cutOfAtEnd(result, ".lng");
		result = cutOfAtStart("0", result);
		Matcher matcher = PATTERN.matcher(result);
		if (matcher.matches()) {
			result = matcher.group(1);
			result = result + repeat('=', numMissing(result));
			result = result.toUpperCase();
			return Optional.of(result);
		} else {
			return Optional.empty();
		}
	}

	public static int numMissing(String name) {
		if (name.startsWith("0")) {
			return (8 - (name.length() - 1) % 8) % 8;
		} else {
			return (8 - name.length() % 8) % 8;
		}
	}

}
