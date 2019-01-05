package ua.edu.ratos.service.lti.request;

@SuppressWarnings("ALL")
public class ReplaceResultRequest {
	
	private ResultRecord resultRecord;

	public ReplaceResultRequest setResultRecord(ResultRecord resultRecord) {
		this.resultRecord = resultRecord;
		return this;
	}
}
