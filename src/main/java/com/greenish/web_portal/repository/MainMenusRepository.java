package com.greenish.web_portal.repository;


import com.greenish.web_portal.data.nativeobj.iMainMenuPermissionDetailsInfo;
import com.greenish.web_portal.data.nativeobj.iMenuPermissionDetailsInfo;
import com.greenish.web_portal.model.MainMenus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainMenusRepository extends JpaRepository<MainMenus, Long> {

    @Query(value =  """
            select master_menu.*,
                   role_menu.*,
                   (select case
                               when (active_yn = 'Y' or active_yn = 'N') then active_yn
                               else null
                           end
                      from user_menu_permissions
                     where     permission_id = 1
                           and (    menu_id = master_menu.menu_id
                                and main_menu_id = master_menu.main_menu_id)
                           and user_id = coalesce ( :user_id, user_id)
                           and user_type_id = coalesce ( :user_type_id, user_type_id))
                       user_menu_access_status
              from (  select *
                        from (select m.main_menu_id,
                                     m.main_menu_name,
                                     m.active_yn           main_menu_status,
                                     m.menu_url            main_menu_url,
                                     m.component_name      main_menu_component_name,
                                     m.visible_yn          main_menu_visibility,
                                     m.sl_no               main_menu_sl_no,
                                     m.menu_icon           main_menu_icon,
                                     sm.menu_id,
                                     sm.menu_name,
                                     sm.active_yn          menu_status,
                                     sm.visible_yn         menu_visibility,
                                     sm.part_menu_yn,
                                     sm.menu_url,
                                     sm.component_name     menu_component_name,
                                     sm.sl_no              menu_sl_no,
                                     sm.menu_icon          menu_icon,
                                     'CHILD'               as menu_type
                                from main_menus m
                               left outer join menus sm on m.main_menu_id = sm.main_menu_id
                              UNION ALL
                              SELECT m.main_menu_id,
                                     m.main_menu_name,
                                     m.active_yn          main_menu_status,
                                     m.menu_url           main_menu_url,
                                     m.component_name     main_menu_component_name,
                                     m.visible_yn         main_menu_visibility,
                                     m.sl_no              main_menu_sl_no,
                                     m.menu_icon          main_menu_icon,
                                     NULL,
                                     'N',
                                     'N',
                                     'N',
                                     'N',
                                     '/#' || m.main_menu_id,
                                     '',
                                     0,
                                     '',
                                     'PARENT'             AS menu_type
                                from main_menus m) m
                    order by main_menu_sl_no asc, menu_type desc, menu_sl_no asc)
                   master_menu
                   left join
                   (select r.role_id,
                           r.role_name,
                           coalesce (r.active_yn, 'N')      role_status,
                           coalesce (tr.active_yn, 'Y')     user_type_role_status,
                           tr.user_type_id,
                           rm.main_menu_id             role_main_menu,
                           rm.menu_id                  role_menu_id,
                           rm.active_yn                role_menu_status
                      from roles r, user_type_roles tr, role_menus rm
                     where     r.role_id = tr.role_id
                           and rm.role_id = r.role_id
                           and tr.active_yn = 'Y'
                           and user_type_id = coalesce ( :user_type_id, user_type_id))
                   role_menu
                       on master_menu.menu_id = role_menu.role_menu_id
                       order by main_menu_sl_no asc, menu_type desc , menu_sl_no asc
            """,  nativeQuery = true )
    List<iMenuPermissionDetailsInfo> getUserPermissionUsingNativeQuery(@Param("user_id") Long userId, @Param("user_type_id") Long userTypeId);


    @Query(value = """
            select *
            from (select 'CHILD'          menu_type,
                 m.visible_yn     menu_visibility,
                 ms.main_menu_id,
                 ms.main_menu_name,
                 m.menu_id,
                 m.menu_name,
                 m.menu_url,
                 rm.active_yn     as permission_wise_yn,
                 m.part_menu_yn,
                 ms.sl_no         main_menu_sl,
                 m.sl_no          menu_sl
            from menus m
                 left join main_menus ms
                     on m.main_menu_id = ms.main_menu_id and m.visible_yn = 'Y'
                 left join role_menus rm
                     on rm.menu_id = m.menu_id and rm.role_id = :role_id
                 left join main_menus mss
                     on     rm.main_menu_id = mss.main_menu_id
                        and rm.menu_id = m.menu_id
                        and mss.visible_yn = 'y'
            union all
            select 'PARENT'       menu_type,
                 visible_yn     menu_visibility,
                 main_menu_id,
                 main_menu_name,
                 null           menu_id,
                 null           menu_name,
                 menu_url,
                 'Y'            permission_wise_yn,
                 'N'            part_menu_yn,
                 sl_no          main_menu_sl,
                 NULL           menu_sl
            from main_menus) mm
            order by main_menu_sl asc, menu_type desc, menu_sl asc
            """, nativeQuery = true )
    List<iMainMenuPermissionDetailsInfo> getMainMenuPermissionUsingNativeQuery(@Param("role_id") Long roleId);

}
