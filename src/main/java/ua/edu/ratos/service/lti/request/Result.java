package ua.edu.ratos.service.lti.request;

@SuppressWarnings("ALL")
public class Result {
	
	private ResultScore resultScore;

	public Result setResultScore(ResultScore resultScore) {
		this.resultScore = resultScore;
		return this;
	}
}
