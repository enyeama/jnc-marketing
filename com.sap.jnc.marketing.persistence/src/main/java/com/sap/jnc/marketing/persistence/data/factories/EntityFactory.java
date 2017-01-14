package com.sap.jnc.marketing.persistence.data.factories;

import java.util.List;

/**
 * Define the factory to create entities for testing purposes, the initial data could be used for system initialization or fundamental data
 * collection, in order support the cases which high-depends on preconfigure data.
 *
 * @author I071053 Diouf Du
 * @param <TEntity>
 */
public interface EntityFactory<TEntity> extends AutoCloseable {

	/**
	 * Check if any data existed within factory.
	 *
	 * @return the check result, true as data existed.
	 */
	boolean doesDataExist();

	/**
	 * Get the created entities if already initialed and embed within factory itself, otherwise trigger the creation and then return the
	 * just created entities.
	 *
	 * @return the created entities.
	 */
	List<TEntity> createdEntities();

	/**
	 * Get the random entity from all created entities, it would involv the {@link getCreatedEntities} internally if entities not created.
	 *
	 * @return the random entity from all created entities
	 */
	TEntity createRandomEntity();

	/**
	 * Remove the created entities if already existed within db.
	 */
	void removeEntities();

	/**
	 * Save and refresh all created entities and update the relationship between each entity and relevant entity, normally should be called after new
	 * relationship assigned.
	 */
	void saveEntities();
}
