package com.lms.constant;

public enum Status {

	/** The active. */
	ACTIVE("A"),
	/** The inactive. */
	INACTIVE("I"),
	/** The rejected. */
	REJECTED("R"),
	/** The locked. */
	LOCKED("L"),
	/** The expired. */
	EXPIRED("E"),
	/** The delete. */
	DELETE("D"),;

	/** The value. */
	protected String value;

	/**
	 * Instantiates a new status.
	 *
	 * @param value the value
	 */
	Status(final String value) {
		this.value = value;
	}

	/**
	 * Value.
	 *
	 * @return the string
	 */
	public String value() {
		return this.value;
	}
}
