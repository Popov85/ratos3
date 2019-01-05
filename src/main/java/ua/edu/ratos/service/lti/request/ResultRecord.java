package ua.edu.ratos.service.lti.request;

@SuppressWarnings("ALL")
public class ResultRecord {

	private SourcedGUID sourcedGUID;
	
	private Result result;

	public ResultRecord setSourcedGUID(SourcedGUID sourcedGUID) {
		this.sourcedGUID = sourcedGUID;
		return this;
	}

	public ResultRecord setResult(Result result) {
		this.result = result;
		return this;
	}
}
