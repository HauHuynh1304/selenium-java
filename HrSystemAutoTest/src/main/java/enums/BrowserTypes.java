package enums;

public enum BrowserTypes {
	CHROME("chrome"),
	FIREFOX("firefox"),
	OPERA("opera"),
	EDGE("edge");
	
	private String value;
	
	
	BrowserTypes(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
