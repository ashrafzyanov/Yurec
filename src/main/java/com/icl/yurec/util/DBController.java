package com.icl.yurec.util;

import com.icl.yurec.dictionary.Dictionary;
import com.icl.yurec.dictionary.impl.PropertiesDictionary;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBController {

    private static DBController ourInstance = new DBController();

    private static final String expressionResultNumber = "SELECT COUNT(*) FROM eventsjournal " +
            "WHERE category_code='PROC' AND " +
            "component_code=? AND " +
            "event_number=? AND " +
            "message_uuid=? ORDER BY 1 DESC";

    private static final String expressionResultErrNumber = "SELECT COUNT(*) FROM eventsjournal " +
            "WHERE category_code='PROC' AND " +
            "component_code=? AND " +
            "event_number in('103','104','203','303','403','503','603') AND " +
            "message_uuid=?";

    private static final String expressionResultErrEventNameAndDescription = "SELECT event_name, message, description from eventsjournal " +
            "WHERE category_code='PROC' AND " +
            "component_code=? AND " +
            "event_number in('103','104','203','303','403','503','603') AND " +
            "message_uuid=?";

    private static final Dictionary CODE_MAP = new PropertiesDictionary("dictionary.properties");

    private DataSource dataSource;

    public static DBController getInstance() {
        return ourInstance;
    }

    private DBController() {
        InitialContext cxt = null;
        try {
            cxt = new InitialContext();
            dataSource = (DataSource) cxt.lookup("java:/comp/env/jdbc/db");
        } catch (NamingException e) {
        }
    }

    public int getResultNumber(String code, String numberKey, String gumcid) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement stmtResultNumber = connection.prepareStatement(expressionResultNumber);
        ResultSet rs = null;
        try {
            String eventNumber = CODE_MAP.getCode(numberKey);
            stmtResultNumber.setString(1, code);
            stmtResultNumber.setString(2, eventNumber);
            stmtResultNumber.setString(3, gumcid);
            rs = stmtResultNumber.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            closeResources(rs);
            closeResources(stmtResultNumber);
            closeResources(connection);
        }
    }

    public int getResultErrNumber(String code, String gumcid) throws SQLException {
        ResultSet rs = null;
        Connection connection = dataSource.getConnection();
        PreparedStatement stmtResultErrNumber = connection.prepareStatement(expressionResultErrNumber);
        try {
            stmtResultErrNumber.setString(1, code);
            stmtResultErrNumber.setString(2, gumcid);
            rs = stmtResultErrNumber.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            closeResources(rs);
            closeResources(stmtResultErrNumber);
            closeResources(connection);
        }
    }

    public Pair getResultEventNameAndDescription(String code, String gumcid) throws SQLException {
        ResultSet rs = null;
        Connection connection = dataSource.getConnection();
        PreparedStatement stmtResultErrEventNameAndDescription = connection.prepareStatement(expressionResultErrEventNameAndDescription);
        try {
            stmtResultErrEventNameAndDescription.setString(1, code);
            stmtResultErrEventNameAndDescription.setString(2, gumcid);
            rs = stmtResultErrEventNameAndDescription.executeQuery();
            rs.next();
            return new Pair(rs.getString(1), ((rs.getString(2) != null && rs.getString(2).length() != 0) ? rs.getString(2) : rs.getString(3)));
        } finally {
            closeResources(rs);
            closeResources(stmtResultErrEventNameAndDescription);
            closeResources(connection);
        }
    }

    private static void closeResources(final AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception ex) {
        }
    }

}
