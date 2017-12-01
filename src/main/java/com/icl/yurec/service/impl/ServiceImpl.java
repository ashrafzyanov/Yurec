package com.icl.yurec.service.impl;

import com.icl.yurec.datamodel.Answer;
import com.icl.yurec.datamodel.DataPojo;
import com.icl.yurec.service.Service;
import com.icl.yurec.util.DBController;
import com.icl.yurec.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service {

    @Override
    public List<Answer> getComponentInfo(List<DataPojo> list, String gumcid) throws Exception {
        List<Answer> result = new ArrayList<Answer>();
        for(DataPojo pojo : list) {
            if (checkNumber(pojo.getCode(), pojo.numberKey(), gumcid)) {
                result.add(new Answer(pojo.getId(), 1));
            } else {
                if (checkErrorNumber(pojo.getCode(), gumcid)) { //место пропажи инцидента
                    Pair pair = getPair(pojo.getCode(), gumcid);
                    result.add(new Answer(pojo.getId(), -1, pair.getEventName(), pair.getDescription()));
                } else { //Узел не пройден
                    result.add(new Answer(pojo.getId(), 0));
                }
            }
        }
        return result;
    }

    private boolean checkNumber(final String code, final String numberKey, final String gumcid) throws Exception {
        return DBController.getInstance().getResultNumber(code, numberKey, gumcid) > 0;
    }

    private boolean checkErrorNumber(final String code, final String gumcid) throws Exception {
        return DBController.getInstance().getResultErrNumber(code, gumcid) > 0;
    }

    private Pair getPair(final String code, final String gumcid) throws Exception {
        return DBController.getInstance().getResultEventNameAndDesctiprion(code, gumcid);
    }

}
