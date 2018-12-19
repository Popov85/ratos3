package ua.edu.ratos.service.lti.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class IMSXPOXHeader {
	
	@JacksonXmlProperty(localName = "imsx_POXRequestHeaderInfo")
	private IMSXPOXRequestHeaderInfo imsxPOXRequestHeaderInfo;

}
