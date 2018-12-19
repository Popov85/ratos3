package ua.edu.ratos.service.lti.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "imsx_POXEnvelopeRequest")
public class IMSXPOXEnvelopeRequest {
	
	@JacksonXmlProperty(isAttribute = true)
	private final String xmlns = "http://www.imsglobal.org/services/ltiv1p1/xsd/imsoms_v1p0";
	
	@JacksonXmlProperty(localName = "imsx_POXHeader")
	private IMSXPOXHeader IMSXPOXHeader;
	
	@JacksonXmlProperty(localName = "imsx_POXBody")
	private IMSXPOXBody IMSXPOXBody;
	
}
