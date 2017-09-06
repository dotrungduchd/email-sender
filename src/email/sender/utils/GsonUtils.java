/*
 * Core Payment Scenarios
 * 2017 (c) VNG Corporation
 */
package email.sender.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author huy
 */
public class GsonUtils {

    private static final Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        gson = gsonBuilder.disableHtmlEscaping().create();
    }

    public static String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJsonString(String sJson, Class<T> t) {
        try {
            return gson.fromJson(sJson, t);
        } catch (Exception e) {
            return null;
        }
    }
}
