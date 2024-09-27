package kr.co.uracle.framework.exception;

public class CommonException extends RuntimeException {
	private final int errorCode;
	private final String errorMessage;

	public CommonException(final int errorCode, final String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
