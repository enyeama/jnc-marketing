package com.sap.jnc.marketing.persistence.data.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

public abstract class GeneralEntityFactory<TEntity> implements EntityFactory<TEntity> {

	private static final int DEFAULT_ENTITIES_SIZE = 32;
	protected static final EntityFactory<?>[] EMPTY_DEPEND_ENTITY_FACTORIES = new EntityFactory<?>[0];

	private List<TEntity> createdEntities;
	private AtomicInteger entityCount;

	protected abstract JpaRepository<TEntity, ?> getGeneralJpaRepository();

	protected abstract EntityFactory<?>[] getDependEntityFactories();

	protected abstract void fillEntitiesForInitialCreation(List<TEntity> creatingEntities);

	@Override
	@Transactional(readOnly = true)
	public synchronized boolean doesDataExist() {
		return this.getGeneralJpaRepository().count() > 0;
	}

	@Override
	@Transactional
	public synchronized List<TEntity> createdEntities() {
		// Return entities once created
		if (!CollectionUtils.isEmpty(this.createdEntities)) {
			return this.createdEntities;
		}
		if (this.doesDataExist()) {
			this.createdEntities = this.getGeneralJpaRepository().findAll();
			return this.createdEntities;
		}
		// Resolve&Create the depend entities before creation
		final EntityFactory<?>[] dependEntityFactories = this.getDependEntityFactories();
		if (!ArrayUtils.isEmpty(dependEntityFactories)) {
			for (final EntityFactory<?> dependEntityFactory : dependEntityFactories) {
				dependEntityFactory.createdEntities();
			}
		}
		// Fulfill entities and scope with current entity only
		this.close();
		this.fillEntitiesForInitialCreation(this.createdEntities);
		this.saveEntities();
		this.entityCount.set(this.createdEntities.size());
		// Update the depend entities after creation
		if (!ArrayUtils.isEmpty(dependEntityFactories)) {
			for (final EntityFactory<?> dependEntityFactory : dependEntityFactories) {
				dependEntityFactory.saveEntities();
			}
		}
		this.getGeneralJpaRepository().flush();
		return this.createdEntities;
	}

	@Override
	@Transactional
	public synchronized TEntity createRandomEntity() {
		if (CollectionUtils.isEmpty(this.createdEntities())) {
			return null;
		}
		return this.createdEntities().get(RandomUtils.nextInt(0, this.entityCount.get()));
	}

	@Override
	@Transactional
	public void saveEntities() {
		if (!CollectionUtils.isEmpty(this.createdEntities)) {
			this.createdEntities = this.getGeneralJpaRepository().save(this.createdEntities);
			this.getGeneralJpaRepository().flush();
		}
	}

	@Override
	@Transactional
	public void removeEntities() {
		this.getGeneralJpaRepository().deleteAllInBatch();
	}

	@Override
	@PostConstruct
	public void close() {
		this.createdEntities = new ArrayList<TEntity>(DEFAULT_ENTITIES_SIZE);
		this.entityCount = new AtomicInteger(0);
	}

	protected void addToAllSavedEntities(@SuppressWarnings("unchecked") TEntity... entities) {
		this.createdEntities.addAll(Arrays.asList(entities));
	}

	/**
	 * Add all saved entities into reference collection.
	 *
	 * @param entities
	 *            the saved entities
	 */
	protected void addToAllSavedEntities(Collection<TEntity> entities) {
		this.createdEntities.addAll(entities);
	}
}
