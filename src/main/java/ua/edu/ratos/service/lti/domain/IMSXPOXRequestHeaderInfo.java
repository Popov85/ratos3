package ua.edu.ratos.service.lti.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class IMSXPOXRequestHeaderInfo {

	@JacksonXmlProperty(localName = "imsx_version")
	private final String imsxVersion = "V1.01";

	@JacksonXmlProperty(localName = "imsx_messageIdentifier")
	private String imsxMessageIdentifier;

}
