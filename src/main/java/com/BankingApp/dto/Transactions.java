package com.BankingApp.dto;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Proxy(lazy=false)
@Data
public class Transactions implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7170595873076698277L;
	@EmbeddedId
	private TransactionId transactionId;
	private String transDesc;
	private LocalDate transDate;
	private Long transAmt;
	private String accountNo;
}
