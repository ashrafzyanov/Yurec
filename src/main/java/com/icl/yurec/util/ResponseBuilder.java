package com.icl.yurec.util;

import com.icl.yurec.datamodel.DataPojo;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by anton.petrov on 21.11.2017.
 */

// {"success":true, "result":[{"id":123,"result":0, "event_name":"Наименование события", "description": "Описание"},{"id":124,"result":-1, "event_name":"Наименование события", "description": "Описание"}]}
//  {"gumcid":12345,"arr":[{"id":123,"code":"hpucmdb", "type":"queue"},{"id":124,"code":"hpsm", "type":"adapter_reciver"}]}
public class ResponseBuilder {
    private static final Logger logger = Logger.getLogger(ResponseBuilder.class.getName());

    public static JSONObject createError(String reason) {
        JSONObject outputJsonObj = new JSONObject();
        try {
            outputJsonObj.append("success", false);
            outputJsonObj.append("result", reason);
        } catch (JSONException e) {
            logger.severe(e.getMessage());
        }
        return outputJsonObj;
    }

    public static JSONObject createAnswer(List<DataPojo> list, String gumcid) throws SQLException, JSONException {
        JSONObject outputJsonObj = new JSONObject();
        JSONArray outputResultArr = new JSONArray();
        for (DataPojo pojo : list) {
            outputResultArr.put(createRowAnswer(pojo, gumcid));
        }
        outputJsonObj.put("success", true);
        outputJsonObj.put("result", outputResultArr);
        return outputJsonObj;
    }

    private static JSONObject createRowAnswer(DataPojo pojo, String gumcid) throws SQLException, JSONException {
        JSONObject rowResult = new JSONObject();
        if (DBController.getInstance().getResultNumber(pojo.getCode(), pojo.numberKey(), gumcid) > 0) {
            rowResult.put("id", pojo.getId());
            rowResult.put("result", 1);
        } else {
            if (DBController.getInstance().getResultErrNumber(pojo.getCode(), gumcid) > 0) { //место пропажи инцидента
                Pair pair = DBController.getInstance().getResultEventNameAndDesctiprion(pojo.getCode(), gumcid);
                rowResult.put("id", pojo.getId());
                rowResult.put("result", -1);
                rowResult.put("event_name", pair.getEvent_name());
                rowResult.put("description", pair.getDescription());
            } else { //Узел не пройден
                rowResult.put("id", pojo.getId());
                rowResult.put("result", 0);
            }
        }
        return rowResult;
    }

}
