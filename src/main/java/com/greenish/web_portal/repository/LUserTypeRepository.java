package com.greenish.web_portal.repository;


import com.greenish.web_portal.model.lookup.LUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LUserTypeRepository extends JpaRepository<LUserType, Long> {


    /*@Query(value = "select new com.cnsbd.carwash.data.valuemap.ValueTextData(ut.userTypeId, ut.userTypeName) " +
            "from LUserType ut " +
            "where ut.activeYn = 'Y' " +
            "and (ut.userTypeId < 3 " +
            "or ut.userTypeId in (8)) " +
            "order by ut.userTypeName asc")
    List<ValueTextData> findUserTypesForUserTypeOne();*/

    @Query(value = "select v from LUserType v " +
            " where v.activeYn='Y' " +
            " and( :search is null or lower(v.userTypeName) like concat('%', lower(:search), '%') )" +
            " order by v.userTypeName asc")
    List<LUserType> getAllUserTypeRole(@Param("search") String search);


}
