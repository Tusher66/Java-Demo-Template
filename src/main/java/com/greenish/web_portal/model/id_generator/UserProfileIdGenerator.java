package com.greenish.web_portal.model.id_generator;

import com.greenish.web_portal.Utils.SequenceUtil;
import com.greenish.web_portal.constant.SequenceConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserProfileIdGenerator extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        try {
            String dateTimeStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
            Long nextSequenceValue = SequenceUtil.getNextSequenceValue(session, SequenceConstant.USER_PROFILE_SEQ);

            String nextIdStr = dateTimeStr + StringUtils.leftPad(nextSequenceValue.toString(), 5, '0');
            return Long.parseLong(nextIdStr);
        } catch (Exception e) {
            throw new HibernateException("UserProfileIdGenerator: Only User Profile Sequence Supported");
        }
    }
}
