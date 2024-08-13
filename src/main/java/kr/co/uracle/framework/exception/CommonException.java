package kr.co.uracle.framework.exception;

public class CommonException extends Exception {
	private final String code;
	private final String msg;

	public CommonException (String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode () {
		return code;
	}

	public String getMsg () {
		return msg;
	}

	@Override
	public String toString () {
		return "CommonException{" +
			"code='" + code + '\'' +
			", msg='" + msg + '\'' +
			'}';
	}
}
