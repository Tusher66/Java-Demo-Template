package com.greenish.web_portal.repository;

import com.greenish.web_portal.model.Menus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MenuRepository extends JpaRepository<Menus, Long> {

    @Query("SELECT r FROM Menus r WHERE r.menuId IN (:menuIds)")
    List<Menus> findMenuByIds(@Param("menuIds") Set<Long> menuIds);
}
