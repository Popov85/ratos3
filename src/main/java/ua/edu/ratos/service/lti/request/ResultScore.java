package ua.edu.ratos.service.lti.request;

@SuppressWarnings("ALL")
public class ResultScore {
	
	private final String language = "en";
	
	private String textString;

	public ResultScore setTextString(String textString) {
		this.textString = textString;
		return this;
	}
}
