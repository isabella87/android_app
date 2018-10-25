package com.banhuitong.enumerate;

public enum CreditPrjStatusType {
	CA_PROCESSING(0, "转让中"), CA_DONE(1, "已转让"), CA_CANCEL(2,
			"已撤销"), CA_ABOLISH(-1, "流标"), CA_AGAIN(3, "再转让"), CA_APPLIABLE(10, "可转让");

	private int value;
	private String message;

	CreditPrjStatusType(final int value, final String message) {
		this.value = value;
		this.message = message;
	}

	public final int value() {
		return this.value;
	}

	public final String message() {
		return this.message;
	}
}
