package com.icl.yurec.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created by anton.petrov on 21.11.2017.
 */
public class DBController {
    private static final Logger logger = Logger.getLogger(DBController.class.getName());

    private static DBController ourInstance = new DBController();

    private static final String expressionResultNumber = "select count(*) from eventsjournal where category_code='PROC' and component_code=? and event_number=? and message_uuid=? order by 1 desc";
    private static final String expressionResultErrNumber = "select count(*) from eventsjournal where category_code='PROC' and component_code=? and event_number in('103','104','203','303','403','503','603') and message_uuid=?";
    private static final String expressionResultErrEventNameAndDescription = "select event_name, description from eventsjournal where category_code='PROC' and component_code=? and event_number in('103','104','203','303','403','503','603') and message_uuid=?";

    private static PreparedStatement stmtResultNumber;
    private static PreparedStatement stmtResultErrNumber;
    private static PreparedStatement stmtResultErrEventNameAndDescription;

    private static Connection connection;

    private static final ConcurrentHashMap<String, String> codeMap = new ConcurrentHashMap<String, String>() {{
        put("arcsightadapter_submitter", "302");
        put("routingadapter_routing", "102");
        put("hpucmdbqueue", "100");
        put("hpucmdbadapter_reciver", "202");
        put("hpucmdbadapter_submitter", "102");
        put("hpsmqueue", "100");
        put("hpsmadapter_reciver", "102");
        put("hpsmadapter_submitter", "302");
    }};
    private DataSource dataSource;

    public static DBController getInstance() {
        return ourInstance;
    }

    private DBController() {
        InitialContext cxt = null;
        try {
            cxt = new InitialContext();
            dataSource = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");
        } catch (NamingException e) {
            dataSource = null;
        }
    }

    private void checkConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            if (dataSource == null)
                throw new SQLException("DataSource error");
            else createConnection();
        }
//        return connection == null ? dataSource == null ? null : createConnection() : connection;
    }

    private Connection createConnection() throws SQLException {
        connection = dataSource.getConnection();
        if (stmtResultNumber != null && !stmtResultNumber.isClosed())
            stmtResultNumber.close();
        stmtResultNumber = connection.prepareStatement(expressionResultNumber);

        if (stmtResultErrNumber != null && !stmtResultErrNumber.isClosed())
            stmtResultErrNumber.close();
        stmtResultErrNumber = connection.prepareStatement(expressionResultErrNumber);

        if (stmtResultErrEventNameAndDescription != null && !stmtResultErrEventNameAndDescription.isClosed())
            stmtResultErrEventNameAndDescription.close();
        stmtResultErrEventNameAndDescription = connection.prepareStatement(expressionResultErrEventNameAndDescription);
        return connection;
    }


    public int getResultNumber(String code, String numberKey, String gumcid) throws SQLException {
        checkConnection();
        logger.info("Code: " + code + " numberKey: " + numberKey + " gumcid:" + gumcid);
        ResultSet rs = null;
        try {
            String eventNumber = codeMap.get(numberKey);
            stmtResultNumber.setString(1, code);
            stmtResultNumber.setString(2, eventNumber);
            stmtResultNumber.setString(3, gumcid);
            rs = stmtResultNumber.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (rs != null)
                rs.close();
        }
    }

    public int getResultErrNumber(String code, String gumcid) throws SQLException {
        checkConnection();
        logger.info("Code: " + code + " gumcid:" + gumcid);
        ResultSet rs = null;
        try {
            stmtResultErrNumber.setString(1, code);
            stmtResultErrNumber.setString(2, gumcid);
            rs = stmtResultErrNumber.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (rs != null)
                rs.close();
        }
    }

    public Pair getResultEventNameAndDesctiprion(String code, String gumcid) throws SQLException {
        checkConnection();
        logger.info("Code: " + code + " gumcid:" + gumcid);
        ResultSet rs = null;
        try {
            stmtResultErrEventNameAndDescription.setString(1, code);
            stmtResultErrEventNameAndDescription.setString(2, gumcid);
            rs = stmtResultErrEventNameAndDescription.executeQuery();
            rs.next();
            return new Pair(rs.getString(1), rs.getString(2));
        } finally {
            if (rs != null)
                rs.close();
        }
    }

}
