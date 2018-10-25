package com.banhuitong.enumerate;

public enum InComeDetailType{
	RECHARGE(1, "充值"), WITHDRAW(2, "提现"), TENDER(3, "出借"), REPAY(4, "还款"),
	REWARD(5, "奖励"), FEE(6, "支出"), INCOME(7, "收入"), OTHER(99, "其他");
	private int value;
	private String desc;

	InComeDetailType(final int value, final String desc) {
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
