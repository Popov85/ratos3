package ua.edu.ratos.service.lti.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResultScore {
	
	private final String language = "en";
	
	private String textString;

}
