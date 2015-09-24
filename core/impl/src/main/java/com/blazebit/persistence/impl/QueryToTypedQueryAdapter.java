package com.blazebit.persistence.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

public class QueryToTypedQueryAdapter<X> implements TypedQuery<X> {

	private final Query delegate;

	public QueryToTypedQueryAdapter(Query delegate) {
		this.delegate = delegate;
	}
	
	private TypedQuery<X> wrapOrReturn(Query q) {
		if (q == delegate) {
			return this;
		}
		
		return new QueryToTypedQueryAdapter<X>(q);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<X> getResultList() {
		return delegate.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public X getSingleResult() {
		return (X) delegate.getSingleResult();
	}

	@Override
	public int executeUpdate() {
		return delegate.executeUpdate();
	}

	@Override
	public TypedQuery<X> setMaxResults(int maxResult) {
		return wrapOrReturn(delegate.setMaxResults(maxResult));
	}

	@Override
	public int getMaxResults() {
		return delegate.getMaxResults();
	}

	@Override
	public TypedQuery<X> setFirstResult(int startPosition) {
		return wrapOrReturn(delegate.setFirstResult(startPosition));
	}

	@Override
	public int getFirstResult() {
		return delegate.getFirstResult();
	}

	@Override
	public TypedQuery<X> setHint(String hintName, Object value) {
		return wrapOrReturn(delegate.setHint(hintName, value));
	}

	@Override
	public Map<String, Object> getHints() {
		return delegate.getHints();
	}

	@Override
	public <T> TypedQuery<X> setParameter(Parameter<T> param, T value) {
		return wrapOrReturn(delegate.setParameter(param, value));
	}

	@Override
	public TypedQuery<X> setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
		return wrapOrReturn(delegate.setParameter(param, value, temporalType));
	}

	@Override
	public TypedQuery<X> setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
		return wrapOrReturn(delegate.setParameter(param, value, temporalType));
	}

	@Override
	public TypedQuery<X> setParameter(String name, Object value) {
		return wrapOrReturn(delegate.setParameter(name, value));
	}

	@Override
	public TypedQuery<X> setParameter(String name, Calendar value, TemporalType temporalType) {
		return wrapOrReturn(delegate.setParameter(name, value, temporalType));
	}

	@Override
	public TypedQuery<X> setParameter(String name, Date value, TemporalType temporalType) {
		return wrapOrReturn(delegate.setParameter(name, value, temporalType));
	}

	@Override
	public TypedQuery<X> setParameter(int position, Object value) {
		return wrapOrReturn(delegate.setParameter(position, value));
	}

	@Override
	public TypedQuery<X> setParameter(int position, Calendar value, TemporalType temporalType) {
		return wrapOrReturn(delegate.setParameter(position, value, temporalType));
	}

	@Override
	public TypedQuery<X> setParameter(int position, Date value, TemporalType temporalType) {
		return wrapOrReturn(delegate.setParameter(position, value, temporalType));
	}

	@Override
	public Set<Parameter<?>> getParameters() {
		return delegate.getParameters();
	}

	@Override
	public Parameter<?> getParameter(String name) {
		return delegate.getParameter(name);
	}

	@Override
	public <T> Parameter<T> getParameter(String name, Class<T> type) {
		return delegate.getParameter(name, type);
	}

	@Override
	public Parameter<?> getParameter(int position) {
		return delegate.getParameter(position);
	}

	@Override
	public <T> Parameter<T> getParameter(int position, Class<T> type) {
		return delegate.getParameter(position, type);
	}

	@Override
	public boolean isBound(Parameter<?> param) {
		return delegate.isBound(param);
	}

	@Override
	public <T> T getParameterValue(Parameter<T> param) {
		return delegate.getParameterValue(param);
	}

	@Override
	public Object getParameterValue(String name) {
		return delegate.getParameterValue(name);
	}

	@Override
	public Object getParameterValue(int position) {
		return delegate.getParameterValue(position);
	}

	@Override
	public TypedQuery<X> setFlushMode(FlushModeType flushMode) {
		return wrapOrReturn(delegate.setFlushMode(flushMode));
	}

	@Override
	public FlushModeType getFlushMode() {
		return delegate.getFlushMode();
	}

	@Override
	public TypedQuery<X> setLockMode(LockModeType lockMode) {
		return wrapOrReturn(delegate.setLockMode(lockMode));
	}

	@Override
	public LockModeType getLockMode() {
		return delegate.getLockMode();
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		return delegate.unwrap(cls);
	}
}