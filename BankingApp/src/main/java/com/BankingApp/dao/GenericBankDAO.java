package com.BankingApp.dao;

import com.BankingApp.dto.*;
import com.BankingApp.exception.BankingException;
import com.BankingApp.util.QueryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository("bankDao")
@Transactional
@Slf4j
public class GenericBankDAO implements IGenericBankDAO
{
	@PersistenceContext
	EntityManager em;
	
	@Override
	public BankUser getUserDetails(BankUser user) throws BankingException
	{
		TypedQuery<BankUser> query;
		if(user.getPassword()==null)
		{
			query=em.createQuery(QueryMapper.validateUser,BankUser.class);
			query.setParameter("uid",user.getUserId());
			query.setParameter("lock", "N");
		}
		else
		{
			query=em.createQuery(QueryMapper.getUser,BankUser.class);
			query.setParameter("uid",user.getUserId());
			query.setParameter("password", user.getPassword());
			user.setLockStatus("N");
			query.setParameter("lock", user.getLockStatus());
		}
		try
		{
			user = query.getSingleResult();
			log.info("User is validated!");
		}
		catch(PersistenceException e)
		{
			throw new BankingException("Invalid Credential");
		}
		return user;
	}

	@Override
	public AccountMaster getAccountDetails(int accid) throws BankingException
	{
		try
		{
			AccountMaster am=em.getReference(AccountMaster.class, accid);
			return am;
		}
		catch(PersistenceException e)
		{
			throw new BankingException("Account Id does not exist");
		}
	}

	
	

	

	@Override
	public List<Transactions> getStatements(int accid) throws BankingException
	{
		TypedQuery<Transactions> query=em.createQuery(QueryMapper.getTransactions,Transactions.class);
		query.setParameter("accid",accid);
		List<Transactions> transactionLogs;
		try {
			transactionLogs = query.getResultList();
			if(transactionLogs.size()>0)
				return transactionLogs;
			else
				throw new BankingException("No records found");
		} catch(PersistenceException e) {
			throw new BankingException("No records found");
		}

	}
	
	

	

	@Override
	public List<SecurityQuest> getQuestionList() throws BankingException {
		TypedQuery<SecurityQuest> query=em.createQuery
				("FROM SecurityQuest",SecurityQuest.class);
		List<SecurityQuest> list;
		try {
			list = query.getResultList();
			return list;
		} catch(PersistenceException e) {
			throw new BankingException("List fetching unsuccessful");
		}

	}

	@Override
	public ServiceTracker showServiceByID(Integer serviceId) throws BankingException
	{
		try {
			ServiceTracker sts = em.getReference(ServiceTracker.class, serviceId);
			return sts;
		} catch(PersistenceException e) {
			throw new BankingException("No records found");
		}

	}
}
