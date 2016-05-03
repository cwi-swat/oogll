package oogll.symbol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oogll.GLL;

public class RegExp implements Terminal, Symbol {

	private Pattern pattern;

	public RegExp(String regex) {
		this.pattern = Pattern.compile(regex);
	}
	
	protected void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
	
	protected Pattern getPattern() {
		return pattern;
	}

	@Override
	public String match(int pos, GLL driver) {
		String src = driver.getSrc();
		Matcher m = pattern.matcher(src.subSequence(pos, src.length()));
		if (m.lookingAt()) {
			return result(m);
		}
		return null;
	}


	protected String result(Matcher m) {
		return m.group();
	}
	
	@Override
	public String toString() {
		return pattern.toString();
	}


}
