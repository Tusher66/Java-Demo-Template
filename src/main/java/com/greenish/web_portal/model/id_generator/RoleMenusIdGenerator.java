package com.greenish.web_portal.model.id_generator;


import com.greenish.web_portal.model.RoleMenus;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;

public class RoleMenusIdGenerator extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        // The object, that shall get a new ID is provided as `Object
        // So feel free to cast it to your own entity
        if (object instanceof RoleMenus entity) {
            String nextIdStr = entity.getRoleId() + "" + entity.getMenuId();
            return Long.parseLong(nextIdStr);
        } else {
            throw new HibernateException("RoleMenusIdGenerator: Only entities of RoleMenus are supported");
        }

    }

}
