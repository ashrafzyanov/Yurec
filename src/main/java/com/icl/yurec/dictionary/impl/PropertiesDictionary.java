package com.icl.yurec.dictionary.impl;

import com.icl.yurec.dictionary.Dictionary;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesDictionary implements Dictionary {
    private static final Logger LOG = Logger.getLogger(PropertiesDictionary.class.getName());
    private final Map<String, String> dictionary = new HashMap<>();

    public PropertiesDictionary(String name) {
        InputStream input = PropertiesDictionary.class.getResourceAsStream(String.format("/%s",name));
        Properties properties = new Properties();
        try {
            properties.load(input);
            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = (String) properties.get(key);
                dictionary.put(key, value);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public String getCode(String name) {
        return dictionary.get(name);
    }

}
