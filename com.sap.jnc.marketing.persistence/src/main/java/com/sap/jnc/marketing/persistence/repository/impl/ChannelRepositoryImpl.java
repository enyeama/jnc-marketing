package com.sap.jnc.marketing.persistence.repository.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import com.sap.jnc.marketing.persistence.model.Channel;
import com.sap.jnc.marketing.persistence.model.Channel_;
import com.sap.jnc.marketing.persistence.repository.ChannelRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChannelRepositoryImpl extends SimpleJpaRepository<Channel, Long> implements ChannelRepository {

	@Autowired
	public ChannelRepositoryImpl(EntityManager em) {
		super(Channel.class, em);
	}

	@Override
	public List<Channel> findByIds(Collection<String> channelIds) {
		return super.findAll((root, query, cb) -> {
			return root.get(Channel_.externalId).in(channelIds);
		});
	}
}
