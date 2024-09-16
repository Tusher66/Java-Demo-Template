package com.greenish.web_portal.model.id_generator;

import com.greenish.web_portal.model.UserTypeRole;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;

public class UserTypeRoleIdGenerator extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        // The object, that shall get a new ID is provided as `Object`
        // So feel free to cast it to your own entity
        if (object instanceof UserTypeRole entity) {
            String nextIdStr = entity.getUserTypeId() + "" + StringUtils.leftPad(entity.getRoleId().toString(), 3, '0');
            return Long.parseLong(nextIdStr);
        } else {
            throw new HibernateException("UserTypeRoleIdGenerator: Only entities of UserTypeRole are supported");
        }

    }

}
