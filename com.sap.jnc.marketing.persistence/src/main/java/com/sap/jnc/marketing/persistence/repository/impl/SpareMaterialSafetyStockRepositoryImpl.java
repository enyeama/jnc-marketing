package com.sap.jnc.marketing.persistence.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.sap.jnc.marketing.persistence.model.InTransitStockQuantity;
import com.sap.jnc.marketing.persistence.model.PositionView;
import com.sap.jnc.marketing.persistence.model.PositionView_;
import com.sap.jnc.marketing.persistence.model.Product;
import com.sap.jnc.marketing.persistence.model.Product_;
import com.sap.jnc.marketing.persistence.model.SafetyStockQuantity;
import com.sap.jnc.marketing.persistence.model.SpareMaterialSafetyStock;
import com.sap.jnc.marketing.persistence.model.SpareMaterialSafetyStock_;
import com.sap.jnc.marketing.persistence.model.TotalDeliveredQuantity;
import com.sap.jnc.marketing.persistence.model.TotalDifferenceQuantity;
import com.sap.jnc.marketing.persistence.model.TotalPaidQuantity;
import com.sap.jnc.marketing.persistence.model.TotalVerifiedQuantity;
import com.sap.jnc.marketing.persistence.repository.PositionViewRepository;
import com.sap.jnc.marketing.persistence.repository.ProductRepository;
import com.sap.jnc.marketing.persistence.repository.SpareMaterialSafetyStockRepository;

