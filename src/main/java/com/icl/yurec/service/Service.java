package com.icl.yurec.service;

import com.icl.yurec.datamodel.Answer;
import com.icl.yurec.datamodel.DataPojo;

import java.util.List;

public interface Service {

    List<Answer> getComponentInfo(List<DataPojo> list, String gumcid) throws Exception;
}
