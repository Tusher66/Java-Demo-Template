package com.greenish.web_portal.Utils;

import com.greenish.web_portal.constant.SequenceConstant;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceUtil {

    public static Long getNextSequenceValue(SharedSessionContractImplementor session, String seqTblName, String seqColName) {
        try (Connection connection = session.getJdbcConnectionAccess().obtainConnection()) {

            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE " + seqTblName + " SET next_val = next_val + 1");
            // Increment the sequence value
            int noOfUpdate = updateStatement.executeUpdate();

            if (noOfUpdate > 0) {
                PreparedStatement selectStatement = connection.prepareStatement(
                        "SELECT " + seqColName + " FROM " + seqTblName);
                ResultSet resultSet = selectStatement.executeQuery();

                // Retrieve the updated sequence value
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            // Handle SQLException appropriately (e.g., log, throw custom exception)
            e.printStackTrace();
        }
        return null;
    }

    public static Long getNextSequenceValue(SharedSessionContractImplementor session, String seqTblName) {
        return getNextSequenceValue(session, seqTblName, SequenceConstant.COMMON_SEQ_COLUMN_NAME);
    }


    public static Long getNextMaxValue(SharedSessionContractImplementor session, String nativeQuery) {
        try (Connection connection = session.getJdbcConnectionAccess().obtainConnection()) {

            PreparedStatement selectStatement = connection.prepareStatement(nativeQuery);
            ResultSet resultSet = selectStatement.executeQuery();

            // Retrieve the updated sequence value
            if (resultSet.next()) {
                return resultSet.getLong(1) + 1L;
            }
        } catch (SQLException e) {
            // Handle SQLException appropriately (e.g., log, throw custom exception)
            e.printStackTrace();
        }
        return null;
    }

}
