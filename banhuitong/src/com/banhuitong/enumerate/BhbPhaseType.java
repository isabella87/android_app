package com.banhuitong.enumerate;

/**
 * 班汇宝项目阶段。
 * 
 * <table>
 * <tr>
 * <th>阶段</th>
 * <th>状态</th>
 * </tr>
 * <tr>
 * <td>待审核</td>
 * <td>0,1,10,20,30</td>
 * </tr>
 * <td>募集中</td>
 * <td>40,50,60</td>
 * </tr>
 * <tr>
 * <td>封闭中</td>
 * <td>&gt;= 70 and &lt; 999</td>
 * </tr>
 * <tr>
 * <td>已结束</td>
 * <td>-1, 999</td>
 * </tr>
 * </table>
 * 
 * @author Haart
 *
 */
public enum BhbPhaseType  {
	/**
	 * 无效
	 */
	INVALID(0),

	/**
	 * 待审核
	 */
	AUDITING(1, BhbStatusType.UN_SUBMITTED, BhbStatusType.MGR_AUDITING,
			BhbStatusType.RISK_CTRL_MGR_AUDITING, BhbStatusType.BUS_SEC_AUDITING,
			BhbStatusType.BUS_VP_AUDITING),

	/**
	 * 募集期
	 */
	RAISING(2, BhbStatusType.RAISING, BhbStatusType.RAISED_FULL, BhbStatusType.BUS_VP_CONFIRMED_FULL),

	/**
	 * 封闭期
	 */
	CLOSED(3, BhbStatusType.RUNNING, BhbStatusType.SETTLEMENT),

	/**
	 * 已结束
	 */
	COMPLETED(4, BhbStatusType.COMPLETED, BhbStatusType.FAILED);

	private final int value;

	private final BhbStatusType[] status;

	/**
	 * 
	 * @param value
	 * @param status
	 */
	BhbPhaseType(final int value, final BhbStatusType... status) {
		this.value = value;
		this.status = status == null ? new BhbStatusType[0] : status;
	}

	public int value() {
		return this.value;
	}

	/**
	 * 
	 * @return
	 */
	public final BhbStatusType[] status() {
		return this.status;
	}

}
