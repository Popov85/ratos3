package ua.edu.ratos.service.lti.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@SuppressWarnings("ALL")
@JacksonXmlRootElement(localName = "imsx_POXEnvelopeRequest")
public class IMSXPOXEnvelopeRequest {
	
	@JacksonXmlProperty(isAttribute = true)
	private final String xmlns = "http://www.imsglobal.org/services/ltiv1p1/xsd/imsoms_v1p0";

	private IMSXPOXHeader imsxPOXHeader;

	private IMSXPOXBody imsxPOXBody;

	@JacksonXmlProperty(localName = "imsx_POXHeader")
	public IMSXPOXEnvelopeRequest setIMSXPOXHeader(IMSXPOXHeader IMSXPOXHeader) {
		this.imsxPOXHeader = IMSXPOXHeader;
		return this;
	}

	@JacksonXmlProperty(localName = "imsx_POXBody")
	public IMSXPOXEnvelopeRequest setIMSXPOXBody(IMSXPOXBody IMSXPOXBody) {
		this.imsxPOXBody = IMSXPOXBody;
		return this;
	}
}
