package com.lms.constant;

public enum Operation {
	
	/** The add. */
	ADD("ADD"),

	/** The modified. */
	MODIFIED("MODIFED"),

	/** The delete. */
	DELETE("DELETE");

	/** The value. */
	protected String value;
	
	Operation(final String value) {
		this.value=value;
	}

    public String getValue() {
        return this.value;
    }

}
