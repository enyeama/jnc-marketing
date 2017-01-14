package com.sap.jnc.marketing.service.terminal.impl;

import com.sap.jnc.marketing.dto.request.register.RegisterRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.sap.jnc.marketing.infrastructure.shared.constant.Constants;
import com.sap.jnc.marketing.infrastructure.shared.utils.ImgUtils;
import com.sap.jnc.marketing.service.config.migration.UploadFile;
import com.sap.jnc.marketing.service.exception.CommonServiceException;
import com.sap.jnc.marketing.service.qingcloud.QingCloudService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sap.jnc.marketing.dto.request.terminal.TerminalRequest;
import com.sap.jnc.marketing.dto.request.verification.UserVerificationRequest;
import com.sap.jnc.marketing.infrastructure.shared.SystemConfig;
import com.sap.jnc.marketing.persistence.enumeration.*;
import com.sap.jnc.marketing.persistence.model.*;
import com.sap.jnc.marketing.persistence.repository.*;
import com.sap.jnc.marketing.persistence.criteria.ka.KAAdvanceSearchKeywordNode;
import com.sap.jnc.marketing.persistence.enumeration.TerminalType;
import com.sap.jnc.marketing.persistence.model.Contact;
import com.sap.jnc.marketing.persistence.model.GPS;
import com.sap.jnc.marketing.persistence.model.Terminal;
import com.sap.jnc.marketing.persistence.repository.ChannelRepository;
import com.sap.jnc.marketing.persistence.repository.ContactRepository;
import com.sap.jnc.marketing.persistence.repository.TerminalRepository;
import com.sap.jnc.marketing.service.terminal.TerminalService;
import sun.misc.BASE64Decoder;
import sun.misc.Request;

/**
 * Created by guokai on 16/6/17.
 */

@Service
@Transactional(readOnly = true)
public class TerminalServiceImpl implements TerminalService {

	@Autowired
	Mapper beanMapper;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	TerminalRepository terminalRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ChannelRepository channelRepository;

	@Autowired
	UserVerificationRepository userVerificationRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TerminalOrderRepository terminalOrderRepository;

	@Autowired
	PositionViewRepository positionViewRepository;

	@Autowired
	RegionRepository regionRepository;

	@Autowired
	QingCloudService qingCloudService;

