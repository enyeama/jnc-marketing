package com.sap.jnc.marketing.persistence.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.SpareMaterialSafetyStock;

/**
 * @author Marco, Huang I323691
 */
@NoRepositoryBean
public interface SpareMaterialSafetyStockRepository extends JpaRepository<SpareMaterialSafetyStock, Long> {
	//查询所有备用物料
	List<SpareMaterialSafetyStock> queryAllSpareMaterials();
	//查询备用物料
	List<SpareMaterialSafetyStock> querySpareMaterials(String positionId, String materialId);
	//根据物料编号查询备用物料 
	List<SpareMaterialSafetyStock> querySpareMaterialByMaterialId(String materialId);
	//根据岗位编号查询备用物料 
	List<SpareMaterialSafetyStock> querySpareMaterialByPositionId(String positionId);
	//根据岗位编号，物料编号
	List<SpareMaterialSafetyStock> queryByPositionIdAndMaterialId(String positionId, String materialId);
	//根据岗位编号，物料编号查询单条安全库存记录
	SpareMaterialSafetyStock querySafetyStockByPositionIdAndMaterialId(String positionId, String materialId);
	//根据岗位编号，物料编号，安全库存数查询单条安全库存记录
	SpareMaterialSafetyStock querySafetyStockByPositionIdAndMaterialIdAndSafeStockQu(String positionId, String materialId, String safeStockQuan);
	//根据岗位编号，物料编号，安全库存数量，操作行为更新数据库
	Map<String,List<String>> uploadSafeStockDataIntoDB(List<String> operationList, List<String> positionIdList, List<String> materialIdList,
		List<String> safeStockQuList);
	//根据materialId查询在t_product中是否存在
	boolean queryMaterialIdExists(String materialId);
	//根据positionId查询该岗位是否存在
	boolean queryPositionIdExists(String positionId);
	//根据岗位编号，物料编号，安全库存数量，准备即将插入数据库的记录
	void prepareInsertSafeStockData(String operationType, String positionId, String materialId, String safeStockQuan, int rowNumber);
	// 根据岗位externalId和物料编号查询安全库存
	SpareMaterialSafetyStock queryByPositionExternalIdAndMaterialId(String positionExternalId, String materialId);
	// 根据岗位externalId获取所有物料
	List<SpareMaterialSafetyStock> findAllMaterialsByPositionExternalId(String positionExternalId);
	
	//真实场景的数据库SQL批量操作
	void doRealDBOperation(List<String> operationList, List<String> positionIdList, List<String> materialIdList, List<String> safeStockQuList);
	//根据岗位编号，物料编号，安全库存数量执行真实的数据库插入操作
	void insertSafeStockDataInDB(String positionId, String materialId, String safeStockQuan, int index);
	//根据岗位编号，物料编号，安全库存数量执行真实的数据库更新操作
	void updateSafeStockDataInDB(String operationType, String positionId, String materialId, String safetyStockQuantity, int index);
	//根据岗位编号，物料编号，安全库存数量，删除数据库记录
	void deleteSafeStockDataInDB(String operationType, String positionId, String materialId, String safetyStockQuantity, int index);
	
}
