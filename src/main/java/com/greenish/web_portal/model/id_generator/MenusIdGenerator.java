package com.greenish.web_portal.model.id_generator;

import com.greenish.web_portal.Utils.SequenceUtil;
import com.greenish.web_portal.model.Menus;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;

public class MenusIdGenerator extends SequenceStyleGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        // The object, that shall get a new ID is provided as `Object`
        // So feel free to cast it to your own entity
        if (object instanceof Menus entity) {

            String nextValQuery = "SELECT MAX (MENU_ID) FROM MENUS" +
                    " WHERE MAIN_MENU_ID = " + entity.getMainMenuId();

            Long nextId = SequenceUtil.getNextMaxValue(session, nextValQuery);

            if (nextId == null) {
                String newId = entity.getMainMenuId() + "" + "001";
                nextId = Long.parseLong(newId);
            }
            return nextId;
        } else {
            throw new HibernateException("CounterInfoIdGenerator: Only entities of CounterInfo are supported");
        }

    }

}
