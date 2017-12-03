package com.icl.yurec.dictionary;

import java.util.Map;

/**
 * Created by Rined on 03.12.2017.
 */
public interface Dictionary {
    String getCode(String name);

    Map<String, String> getDictionary();
}
