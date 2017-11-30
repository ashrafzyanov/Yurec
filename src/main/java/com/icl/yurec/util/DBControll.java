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
public class DBControll {
    private static final Logger logger = Logger.getLogger(DBControll.class.getName());
    private static DBControll ourInstance = new DBControll();
    private static final String expressionResultNumber = "select count(*) from eventsjournal where category_code='PROC' and component_code=? and event_number=? and message_uuid=? order by 1 desc";
    private static final String expressionResultErrNumber = "select count(*) from eventsjournal where category_code='PROC' and component_code=? and event_number in('103','104','203','303','403','503','603') and message_uuid=?";
    private static final String expressionResultErrEventNameAndDescription = "select event_name, description from eventsjournal where category_code='PROC' and component_code=? and event_number in('103','104','203','303','403','503','603') and message_uuid=?";

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

    public static DBControll getInstance() {
        return ourInstance;
    }

    private DBControll() {
        InitialContext cxt = null;
        try {
            cxt = new InitialContext();
            dataSource = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");
        } catch (NamingException e) {
            dataSource = null;
        }
    }

    private Connection getConnection() throws SQLException {
        return dataSource == null ? null : dataSource.getConnection();
    }

    public int getResultNumber(String code, String numberKey, String gumcid) throws SQLException {
        logger.info("Code: " + code + " numberKey: " + numberKey + " gumcid:" + gumcid);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String eventNumber = codeMap.get(numberKey);
            conn = DBControll.getInstance().getConnection();
            stmt = conn.prepareStatement(expressionResultNumber);
            stmt.setString(1, code);
            stmt.setString(2, eventNumber);
            stmt.setString(3, gumcid);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
    }

    public int getResultErrNumber(String code, String gumcid) throws SQLException {
        logger.info("Code: " + code  +  " gumcid:" + gumcid);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBControll.getInstance().getConnection();
            stmt = conn.prepareStatement(expressionResultErrNumber);
            stmt.setString(1, code);
            stmt.setString(2, gumcid);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
    }

    public Pair getResultEventNameAndDesctiprion(String code, String gumcid) throws SQLException {
        logger.info("Code: " + code +  " gumcid:" + gumcid);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBControll.getInstance().getConnection();
            stmt = conn.prepareStatement(expressionResultErrEventNameAndDescription);
            stmt.setString(1, code);
            stmt.setString(2, gumcid);
            rs = stmt.executeQuery();
            rs.next();
            return new Pair(rs.getString(1), rs.getString(2));
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
    }

}
