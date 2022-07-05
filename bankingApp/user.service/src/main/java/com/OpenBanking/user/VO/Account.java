package com.OpenBanking.user.VO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Account {
	
	private Long accountId;
	private String number;
    private BigDecimal actualBalance;
    private String type;
    private String status;
    private BigDecimal availableBalance;
    
    
    
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public BigDecimal getActualBalance() {
		return actualBalance;
	}
	public void setActualBalance(BigDecimal actualBalance) {
		this.actualBalance = actualBalance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}
    
    

}
