package com.BankingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name="users")
@Proxy(lazy=false)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3449124225226660570L;
	private Integer accId;
	@Id
	@Min(value=100000,message="Invalid User ID")
	@Max(value=999999,message="Invalid User ID")
	private Integer userId;
	private String password;
	private String secretQues;
	private String answer;
	private String transPassword;
	private String lockStatus;
}
