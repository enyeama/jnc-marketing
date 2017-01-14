package com.sap.jnc.marketing.persistence.repository.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.persistence.repository.GeneralJpaRepository;

@SuppressWarnings("unchecked")
@Transactional
@NoRepositoryBean
public abstract class GeneralJpaRepositoryImpl<TEntity, TEntity_, TId extends Serializable> extends SimpleJpaRepository<TEntity, TId> implements GeneralJpaRepository<TEntity, TEntity_, TId> {

	protected static final String Id_MUST_NOT_BE_NULL = "The given id must not be null!";

	private JpaMetamodelEntityInformation<TEntity, TId> jpaMetamodelEntityInformation;
	private Type[] genericTypes;
	protected EntityManager sharedEntityManager;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public GeneralJpaRepositoryImpl(Class<TEntity> domainClass, EntityManager sharedEntityManager) {
		super(domainClass, sharedEntityManager);
		this.sharedEntityManager = sharedEntityManager;
	}

	/*
	 * Basic Enhancements to JPA Repository
	 */

	@Override
	@Transactional(readOnly = true)
	public Page<TEntity> findAll(Specification<TEntity> spec, Sort sort, Pageable pageable) {
		final TypedQuery<TEntity> query = this.getQuery(spec, pageable);
		return pageable == null ? new PageImpl<TEntity>(this.getQuery(spec, sort).getResultList()) : this.readPage(query, pageable, spec);
	}

	@Override
	@Transactional
	public List<TEntity> save(TEntity... entities) {
		return this.save(Arrays.asList(entities));
	}

	@Override
	@Transactional
	public List<TEntity> saveAndFlush(TEntity... entities) {
		final List<TEntity> result = this.save(Arrays.asList(entities));
		this.flush();
		return result;
	}

	@Override
	@Transactional
	public List<TEntity> saveAndFlush(Iterable<TEntity> entities) {
		final List<TEntity> result = this.save(entities);
		this.flush();
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public void refresh(TId id) {
		this.sharedEntityManager.refresh(super.findOne(id));
	}

	@Override
	@Transactional(readOnly = true)
	public void refresh(TEntity t) {
		this.sharedEntityManager.refresh(t);
	}

	@Override
	@Transactional(readOnly = true)
	public void refresh(TId... ids) {
		for (final TId id : ids) {
			this.refresh(id);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void refresh(TEntity... entities) {
		for (final TEntity entity : entities) {
			this.refresh(entity);
		}
	}

	public JpaMetamodelEntityInformation<TEntity, TId> getJpaMetamodelEntityInformation() {
		if (this.jpaMetamodelEntityInformation == null) {
			this.jpaMetamodelEntityInformation = new JpaMetamodelEntityInformation<TEntity, TId>(this.getEntityType(), this.sharedEntityManager
				.getMetamodel());
		}
		return this.jpaMetamodelEntityInformation;
	}

	/*
	 * Private & Protected Methods
	 */

	/**
	 * Get the type of entity
	 *
	 * @return
	 */
	protected Class<TEntity> getEntityType() {
		return (Class<TEntity>) this.getGenericTypes()[0];
	}

	/**
	 * Get the canonical metadata type of entity
	 *
	 * @return
	 */
	protected Class<TEntity_> getCanonicalMetadataType() {
		return (Class<TEntity_>) this.getGenericTypes()[1];
	}

	/**
	 * Get the id type of entity
	 *
	 * @return
	 */
	protected Class<TId> getIdType() {
		return (Class<TId>) this.getGenericTypes()[2];
	}

	/**
	 * Get the actual generic types from this general repository
	 *
	 * @return
	 */
	private Type[] getGenericTypes() {
		// TODO: Refactor with perfect lock
		if (this.genericTypes == null) {
			synchronized (this) {
				this.genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
			}
		}
		return this.genericTypes.clone();
	}
}
