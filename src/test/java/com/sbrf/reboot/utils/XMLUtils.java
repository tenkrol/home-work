package com.sbrf.reboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;

public class XMLUtils {
    public static String toXML(Object object) throws JsonProcessingException {
        return new XmlMapper().writeValueAsString(object);
    }

    public static Request XMLtoRequest(String json) throws JsonProcessingException {
        return new XmlMapper().readValue(json, Request.class);
    }

    public static Response XMLtoResponse(String json) throws JsonProcessingException {
        return new XmlMapper().readValue(json, Response.class);
    }
}