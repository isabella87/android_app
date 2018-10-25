package com.banhuitong.enumerate;

public enum EntStatusType {
	/**
	 * 未提交
	 */
	UN_SUBMITTED(0),
	/**
	 * 待项目经理审批。
	 */
	MGR_AUDITING(1),
	/**
	 * 待风控审批。
	 */
	RISK_CTRL_MGR_AUDITING(10),
	/**
	 * 待评委会审批。
	 */
	BUS_SEC_AUDITING(20),
	/**
	 * 待业务副总批准上线
	 */
	BUS_VP_AUDITING(30),
	/**
	 * 募集中
	 */
	RAISING(40),
	/**
	 * 募集满
	 */
	RAISED_FULL(50),
	/**
	 * 业务副总已确认满标
	 */
	BUS_VP_CONFIRMED_FULL(60),
	/**
	 * 财务已核收服务费
	 */
	CHECKED_FEE(70),
	/**
	 * 业务副总已批准放款
	 */
	BUS_VP_CONFIRMED_LOAN(80),
	/**
	 * 正在还款
	 */
	REPAYING(90),
	/**
	 * 已逾期
	 */
	TIME_OUT(100),
	/**
	 * 已展期
	 */
	EXTENSION(110),
	/**
	 * 已结清
	 */
	COMPLETED(999),
	/**
	 * 已流标
	 */
	FAILED(-1);

	private int value;

	/**
	 * 创建<code>EntStatusType</code>对象。
	 * 
	 * @param value
	 *            枚举对象关联的值。
	 */
	EntStatusType(final int value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see org.json.ValueEnum#value()
	 */
	public final int value() {
		return this.value;
	}

	/**
	 * 
	 * @return
	 */
	public final long longValue() {
		return this.value;
	}
}
