package com.banhuitong.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PrjEntItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1224340031236737596L;
	private String itemNo;
	private String itemShowName;
	private BigDecimal moneyRate;
	private BigDecimal amt;
	private long borrowDays;
	private long flags;
	private long type;
	private Date financingEndTime;
	private BigDecimal investedMoney;
	private long status;
	private long pId;
	private String hash;
	
	public long getPId() {
		return pId;
	}
	public void setPId(long pId) {
		this.pId = pId;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public BigDecimal getInvestedMoney() {
		return investedMoney;
	}
	public void setInvestedMoney(BigDecimal investedMoney) {
		this.investedMoney = investedMoney;
	}
	public Date getFinancingEndTime() {
		return financingEndTime;
	}
	public void setFinancingEndTime(Date financingEndTime) {
		this.financingEndTime = financingEndTime;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	public long getFlags() {
		return flags;
	}
	public void setFlags(long flags) {
		this.flags = flags;
	}
	public BigDecimal getMoneyRate() {
		return moneyRate;
	}
	public void setMoneyRate(BigDecimal moneyRate) {
		this.moneyRate = moneyRate;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemShowName() {
		return itemShowName;
	}
	public void setItemShowName(String itemShowName) {
		this.itemShowName = itemShowName;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public long getBorrowDays() {
		return borrowDays;
	}
	public void setBorrowDays(long borrowDays) {
		this.borrowDays = borrowDays;
	}
}
