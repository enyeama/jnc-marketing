package com.sap.jnc.marketing.service.ka.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.jnc.marketing.dto.response.contact.ContactResponse;
import com.sap.jnc.marketing.dto.shared.gps.GPSNode;
import com.sap.jnc.marketing.dto.shared.terminal.TerminalNode;
import com.sap.jnc.marketing.persistence.enumeration.TerminalStatus;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.Contact;
import com.sap.jnc.marketing.persistence.model.GPS;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.ChannelRepository;
import com.sap.jnc.marketing.persistence.repository.ContactRepository;
import com.sap.jnc.marketing.persistence.repository.DepartmentViewRepository;
import com.sap.jnc.marketing.persistence.repository.KARepository;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.RegionRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.exception.CommonServiceException;
import com.sap.jnc.marketing.service.exception.validation.SpecifiedEntityNotFoundException;
import com.sap.jnc.marketing.service.ka.KAService;

import me.chanjar.weixin.common.util.StringUtils;

@Service
@Transactional
public class KAServiceImpl implements KAService {

	public static final Logger logger = LoggerFactory.getLogger(KAServiceImpl.class);

	// errors
	private final static String INVALID_INPUT = "Input data is empty or the terminal id is null.";

	@Autowired
	private DepartmentViewRepository departmentViewRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private TerminalRepository terminalRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ChannelRepository channelRepository;

	@Qualifier("kARepositoryImpl")
	KARepository kaRepository;

	@Override
	public void disableKA(Long id) {
		Terminal terminal = this.terminalRepository.findOne(id);
		if (terminal == null || terminal.getType() != TerminalType.KA) {
			throw new SpecifiedEntityNotFoundException(Terminal.class, id);
		}
		terminal.setStatus(TerminalStatus.INACTIVE);
		this.terminalRepository.saveAndFlush(terminal);
	}

	@Override
	public TerminalNode update(TerminalNode request) {
		if (request == null || request.getId() == null || request.getId().longValue() < 1) {
			throw new CommonServiceException(INVALID_INPUT);
		}
		Terminal terminal = this.terminalRepository.findOne(request.getId());
		if (terminal == null || terminal.getType() != TerminalType.KA) {
			throw new SpecifiedEntityNotFoundException(Terminal.class, request.getId());
		}
		mergeTerminal(terminal, request);
		this.terminalRepository.saveAndFlush(terminal);
		return new TerminalNode(terminal);
	}

