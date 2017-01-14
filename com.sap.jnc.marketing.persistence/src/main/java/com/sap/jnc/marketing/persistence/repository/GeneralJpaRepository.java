package com.sap.jnc.marketing.persistence.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author I071053 Diouf Du
 * @param <TEntity>
 *            Entity Type
 * @param <TEntity_>
 *            Entity Canonical Model Type
 * @param <TId>
 */
@SuppressWarnings("unchecked")
@NoRepositoryBean
public interface GeneralJpaRepository<TEntity, TEntity_, TId extends Serializable> extends JpaRepository<TEntity, TId> {

	/*
	 * Basic Enhancements to JPA Repository
	 */

	/**
	 * Find all the entities with both sort and pageable supported
	 *
	 * @param spec
	 * @param sort
	 * @param pageable
	 * @return
	 */
	Page<TEntity> findAll(Specification<TEntity> spec, Sort sort, Pageable pageable);

	/**
	 * Save all the entities
	 *
	 * @param entities
	 * @return
	 */
	List<TEntity> save(TEntity... entities);

	/**
	 * Save and flush all the entities
	 *
	 * @param entities
	 * @return
	 */
	List<TEntity> saveAndFlush(TEntity... entities);

	/**
	 * Save and flush all the entities
	 *
	 * @param entities
	 * @return
	 */
	List<TEntity> saveAndFlush(Iterable<TEntity> entities);

	/**
	 * Refresh specified entity by id
	 *
	 * @param id
	 */
	void refresh(TId id);

	/**
	 * Refresh specified entity
	 *
	 * @param entity
	 */
	void refresh(TEntity entity);

	/**
	 * Refresh specified entities by ids
	 *
	 * @param ids
	 */
	void refresh(TId... ids);

	/**
	 * Refresh specified entities
	 *
	 * @param entities
	 */
	void refresh(TEntity... entities);

}