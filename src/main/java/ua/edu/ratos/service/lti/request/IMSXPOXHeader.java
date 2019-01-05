package ua.edu.ratos.service.lti.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@SuppressWarnings("ALL")
public class IMSXPOXHeader {
	
	@JacksonXmlProperty(localName = "imsx_POXRequestHeaderInfo")
	private IMSXPOXRequestHeaderInfo imsxPOXRequestHeaderInfo;

	public IMSXPOXHeader setImsxPOXRequestHeaderInfo(IMSXPOXRequestHeaderInfo imsxPOXRequestHeaderInfo) {
		this.imsxPOXRequestHeaderInfo = imsxPOXRequestHeaderInfo;
		return this;
	}
}
