package com.banhuitong.enumerate;


public enum InvestPrjStatus {
	ALL(0xFFFF, ""), FINANCING(1, "投标中"), REPAYING(2, "还款中"), REPAYOVER(3, "还款完成"), FAILED(4, "流标");
	private int value;
	private String desc;

	InvestPrjStatus(final int value, final String desc) {
		this.value = value;
		this.desc = desc;
	}

	public final int value() {
		return this.value;
	}

	public String desc() {
		return this.desc;
	}

}
