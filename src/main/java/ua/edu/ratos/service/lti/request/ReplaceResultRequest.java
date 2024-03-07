package ua.edu.ratos.service.lti.request;


public class ReplaceResultRequest {
	
	private ResultRecord resultRecord;

	public ResultRecord getResultRecord() {
		return resultRecord;
	}

	public ReplaceResultRequest setResultRecord(ResultRecord resultRecord) {
		this.resultRecord = resultRecord;
		return this;
	}
}
