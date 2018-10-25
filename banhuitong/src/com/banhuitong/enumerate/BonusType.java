package com.banhuitong.enumerate;


/**
 * 还款类型
 * 
 * @author Haart
 *
 */
public enum BonusType {
	/**
	 * 还息
	 */
	INTEREST(0), 
	
	/**
	 * 还本
	 */
	PRINCIPAL(1),
	
	/**
	 * 班汇宝优先期间分红
	 */
	BHB_PRIOR_BONUS(2),
	
	/**
	 * 班汇宝劣后期间分红
	 */
	BHB_RISK_BONUS(3),
	
	/**
	 * 班汇宝优先本金
	 */
	BHB_PRIOR_PRINCIPAL(4),
	
	/**
	 * 班汇宝优先收益
	 */
	BHB_PRIOR_PROFIT(5),
	
	/**
	 * 班汇宝劣后本金
	 */
	BHB_RISK_PRINCIPAL(6),
	
	/**
	 * 班汇宝劣后补贴
	 */
	BHB_LAST_RISK_SUBSIDIES(7),
	
	/**
	 * 班汇宝优先盈余分红
	 */
	BHB_LAST_PRIOR_SURPLUS(8),
	
	/**
	 * 班汇宝劣后盈余分红
	 */
	BHB_LAST_RISK_SURPLUS(9);
	
	private final int value;

	BonusType(int value) {
		this.value = value;
	}

	public final long value() {
		return value;
	}
	
	public static String name(int value) {
		switch(value){
			case 0: 
				return "利息";
			case 1: 
				return "本金";
			case 2: 
				return "普通资金\n期间分红";
			case 3: 
				return "第三方担保资金\n期间分红";
			case 4: 
				return "普通资金\n本金";
			case 5: 
				return "普通资金\n收益";
			case 6: 
				return "第三方担保资金\n本金";
			case 7: 
				return "第三方担保资金\n补贴";
			case 8: 
				return "普通资金\n盈余分红";
			case 9: 
				return "第三方担保资金\n盈余分红";
			default:
		}
		return "";
	}

}
