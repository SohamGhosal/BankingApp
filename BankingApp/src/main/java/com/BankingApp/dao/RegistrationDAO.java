package com.BankingApp.dao;

import com.BankingApp.dto.AccountMaster;
import com.BankingApp.dto.BankUser;
import com.BankingApp.dto.Customer;
import com.BankingApp.dto.CustomerRequests;
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

@Repository("registrationDAO")
@Transactional
@Slf4j
public class RegistrationDAO implements IRegistrationDAO {
	@PersistenceContext
	EntityManager em;
	@Override
	public boolean registerUser(CustomerRequests cr) throws BankingException {
		boolean res=false;
		try
		{
			em.persist(cr);
			res=true;
			return res;
		}
		catch(PersistenceException e) {
			throw new BankingException("Registration Failed");
		}
	}
	@Override
	public List<CustomerRequests> getCustReqList() throws BankingException {
		TypedQuery<CustomerRequests> query=em.createQuery
				(QueryMapper.getRequests,CustomerRequests.class);
		query.setParameter("status","Open");
		try {
			List<CustomerRequests> custReqList=query.getResultList();
			if(custReqList.size()>0)
				return custReqList;
			else
				throw new BankingException("Customer List is Empty");
		} catch(PersistenceException e) {
			throw new BankingException("Customer List is Empty");
		}
	}
	@Override
	public CustomerRequests getCustReq(Integer customerReqId) throws BankingException {
		try {
			CustomerRequests custReq=em.getReference(CustomerRequests.class, customerReqId);
			return custReq;
		} catch(PersistenceException e) {
			throw new BankingException("Request Not found");
		}
	}
	@Override
	public boolean insertCustomer(AccountMaster acc, BankUser user, Customer cust, CustomerRequests custReq) throws BankingException{
		try
		{
			em.persist(acc);
			em.persist(user);
			em.persist(cust);
			em.merge(custReq);
			log.info("Registration is Sucessful");
			return true;
		}
		catch(PersistenceException e) {
			throw new BankingException("Insertion Failed");
		}


	}

}
