package com.greenish.web_portal.model.id_generator;

import com.greenish.web_portal.Utils.SequenceUtil;
import com.greenish.web_portal.constant.SequenceConstant;
import com.greenish.web_portal.model.UserMenuPermission;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserMenuPermissionIdGenerator extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        // The object, that shall get a new ID is provided as `Object`
        // So feel free to cast it to your own entity
        if (object instanceof UserMenuPermission entity) {
            Long nextSequenceValue = SequenceUtil.getNextSequenceValue(session, SequenceConstant.USER_MENU_PERMISSIONS_SEQ);
            String tempMenuId = entity.getMenuPermissionId() != null ? entity.getMenuPermissionId().toString() : "";
            String strDate = new SimpleDateFormat("yyMMdd").format(new Date());

            String nextIdStr = strDate + "" +
                    tempMenuId + "" + StringUtils.leftPad(nextSequenceValue.toString(), 4, '0');
            return Long.parseLong(nextIdStr);
        } else {
            throw new HibernateException("UserMenuPermissionIdGenerator: Only entities of UserMenuPermission are supported");
        }
    }

}