	@Override
	@Transactional
	public void create(TerminalRequest request) {

		// 判断termial是否存在,如存在则更新,不存在则添加
		Terminal terminal = request.getId() != null ? this.terminalRepository.findOne(request.getId()) : new Terminal();

		// 将request转化为terminal
		terminal = request.toTerminal(terminal);

		boolean isNewTerminal = false;
		if (terminal.getId() == null) {
			isNewTerminal = true;
			// 查询组织机构代码是否存在
			List<Terminal> results = terminalRepository.findAllByOrganizationCode(request.getOrganizationCode());
			if (results != null && results.size() > 0) {
				throw new CommonServiceException("组织机构代码已被使用,请确认是否填写正确。");
			}
		}
		//保存门头照片
		if (!StringUtils.isEmpty(request.getPicture())) {
			String filePath = imgSave(request);
			terminal.setPictureURL(filePath);
		}
		//保存营业执照
		if (!StringUtils.isEmpty(request.getLicensePicture())) {
			String filePath = imgSave(request);
			terminal.setLicensePictureURL(filePath);
		}
		if (terminal.getType().equals(TerminalType.HOTEL)) {
			terminal.setChannel(this.channelRepository.findOne(Constants.CHANNEL_HOTEL_ID));// 酒店
		}
		else if (terminal.getType().equals(TerminalType.SHOP)) {
			terminal.setChannel(this.channelRepository.findOne(Constants.CHANNEL_SHOP_ID));// 名酒名烟
		}

		terminal.setStatus(TerminalStatus.ACTIVE);

		Contact keyUser = terminal.getKeyUserContact() == null ? new Contact() : terminal.getKeyUserContact();
		Contact keyUserCompare = null;
		Contact cash = null;
		terminal.setCreationClerk(Constants.SALE_ACCOUNT_ID.toString());// 由于没有登录逻辑/此处暂时写死

		// 插入或更新采购人信息
		if (!StringUtils.isEmpty(request.getPurchaserName())) {
			final Contact purchase = terminal.getPurchaserContact() == null ? new Contact() : terminal.getPurchaserContact();
			purchase.setName(request.getPurchaserName());
			purchase.setPhone(request.getPurchaserPhone());
			purchase.setWechat(request.getPurchaserWechat());
			terminal.setPurchaserContact(contactRepository.saveAndFlush(purchase));

			if (TerminalKeyUserPosition.PURCHASER.equals(TerminalKeyUserPosition.labelOf(request.getKeyUserPosition()))) {
				keyUserCompare = purchase;
			}
		}

		// 插入或更新吧台主管信息
		if (!StringUtils.isEmpty(request.getSupervisorName())) {
			final Contact supervisor = terminal.getSupervisorContact() == null ? new Contact() : terminal.getSupervisorContact();
			supervisor.setName(request.getSupervisorName());
			supervisor.setPhone(request.getSupervisorPhone());
			supervisor.setWechat(request.getSupervisorWechat());
			terminal.setSupervisorContact(contactRepository.saveAndFlush(supervisor));
			if (TerminalKeyUserPosition.BARSUPERVISOR.equals(TerminalKeyUserPosition.labelOf(request.getKeyUserPosition()))) {
				keyUserCompare = supervisor;
			}
		}

		// 插入或更新业务联系人
		if (!StringUtils.isEmpty(request.getBusinessName())) {
			final Contact business = terminal.getBusinessContact() == null ? new Contact() : terminal.getBusinessContact();
			business.setName(request.getBusinessName());
			business.setPhone(request.getBusinessPhone());
			business.setWechat(request.getBusinessWechat());
			terminal.setBusinessContact(contactRepository.saveAndFlush(business));

			if (TerminalKeyUserPosition.BUSINESSCONTACT.equals(TerminalKeyUserPosition.labelOf(request.getKeyUserPosition()))) {
				keyUserCompare = business;
			}
		}

		// 插入或更新促销员
		if (!StringUtils.isEmpty(request.getPromoterName())) {
			final Contact promoter = terminal.getPromoterContact() == null ? new Contact() : terminal.getPromoterContact();
			promoter.setName(request.getPromoterName());
			promoter.setPhone(request.getPromoterPhone());
			promoter.setWechat(request.getPromoterWechat());
			terminal.setPromoterContact(contactRepository.saveAndFlush(promoter));

			if (TerminalKeyUserPosition.PROMOTER.equals(TerminalKeyUserPosition.labelOf(request.getKeyUserPosition()))) {
				keyUserCompare = promoter;
			}
		}

		// 插入或更新资源对付人
		if (!StringUtils.isEmpty(request.getCashPersonName())) {
			final Contact cashPerson = terminal.getCashPersonContact() == null ? new Contact() : terminal.getCashPersonContact();
			cashPerson.setName(request.getCashPersonName());
			cashPerson.setPhone(request.getCashPersonPhone());
			cashPerson.setWechat(request.getCashPersonWechat());
			terminal.setCashPersonContact(contactRepository.saveAndFlush(cashPerson));
			cash = cashPerson;
		}

		// 插入或更新GPS信息
		if (!StringUtils.isEmpty(request.getLatitude())) {
			final GPS gps = terminal.getGps() == null ? new GPS() : terminal.getGps();
			gps.setProvince(request.getProvince());
			gps.setAccuracy(request.getAccuracy());
			gps.setCity(request.getCity());
			gps.setCounty(request.getCountry());
			gps.setDescription(request.getGpsDescription());
			gps.setLatitude(request.getLatitude());
			gps.setLongitude(request.getLongitude());
			terminal.setGps(gps);
		}

		// 更新区县编码
		if (!StringUtils.isEmpty(request.getCountyId())) {
			Region region = regionRepository.findOneByCountyId(request.getCountyId());
			terminal.setRegion(region);
		}

		if (keyUserCompare != null) {
			keyUser.setPhone(keyUserCompare.getPhone());
			keyUser.setName(keyUserCompare.getName());
			keyUser.setWechat(keyUser.getWechat());
			terminal.setKeyUserContact(contactRepository.saveAndFlush(keyUser));
		}

		terminal = terminalRepository.saveAndFlush(terminal);

		PositionView salesman = positionViewRepository.findOne(request.getCreationClerk());
		if (isNewTerminal) {
			terminal.getSalesmen().add(salesman);
			salesman.getTerminals().add(terminal);
			terminal.setCreationClerk(salesman.getEmployees().get(0).getId().toString());
			positionViewRepository.saveAndFlush(salesman);
			terminal = terminalRepository.saveAndFlush(terminal);
		}

		// 保存关键人信息到verification
		if (keyUserCompare != null) {
			saveUserVerification(terminal, salesman, ExternalUserRoleType.KEYUSER, keyUserCompare);
		}
		if (cash != null) {
			saveUserVerification(terminal, salesman, ExternalUserRoleType.DRAWEE, cash);
		}
	}

