package com.sap.jnc.marketing.service.migration;

import com.sap.jnc.marketing.dto.response.migration.DealerMigrationResponse;

public interface DealerImportService {
	//上传经销商数据
	DealerMigrationResponse uploadDealer(String name, byte[] bytes);
}