/**
 * @author Marco, Huang I323691
 */

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SpareMaterialSafetyStockRepositoryImpl extends SimpleJpaRepository<SpareMaterialSafetyStock, Long> implements SpareMaterialSafetyStockRepository {

	private static final String SAFE_STOCK_UNIT = "瓶";
	private static final String MATERIAL_ID_NOT_EXIST = "物料编号不存在";
	private static final String POSITION_ID_NOT_EXIST = "岗位编号不存在";
	private static final String RECORD_NOT_EXIST = "该条记录在数据库不存在";
	private static final String DUPLICATE_MATERIAL_POSITION_RECORD = "相同物料编号，岗位编号记录在安全库存表中已存在,表记录重复.";

	/*
	 * 传输给service layer的包含错误信息的Map KEY-VALUE: "errorFound"-responseMsg
	 */
	Map<String, List<String>> dbOperationResult = null;

	// 往DB插入数据的响应信息，最终该信息会回传给service层
	public List<String> responseMsg = null;

	// 错误标识符，任何错误信息被找到将被设置为true
	public boolean errorFound = false;
	/*
	 * 往DB插入数据的List集合，批量数据库存储
	 */
	private List<String> operationList = null;
	private List<String> positionIdList = null;
	private List<String> materialIdList = null;
	private List<String> safeStockQuList = null;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PositionViewRepository positionViewRepository;

	/*
	 * @Autowired private EmployeeRepository employeeRepository;
	 */

	@Autowired
	public SpareMaterialSafetyStockRepositoryImpl(EntityManager em) {
		super(SpareMaterialSafetyStock.class, em);
	}

	// 记录查询materialId， positionId结果
	public Map<String, String> queryIdMap = new HashMap<String, String>();

	/**
	 * 查询所有安全库存
	 **/
	@Override
	@Transactional(readOnly = true)
	public List<SpareMaterialSafetyStock> queryAllSpareMaterials() {
		return super.findAll((root, query, cb) -> {
			/*
			 * // TODO performance issue n+1 query.distinct(true); //Fetches root.fetch(SpareMaterialSafetyStock_.createBy, JoinType.LEFT);
			 * root.fetch(SpareMaterialSafetyStock_.updateBy, JoinType.LEFT); root.fetch(SpareMaterialSafetyStock_.product,
			 * JoinType.LEFT).fetch(Product_.productGroup, JoinType.LEFT); //Order query.orderBy(cb.asc(root.join(SpareMaterialSafetyStock_.product,
			 * JoinType.LEFT).get(Product_.id)), cb.asc(root.join( SpareMaterialSafetyStock_.position, JoinType.LEFT).get(PositionView_.id))); return
			 * cb.isNotNull(root.get(SpareMaterialSafetyStock_.id));
			 */
			Join<SpareMaterialSafetyStock, Product> joinProduct = root.join(SpareMaterialSafetyStock_.product, JoinType.LEFT);
			Join<SpareMaterialSafetyStock, PositionView> joinPostionView = root.join(SpareMaterialSafetyStock_.position, JoinType.LEFT);
			query.orderBy(cb.asc(joinProduct.get(Product_.id)), cb.asc(joinPostionView.get(PositionView_.id)));
			return cb.isNotNull(root.get(SpareMaterialSafetyStock_.id));
		});
	}

	/**
	 * 查询安全库存入口
	 **/
	@Override
	@Transactional(readOnly = true)
	public List<SpareMaterialSafetyStock> querySpareMaterials(final String positionId, final String materialId) {
		// 如果岗位编号为空
		if (StringUtils.isEmpty(positionId)) {
			// 如果物料编号同时也为空
			if (StringUtils.isEmpty(materialId)) {
				return this.queryAllSpareMaterials();
			}
			else {
				return this.querySpareMaterialByMaterialId(materialId);
			}
		}
		else {
			if (StringUtils.isEmpty(materialId)) {
				return this.querySpareMaterialByPositionId(positionId);
			}
			else {
				return this.queryByPositionIdAndMaterialId(positionId, materialId);
			}
		}
	}

	/**
	 * 根据物料编号查询所有安全库存
	 **/
	@Override
	@Transactional(readOnly = true)
	public List<SpareMaterialSafetyStock> querySpareMaterialByMaterialId(final String materialId) {
		return super.findAll((root, query, cb) -> {
			Join<SpareMaterialSafetyStock, Product> joinProduct = root.join(SpareMaterialSafetyStock_.product, JoinType.LEFT);
			Join<SpareMaterialSafetyStock, PositionView> joinPostionView = root.join(SpareMaterialSafetyStock_.position, JoinType.LEFT);
			query.orderBy(cb.asc(joinPostionView.get(PositionView_.id)));
			return cb.equal(joinProduct.get(Product_.id), materialId);
		});

	}

	/**
	 * 根据岗位编号查询所有安全库存 按照物料编号升序排序
	 **/
	@Override
	@Transactional(readOnly = true)
	public List<SpareMaterialSafetyStock> querySpareMaterialByPositionId(final String positionId) {
		return super.findAll((root, query, cb) -> {
			Join<SpareMaterialSafetyStock, Product> joinProduct = root.join(SpareMaterialSafetyStock_.product, JoinType.LEFT);
			Join<SpareMaterialSafetyStock, PositionView> join = root.join(SpareMaterialSafetyStock_.position, JoinType.LEFT);
			query.orderBy(cb.asc(joinProduct.get(Product_.id)));
			return cb.equal(join.get(PositionView_.id), positionId);
		});
	}

	/**
	 * 根据岗位编号和物料编号查询所有安全库存 按照岗位编号，物料编号升序排序
	 **/
	@Override
	@Transactional(readOnly = true)
	public List<SpareMaterialSafetyStock> queryByPositionIdAndMaterialId(String positionId, String materialId) {
		return super.findAll((root, query, cb) -> {
			Join<SpareMaterialSafetyStock, Product> joinProduct = root.join(SpareMaterialSafetyStock_.product, JoinType.LEFT);
			Join<SpareMaterialSafetyStock, PositionView> joinPostionView = root.join(SpareMaterialSafetyStock_.position, JoinType.LEFT);
			query.orderBy(cb.asc(joinProduct.get(Product_.id)), cb.asc(joinPostionView.get(PositionView_.id)));
			return cb.and(cb.equal(joinPostionView.get(PositionView_.id), positionId), cb.equal(joinProduct.get(Product_.id), materialId));
		});
	}

	/**
	 * 根据岗位编号，物料编号查询单个安全库存
	 **/
	@Override
	@Transactional(readOnly = true)
	public SpareMaterialSafetyStock querySafetyStockByPositionIdAndMaterialId(String positionId, String materialId) {
		return super.findOne((root, query, cb) -> {
			Join<SpareMaterialSafetyStock, Product> joinProduct = root.join(SpareMaterialSafetyStock_.product, JoinType.LEFT);
			Join<SpareMaterialSafetyStock, PositionView> joinPostionView = root.join(SpareMaterialSafetyStock_.position, JoinType.LEFT);
			query.orderBy(cb.asc(joinProduct.get(Product_.id)), cb.asc(joinPostionView.get(PositionView_.id)));
			return cb.and(cb.equal(joinPostionView.get(PositionView_.id), positionId), cb.equal(joinProduct.get(Product_.id), materialId));
		});
	}

	/**
	 * 根据岗位编号，物料编号,安全库存数查询单个安全库存
	 **/
	@Override
	@Transactional(readOnly = true)
	public SpareMaterialSafetyStock querySafetyStockByPositionIdAndMaterialIdAndSafeStockQu(String positionId, String materialId, String safeStockQuan) {
		SafetyStockQuantity safetyStockQuantity = new SafetyStockQuantity();
		safetyStockQuantity.setUnit(SAFE_STOCK_UNIT);
		// TODO validation check.
		safetyStockQuantity.setValue(BigDecimal.valueOf(Long.valueOf(safeStockQuan)));
		return super.findOne((root, query, cb) -> {
			root.fetch(SpareMaterialSafetyStock_.product, JoinType.LEFT);
			root.fetch(SpareMaterialSafetyStock_.position, JoinType.LEFT);
			return cb.and(cb.equal(root.get(SpareMaterialSafetyStock_.position).get(PositionView_.id), positionId), cb.equal(root.get(
				SpareMaterialSafetyStock_.product).get(Product_.id), materialId), cb.equal(root.get(SpareMaterialSafetyStock_.safetyStockQuantity),
				safetyStockQuantity));
		});
	}

	/**
	 * 根据岗位编号，物料编号，安全库存数量，操作特征符执行数据库操作入口 得到service层传入的4个List集合数据，进行第二步骤的业务逻辑数据删选 a. 判断传入的4个List集合数据长度是否一致 (理论上应当一致) b.
	 * 统一循环遍历4个List记录，取出4个List的同一索引值,准备对4个List中每个相同索引的值进行操作 c. 判断岗位编号，物料编号数据库中是否存在 失败： 1> 发现两个主数据任意一个不存在，记录出错信息到List<String> responseMsg 2> 将错误信息标识符
	 * errorFound 设为false 3> 扫描4个List的下条同一索引数据，统计出错信息 4> 扫描结束后，不进行任何真实数据插入 成功： 1> 4个List集合数据长度相同 2> 循环遍历4个List同一索引值记录，按照操作特征符进行数据库操作 3> 出现异常，全部回滚 4>
	 * 将错误信息标识符 errorFound 设为false 5> 记录出错信息到List<String> responseMsg 6> 将错误信息返回到service层 没有任何错误信息，返回success给service层
	 **/
	@Override
	public Map<String, List<String>> uploadSafeStockDataIntoDB(List<String> operationList, List<String> positionIdList, List<String> materialIdList,
		List<String> safeStockQuList) {
		// 对service层过来的数据进行业务校验,同时准备安全数据,若有错误，直接返回responseMsg给service层
		this.errorFound = false;
		this.operationList = new ArrayList<String>();
		this.positionIdList = new ArrayList<String>();
		this.materialIdList = new ArrayList<String>();
		this.safeStockQuList = new ArrayList<String>();
		this.responseMsg = new ArrayList<String>();
		this.dbOperationResult = new HashMap<String, List<String>>();

		this.prepareSafeStockDataForDB(operationList, positionIdList, materialIdList, safeStockQuList);
		if (this.errorFound) {
			this.dbOperationResult.put("errorFound", responseMsg);
			return dbOperationResult;
		}
		// 通过上一步骤的所有数据已经被拆分存入4个List，这里执行真实的数据库操作
		this.doRealDBOperation(operationList, positionIdList, materialIdList, safeStockQuList);
		if (this.errorFound) {
			this.dbOperationResult.put("errorFound", responseMsg);
			return dbOperationResult;
		}
		return this.dbOperationResult;
	}

	/**
	 * 准备可以插入到数据库的安全数据
	 **/
	public void prepareSafeStockDataForDB(List<String> operationList, List<String> positionIdList, List<String> materialIdList,
		List<String> safeStockQuList) {
		for (int index = 0; index < operationList.size(); index++) {
			// 取得操作符,物料编号,岗位编号,安全库存数
			String operationType = operationList.get(index);
			String positionId = positionIdList.get(index);
			String materialId = materialIdList.get(index);
			String safeStockQuan = safeStockQuList.get(index);

			switch (operationType) {
			// TODO static constant
			case "C":
				this.prepareInsertSafeStockData(operationType, positionId, materialId, safeStockQuan, index);
				break;
			case "U":
				this.prepareUpdateAndDeleteSafeStockData(operationType, positionId, materialId, safeStockQuan, index);
				break;
			case "D":
				this.prepareUpdateAndDeleteSafeStockData(operationType, positionId, materialId, safeStockQuan, index);
				break;
			}
		}
	}

	/**
	 * a. 查询岗位编号,物料编号是否存在 b. 如果存在，将物料编号，岗位编号查询结果插入缓存 c. 查询物料编号，岗位编号，都相同的记录是否存在 c. 将健康数据插入4个List: operationList, positionIdList, materialIdList,
	 * safeStockQuList
	 **/
	@Override
	@Transactional(readOnly = true)
	public void prepareInsertSafeStockData(String operationType, String positionId, String materialId, String safetyStockQuantity, int index) {

		StringBuffer sb = new StringBuffer();
		if (!this.queryMaterialIdExists(materialId)) {
			// TODO static constant
			sb.append(MATERIAL_ID_NOT_EXIST);
			if (!this.queryPositionIdExists(positionId)) {
				sb.append(POSITION_ID_NOT_EXIST);
			}
			this.errorFound = true;
			this.responseMsg.add(this.createResponseMsg(operationType, positionId, materialId, safetyStockQuantity, index, sb.toString()));
			return;
		}
		/*
		 * 查询物料编号，岗位编号，都相同的记录是否存在
		 */
		List<SpareMaterialSafetyStock> safetyStock = this.queryByPositionIdAndMaterialId(positionId, materialId);
		if (!CollectionUtils.isEmpty(safetyStock)) {
			// TODO
			sb.append(DUPLICATE_MATERIAL_POSITION_RECORD);
			this.errorFound = true;
			this.responseMsg.add(this.createResponseMsg(operationType, positionId, materialId, safetyStockQuantity, index, sb.toString()));
			return;
		}

		/*
		 * 如果物料编号，岗位编号存在 那么该记录是一条有效记录 将该记录存入有效记录列表: operationList/ positionIdList/ materialIdList/ safeStockQuantityList
		 */
		this.operationList.add(operationType);
		this.positionIdList.add(positionId);
		this.materialIdList.add(materialId);
		this.safeStockQuList.add(safetyStockQuantity);
	}

	/**
	 * 准备往4个List中插入更新和删除操作的数据库语句
	 **/
	private void prepareUpdateAndDeleteSafeStockData(String operationType, String positionId, String materialId, String safetyStockQuantity, int index) {

		StringBuffer sb = new StringBuffer();
		if (!this.queryMaterialIdExists(materialId)) {
			sb.append(MATERIAL_ID_NOT_EXIST);
			if (!this.queryPositionIdExists(positionId)) {
				sb.append(POSITION_ID_NOT_EXIST);
			}
			this.errorFound = true;
			this.responseMsg.add(this.createResponseMsg(operationType, positionId, materialId, safetyStockQuantity, index, sb.toString()));
			return;
		}
		this.operationList.add(operationType);
		this.positionIdList.add(positionId);
		this.materialIdList.add(materialId);
		this.safeStockQuList.add(safetyStockQuantity);
	}

	/**
	 * 真实场景数据库批量操作 a. 从4个List中提取相同索引项的记录 b. 根据operationType执行数据库操作
	 **/
	@Override
	public void doRealDBOperation(List<String> operationList, List<String> positionIdList, List<String> materialIdList, List<String> safeStockQuList) {
		for (int index = 0; index < operationList.size(); index++) {
			// 取得操作符,物料编号,岗位编号,安全库存数
			String operationType = operationList.get(index);
			String positionId = positionIdList.get(index);
			String materialId = materialIdList.get(index);
			String safeStockQuan = safeStockQuList.get(index);

			switch (operationType) {
			case "C":
				this.insertSafeStockDataInDB(positionId, materialId, safeStockQuan, index);
				break;
			case "U":
				this.updateSafeStockDataInDB(operationType, positionId, materialId, safeStockQuan, index);
				break;
			case "D":
				this.deleteSafeStockDataInDB(operationType, positionId, materialId, safeStockQuan, index);
				break;
			}
		}
	}

	/**
	 * 真实的数据库插入操作
	 **/
	@Override
	public void insertSafeStockDataInDB(String positionId, String materialId, String safeStockQuan, int index) {
		// 准备安全库存表其他列数据
		SpareMaterialSafetyStock safetyStock = new SpareMaterialSafetyStock();
		// 时间戳
		// Calendar currentTime = Calendar.getInstance();
		// 在途库存
		InTransitStockQuantity inTransitStockQuantity = new InTransitStockQuantity();
		inTransitStockQuantity.setUnit(SAFE_STOCK_UNIT);
		inTransitStockQuantity.setValue(BigDecimal.ZERO);
		safetyStock.setInTransitStockQuantity(inTransitStockQuantity);
		// 安全库存
		SafetyStockQuantity safetyStockQuantity = new SafetyStockQuantity();
		safetyStockQuantity.setUnit(SAFE_STOCK_UNIT);
		safetyStockQuantity.setValue(new BigDecimal(Long.valueOf(safeStockQuan)));
		safetyStock.setSafetyStockQuantity(safetyStockQuantity);
		// 累计发放数
		TotalDeliveredQuantity totalDeliveredQuantity = new TotalDeliveredQuantity();
		totalDeliveredQuantity.setUnit(SAFE_STOCK_UNIT);
		totalDeliveredQuantity.setValue(BigDecimal.ZERO);
		safetyStock.setTotalDeliveredQuantity(totalDeliveredQuantity);
		// 累计核销差异数
		TotalDifferenceQuantity totalDifferenceQuantity = new TotalDifferenceQuantity();
		totalDifferenceQuantity.setUnit(SAFE_STOCK_UNIT);
		totalDifferenceQuantity.setValue(BigDecimal.ZERO);
		safetyStock.setTotalDifferenceQuantity(totalDifferenceQuantity);
		// 累计兑付数
		TotalPaidQuantity totalPaidQuantity = new TotalPaidQuantity();
		totalPaidQuantity.setUnit(SAFE_STOCK_UNIT);
		totalPaidQuantity.setValue(BigDecimal.ZERO);
		safetyStock.setTotalPaidQuantity(totalPaidQuantity);
		// 累计核销
		TotalVerifiedQuantity totalVerifiedQuantity = new TotalVerifiedQuantity();
		totalVerifiedQuantity.setUnit(SAFE_STOCK_UNIT);
		totalVerifiedQuantity.setValue(BigDecimal.ZERO);
		safetyStock.setTotalVerifiedQuantity(totalVerifiedQuantity);

		Product spareMaterial = productRepository.findOne(materialId);
		PositionView positionView = positionViewRepository.findOne(positionId);
		// Position position = positionRepository.findOne(positionView.getId());

		safetyStock.setPosition(positionView);
		safetyStock.setProduct(spareMaterial);
		super.saveAndFlush(safetyStock);

		safetyStock = null;
	}

	/**
	 * 真实的数据库更新操作 安全库存数量改变，只有需补库存受影响 a. 查找物料编号，岗位编号的安全库存数据库记录 b. 计算 当前库存 = 累计发放数 - 累计兑付数 + 累计核销差异数 - 在途库存 c. 计算 需补库存= 安全库存 - 在途库存 - 当前库存 d. 更新需补库存
	 * 
	 * @param positionId
	 * @param materialId
	 * @param safetyStockQuantity
	 * @param index
	 */
	@Override
	// TODO
	public void updateSafeStockDataInDB(String operationType, String positionId, String materialId, String safetyStockQuan, int index) {
		StringBuffer sb = new StringBuffer();
		SpareMaterialSafetyStock safetyStock = this.querySafetyStockByPositionIdAndMaterialId(positionId, materialId);
		if (safetyStock == null) {
			this.errorFound = true;
			sb.append(RECORD_NOT_EXIST);
			this.responseMsg.add(this.createResponseMsg(operationType, positionId, materialId, safetyStockQuan, index, sb.toString()));
			return;
		}
		// 设置安全库存
		SafetyStockQuantity safetyStockQuantity = new SafetyStockQuantity();
		safetyStockQuantity.setUnit(SAFE_STOCK_UNIT);
		safetyStockQuantity.setValue(BigDecimal.valueOf(Long.valueOf(safetyStockQuan)));
		safetyStock.setSafetyStockQuantity(safetyStockQuantity);
		// 更新数据
		this.saveAndFlush(safetyStock);
		safetyStock = null;

	}

	/**
	 * 用上传的安全库存数据在数据库中找到相应记录删除 a. 查找相同物料编号，岗位编号，安全库存数量的记录 b. 执行删除操作
	 **/
	@Override
	public void deleteSafeStockDataInDB(String operationType, String positionId, String materialId, String safetyStockQuantity, int index) {
		StringBuffer sb = new StringBuffer();
		SpareMaterialSafetyStock safetyStock = this.querySafetyStockByPositionIdAndMaterialId(positionId, materialId);
		if (safetyStock == null) {
			this.errorFound = true;
			sb.append(RECORD_NOT_EXIST);
			this.responseMsg.add(this.createResponseMsg(operationType, positionId, materialId, safetyStockQuantity, index, sb.toString()));
			return;
		}
		this.delete(safetyStock.getId());
	}

	/**
	 * 查询主数据中物料编号是否存在 a. 如果没有 被缓存过，执行一次新的查询 b. 如果被缓存过，从缓存中拿最新的值
	 **/
	@Override
	@Transactional(readOnly = true)
	public boolean queryMaterialIdExists(String materialId) {
		if (queryIdMap.get(materialId) == null) {
			// 执行新的查询
			boolean materialIdExists = productRepository.exists(materialId);
			if (materialIdExists) {
				queryIdMap.put(materialId, "exist");
			}
			else {
				queryIdMap.put(materialId, "NotExist");
			}
			return materialIdExists;
		}
		else if ("exist".equals(queryIdMap.get(materialId))) {
			return true;
		}
		return false;

	}

	/**
	 * 查询主数据中岗位编号是否存在 a. 如果没有 被缓存过，执行一次新的查询 b. 如果被缓存过，从缓存中拿最新的值
	 **/
	@Override
	@Transactional(readOnly = true)
	public boolean queryPositionIdExists(String positionId) {

		if (queryIdMap.get(positionId) == null) {
			// 执行新的查询
			boolean positionIdExists = positionViewRepository.exists(positionId);
			if (positionIdExists) {
				queryIdMap.put(positionId, "exist");
			}
			else {
				queryIdMap.put(positionId, "NotExist");
			}
			return positionIdExists;
		}
		else if ("exist".equals(queryIdMap.get(positionId))) {
			return true;
		}
		return false;

	}

	// 创建响应Message
	public String createResponseMsg(String operationType, String positionId, String materialId, String safetyStockQuantity, int index, String errorMsg) {
		StringBuffer result = new StringBuffer();
		result.append("{");
		result.append("\"rowNumber\":" + "\"" + String.valueOf(index + 1) + "\",");
		result.append("\"operation\":" + "\"" + operationType + "\",");
		result.append("\"positionId\":" + "\"" + positionId + "\",");
		result.append("\"materialId\":" + "\"" + materialId + "\",");
		result.append("\"safetyStockQuantity\":" + "\"" + safetyStockQuantity + "\",");
		result.append("\"errorMsg\":" + "\"" + errorMsg + "\"");
		result.append("}");
		return result.toString();
	}

	// 根据岗位externalId和物料编号查看安全库存
	@Override
	public SpareMaterialSafetyStock queryByPositionExternalIdAndMaterialId(String positionExternalId, String materialId) {
		return super.findOne((root, query, cb) -> {
			Join<SpareMaterialSafetyStock, Product> joinProduct = root.join(SpareMaterialSafetyStock_.product, JoinType.LEFT);
			Join<SpareMaterialSafetyStock, PositionView> joinPostionView = root.join(SpareMaterialSafetyStock_.position, JoinType.LEFT);

			return cb.and(cb.equal(joinPostionView.get(PositionView_.externalId), positionExternalId), cb.equal(joinProduct.get(Product_.id),
				materialId));
		});
	}

	@Override
	public List<SpareMaterialSafetyStock> findAllMaterialsByPositionExternalId(String positionExternalId) {
		return super.findAll((root, query, cb) -> {
			Join<SpareMaterialSafetyStock, Product> joinProduct = root.join(SpareMaterialSafetyStock_.product, JoinType.LEFT);
			Join<SpareMaterialSafetyStock, PositionView> joinPostionView = root.join(SpareMaterialSafetyStock_.position, JoinType.LEFT);
			Subquery<String> sbquery = query.subquery(String.class);
			Root<Product> subRoot = sbquery.from(Product.class);

			sbquery.distinct(true);
			sbquery.select(subRoot.get(Product_.id));

			return cb.and(cb.equal(joinPostionView.get(PositionView_.externalId), positionExternalId), cb.in(joinProduct.get(Product_.id)).value(sbquery));
		});
	}
}
