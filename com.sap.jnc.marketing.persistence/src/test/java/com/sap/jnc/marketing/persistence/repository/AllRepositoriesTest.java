package com.sap.jnc.marketing.persistence.repository;

import com.sap.jnc.marketing.persistence.PersistenceHanaDatabaseTest;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.TypedQuery;

/**
 * @author I071053 Diouf Du
 */
public class AllRepositoriesTest extends PersistenceHanaDatabaseTest {

	@Autowired(required = false)
	private List<SimpleJpaRepository<?, ?>> allRepositories;

	@Test
	@Transactional(readOnly = true)
	@Rollback(true)
	public final void checkRepositoryAbility() {
		try {
			Method getQueryMethod = SimpleJpaRepository.class.getDeclaredMethod("getQuery", Specification.class, Sort.class);
			getQueryMethod.setAccessible(true);
			for (SimpleJpaRepository<?, ?> generalJpaRepository : allRepositories) {
				try {
					((TypedQuery<?>) getQueryMethod.invoke(generalJpaRepository, null, null)).setMaxResults(1).getResultList();
				}
				catch (DatabaseException | IllegalAccessException | InvocationTargetException e) {
					continue;
				}
			}
		}
		catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
			Assert.fail("Invalid JPA Runtime");
		}
	}
}