	private void mergeTerminal(Terminal terminal, TerminalNode request) {
		terminal.setAddress(request.getAddress());
		terminal.setBranchLevel(request.getBranchLevel());
		terminal.setBranchName(request.getBranchName());
		terminal.setBranchType(request.getBranchType());
		terminal.setBusinessLicenseName(request.getBusinessLicenseName());
		terminal.setBusinessStatus(request.getBusinessStatus());
		terminal.setCashPersonMobile(request.getCashPersonMobile());
		terminal.setCashPersonName(request.getCashPersonName());
		terminal.setCashPersonOpenId(request.getCashPersonOpenId());
		terminal.setCashPersonWechat(request.getCashPersonWechat());
		terminal.setChannelName(request.getChannelName());
		terminal.setChannelNumber(request.getChannelNumber());
		terminal.setCountyId(request.getCountyId());
		terminal.setCreationClerk(request.getCreationClerk());
		terminal.setDescription(request.getDescription());
		terminal.setExternalId(request.getExternalId());
		terminal.setGoodsReceiverWechat(request.getGoodsReceiverWechat());
		terminal.setId(request.getId());
		terminal.setInWholeSaleMarket(request.getInWholeSaleMarket());
		terminal.setIsBanquetHotel(request.getIsBanquetHotel());
		terminal.setIsRefundAllowed(request.getIsRefundAllowed());
		terminal.setKAProducts(request.getKaProducts());
		terminal.setKASystemName(request.getKaSystemName());
		terminal.setKASystemNumber(request.getKaSystemNumber());
		terminal.setKASystemPropertyName(request.getKaSystemPropertyName());
		terminal.setKASystemPropertyNumber(request.getKaSystemPropertyNumber());
		terminal.setKAType(request.getKaType());
		terminal.setLicensePictureURL(request.getLicensePictureURL());
		terminal.setPhone(request.getPhone());
		terminal.setPictureURL(request.getPictureURL());
		terminal.setRegistrantName(request.getRegistrantName());
		terminal.setStatus(request.getStatus());
		terminal.setStoreNumber(request.getStoreNumber());
		terminal.setTitle(request.getTitle());
		terminal.setType(request.getType());
		terminal.setPromoterType(request.getPromoterType());
		terminal.setPurchaserType(request.getPurchaserType());
		terminal.setWarehouseAddress(request.getWarehouseAddress());
		terminal.setBusinessContact(mergeContact(request.getBusinessContact()));
		terminal.setCashPersonContact(mergeContact(request.getCashPersonContact()));
		Long channelId = request.getChannel() != null ? request.getChannel().getId() : null;
		if (channelId != null && channelId.longValue() > 0) {
			terminal.setChannel(this.channelRepository.findOne(channelId));
		}
		else {
			terminal.setChannel(null);
		}
		terminal.setGoodsReceiverContact(mergeContact(request.getGoodsReceiverContact()));
		GPSNode gpsNode = request.getGps();
		if (gpsNode != null) {
			GPS gps = new GPS();
			gps.setAccuracy(gpsNode.getAccuracy());
			gps.setCity(gpsNode.getCity());
			gps.setCounty(gpsNode.getCounty());
			gps.setDescription(gpsNode.getDescription());
			gps.setLatitude(gpsNode.getLatitude());
			gps.setLongitude(gpsNode.getLongitude());
			gps.setProvince(gpsNode.getProvince());
			terminal.setGps(gps);
		}
		else {
			terminal.setGps(null);
		}
		String kaAccountManagerPositionExternalId = request.getKaAccountManager() != null ? request.getKaAccountManager().getPositionExternalId()
			: null;
		if (!StringUtils.isEmpty(kaAccountManagerPositionExternalId)) {
			terminal.setKAAcountManager(this.positionViewRepository.findOne(kaAccountManagerPositionExternalId));
		}
		else {
			terminal.setKAAcountManager(null);
		}
		String cityManagerPositionExternalId = request.getCityManager() != null ? request.getCityManager().getPositionExternalId() : null;
		if (!StringUtils.isEmpty(cityManagerPositionExternalId)) {
			terminal.setCityManager(this.positionViewRepository.findOne(cityManagerPositionExternalId));
		}
		else {
			terminal.setCityManager(null);
		}
		String kaOfficeExternalId = request.getKaOffice() != null ? request.getKaOffice().getExternalId() : null;
		if (!StringUtils.isEmpty(kaOfficeExternalId)) {
			terminal.setKAOffice(this.departmentViewRepository.findOne(kaOfficeExternalId));
		}
		else {
			terminal.setKAOffice(null);
		}
		terminal.setKaPurchaserContact(mergeContact(request.getKaPurchaserContact()));
		String kaSpecialistPositionExternalId = request.getKaSpecialist() != null ? request.getKaSpecialist().getPositionExternalId() : null;
		if (!StringUtils.isEmpty(kaSpecialistPositionExternalId)) {
			terminal.setKASpecialist(this.positionViewRepository.findOne(kaSpecialistPositionExternalId));
		}
		else {
			terminal.setKASpecialist(null);
		}
		terminal.setKeyUserContact(mergeContact(request.getKeyUserContact()));
		String maintainerPositionExternalId = request.getMaintainer() != null ? request.getMaintainer().getPositionExternalId() : null;
		if (!StringUtils.isEmpty(maintainerPositionExternalId)) {
			terminal.setMaintainer(this.positionViewRepository.findOne(maintainerPositionExternalId));
		}
		else {
			terminal.setMaintainer(null);
		}
		terminal.setPromoterContact(mergeContact(request.getPromoterContact()));
		terminal.setPurchaserContact(mergeContact(request.getPurchaserContact()));
		Long regionId = request.getRegion() != null ? request.getRegion().getId() : null;
		if (regionId != null && regionId.longValue() > 0) {
			terminal.setRegion(this.regionRepository.findOne(regionId));
		}
		else {
			terminal.setRegion(null);
		}
		terminal.setSupervisorContact(mergeContact(request.getSupervisorContact()));
	}

	private Contact mergeContact(ContactResponse contactResponse) {
		if (contactResponse == null) {
			return null;
		}
		Long contactId = contactResponse.getId();
		Contact contact = null;
		if (contactId == null) {
			contact = new Contact();
		}
		else {
			if (contactId.longValue() < 1) {
				return null;
			}
			contact = this.contactRepository.findOne(contactId);
			if (contact == null) {
				return null;
			}
		}
		contact.setIDCardNumber(contactResponse.getIDCardNumber());
		contact.setName(contactResponse.getName());
		contact.setPhone(contactResponse.getPhone());
		contact.setPosition(contactResponse.getPosition());
		contact.setType(contactResponse.getType());
		contact.setWechat(contactResponse.getWechat());
		contact.setWechatOpenId(contactResponse.getWechatOpenId());
		this.contactRepository.saveAndFlush(contact);
		return contact;
	}

}