	@Override
	public UserVerification findDetail(Long terminalId, Long id) {
		UserVerification verification = userVerificationRepository.findOne(id);
		if(!terminalId.equals(verification.getReferenceId())){
			throw new CommonServiceException("对不起您无权查看此信息!");
		}
		return verification;
	}

	@Override
	public List<Terminal> findAll() {
		return this.terminalRepository.findAll();
	}

	@Override
	public Terminal findById(Long id) {
		return this.terminalRepository.findOne(id);
	}

	@Override
	@Transactional
	public void registerUser(RegisterRequest request) {
		// 终端和终端子账号注册
		RegisterType type = RegisterType.valueOf(request.getType());
		if (RegisterType.TERMINAL.equals(type) || RegisterType.TERMINAL_ACCOUNT.equals(type)) {
			List<UserVerification> results = userVerificationRepository.findOneByPhone(request.getPhoneNumber(), request.getUserName());
			if (results != null && results.size() == 1) {
				UserVerification result = results.get(0);
				// TODO 根据role来判断权限
				ExternalUserRoleType role = result.getRoleType();
				// 插入到用户表
				User user = new User();
				UserReference reference = new UserReference();
				reference.setReferenceExternalId(result.getExternalId());
				// 判断为终端或子账号
				// TODO 完善保存用户的其他逻辑
				if (role.equals(ExternalUserRoleType.TERMINALSUBACCOUNT)) {
					reference.setReferenceType(UserReferenceType.TERMINAL);
				}
				else {
					reference.setReferenceType(UserReferenceType.TERMINAL);
				}
				user.setName(request.getUserName());
				userRepository.saveAndFlush(user);
			}
			else {
				throw new CommonServiceException("亲,未找到您录入的终端信息,请确认是否录入正确!");
			}
		}
		else {

			// TODO 内部人员注册
		}

	}

	@Override
	public List<UserVerification> findAccount(Long id) {
		return userVerificationRepository.findAllByReferenceId(id, ExternalUserRoleType.TERMINALSUBACCOUNT);
	}

	@Override
	public void accountAdd(Long id, UserVerificationRequest request) {

		UserVerificationForTerminal user = new UserVerificationForTerminal();
		if (request.getId() != null) {
			user = (UserVerificationForTerminal) userVerificationRepository.findOne(request.getId());
		}
		else {
			List<UserVerification> results = userVerificationRepository.findAllByMobile(request.getMobile());
			if (results != null && results.size() > 0) {
				throw new CommonServiceException("录入的子账号电话好吗已被使用!");
			}
		}

		if (user != null) {
			Terminal terminal = terminalRepository.findOne(id);
			user = request.toUserVerification(user);
			user.setTerminal(terminal);
			user.setStatus(UserVerificationStatus.ACTIVE);
			user.setRoleType(ExternalUserRoleType.TERMINALSUBACCOUNT);
			userVerificationRepository.saveAndFlush(user);
		}
	}

	@Override
	public Page<Terminal> advanceKASearch(KAAdvanceSearchKeywordNode searchCriteria, PageRequest pageRequest) {
		return this.terminalRepository.advanceKASearch(searchCriteria, pageRequest);
	}

	// 更新关键人Verification
	private void saveUserVerification(Terminal terminal, PositionView salesman, ExternalUserRoleType type, Contact contact) {
		List<UserVerification> results = userVerificationRepository.findAllByReferenceId(terminal.getId(), type);
		UserVerificationForTerminal verification = new UserVerificationForTerminal();
		boolean resetStatus = true;
		if (results != null && results.size() > 0) {
			verification = (UserVerificationForTerminal) results.get(0);
			if (!contact.getName().equals(verification.getName()) || !contact.getPhone().equals(verification.getMobile())) {
				resetStatus = false;
			}
		}
		verification.setName(contact.getName());
		verification.setMobile(contact.getPhone());
		verification.setWechatId(contact.getWechat());
		verification.setTerminal(terminal);
		verification.setRoleType(type);
		verification.setExternalId(salesman.getId().toString());
		if (resetStatus) {
			verification.setStatus(UserVerificationStatus.ACTIVE);
		}
		userVerificationRepository.saveAndFlush(verification);
	}

	private String imgSave(TerminalRequest request){
		BASE64Decoder decoder = new BASE64Decoder();
		//Base64解码
		try {
			byte[] b = decoder.decodeBuffer(request.getPicture());
			String filePath = "/jnctest01/" + UUID.randomUUID() + ".jpg";
			boolean success = qingCloudService.uploadFile(filePath, "image/jpeg", b);
			return filePath;
		} catch (Exception e) {
			throw new CommonServiceException("请上传正确的图片!");
		}
	}

}
