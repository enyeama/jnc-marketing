package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.Channel;

@NoRepositoryBean
public interface ChannelRepository extends JpaRepository<Channel, Long> {
	public List<Channel> findByIds(Collection<String> channelIds);
}
