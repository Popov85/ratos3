package ua.edu.ratos.service.lti.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@SuppressWarnings("ALL")
public class IMSXPOXRequestHeaderInfo {

	@JacksonXmlProperty(localName = "imsx_version")
	private final String imsxVersion = "V1.0";

	@JacksonXmlProperty(localName = "imsx_messageIdentifier")
	private String imsxMessageIdentifier;

	public IMSXPOXRequestHeaderInfo setImsxMessageIdentifier(String imsxMessageIdentifier) {
		this.imsxMessageIdentifier = imsxMessageIdentifier;
		return this;
	}
}
