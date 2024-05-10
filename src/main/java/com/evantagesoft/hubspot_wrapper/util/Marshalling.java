package com.evantagesoft.hubspot_wrapper.util;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Marshalling<T> {

    public static <T> T jsonStringToObjectMapper(String jsonString, Class<T> t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.readValue(jsonString, t);
    }

    public static <T> List<T> jsonStringToObjectMapperList(String jsonString, Class<List<T>> t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.readValue(jsonString, t);
    }

    public static <T> List<T> fromJsonAsList(String json, Class<T[]> clazz) {
        return Arrays.asList(new Gson().fromJson(json, clazz));
    }

    public static <T> T fromJsonAsObject(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    public static <T> String objectToJsonStringMapper(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(t);
    }


    public static <T> String requestParamListToString(List<T> listT, String varName) {
        String statusStr = "";
        if (listT != null && !listT.isEmpty()) {
            statusStr = "?" + varName + "=" + listT.get(0);
            for (int i = 1; i <= listT.size() - 1; i++) {
                statusStr += "&" + varName + "=" + listT.get(i);
            }
        }
        return statusStr;
    }

    public static String ifNullReturnEmptyString(String str) {
        return str != null ? str : "";
    }

    public static Boolean ifNullOrEmptyReturnFalse(Object obj) {
        return obj != null && !obj.equals("") ? Boolean.TRUE : Boolean.FALSE;
    }


}
