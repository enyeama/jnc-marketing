package com.sap.jnc.marketing.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.DealerOrderItem;

@NoRepositoryBean
public interface DealerOrderItemRepository extends JpaRepository<DealerOrderItem, Long> {

}
