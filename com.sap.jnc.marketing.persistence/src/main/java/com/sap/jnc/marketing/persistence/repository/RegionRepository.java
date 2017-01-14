package com.sap.jnc.marketing.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sap.jnc.marketing.persistence.model.Region;

@NoRepositoryBean
public interface RegionRepository extends JpaRepository<Region, Long> {

	Region findOneByPositionId(Long positionId);

	String findProvinceTempIdByRegionId(Long regionId);

	Long findOneByProvinceId(String provinceId);

	List<Region> findByIds(Collection<Long> ids);

	List<Region> findByCountyIds(Collection<String> countyIds);

	List<Region> findAllByPositionId(Long id);
	
	List<Region> findAllWithProvince();

	List<Region> findAllByProvinceId(String provinceId);

	List<Region> findAllByCityId(String provinceId, String cityId);

	List<Region> findAllWithProvinceAndCityAndCounty(String provinceId);

	List<Region> findAllWithCityAndCounty(String provinceId, String cityId);

	Region findAllWithCounty(String provinceId, String cityId, String countyId);

	List<Region> findByCityName(String cityName, String provinceName);

	Region findByAddress(String countyName, String cityName, String provinceName);

	Region findOneByCountyId(String countyId);
}
