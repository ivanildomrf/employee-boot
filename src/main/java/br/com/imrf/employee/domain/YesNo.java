package br.com.imrf.employee.domain;

public enum YesNo {

	Yes("Y"),

	No("N");
	
	private String code;
	
	private YesNo(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
