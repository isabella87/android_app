package com.banhuitong.enumerate;

/**
 * 班汇宝项目状态
 * 
 * @author Haart
 *
 */
public enum BhbStatusType {
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
	 * 业务副总已确认满标，可以执行放款，全部放款完成后进入产品运行状态。
	 */
	BUS_VP_CONFIRMED_FULL(60),

	/**
	 * 产品运行
	 */
	RUNNING(70),

	/**
	 * 清算
	 */
	SETTLEMENT(90),
	
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
	 * 
	 * @param value
	 */
	BhbStatusType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

}
