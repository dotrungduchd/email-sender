/*
 * Core Payment Scenarios
 * 2017 (c) VNG Corporation
 */
package email.sender.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author huypva
 */
public class ConfigReader {

    private static String APP_PATH = System.getProperty("apppath");
    private static String APP_ENV = System.getProperty("appenv");
    private static String configFile = null;

    private Map<String, String> configMap = null;
    private String currentSection = "";

    private static ConfigReader instance;

    private static void loadconfig() {
        if (configFile == null) {
            if (APP_PATH == null) {
                System.out.println("Cannot find application path. Please set System property 'apppath' or using Config.setApplicationPath(your_path) to continue");
                System.exit(0);
            }
            if (APP_ENV == null) {
                APP_ENV = "";
            }
            if (!"".equals(APP_ENV)) {
                APP_ENV += ".";
            }
            configFile = APP_PATH + File.separator + "conf" + File.separator + APP_ENV + "config.ini";
        }

        instance = new ConfigReader();
    }

    public static void loadconfig(String apppath, String app_env) {
        System.setProperty("apppath", apppath);
        System.setProperty("appenv", app_env);
        APP_PATH = apppath;
        APP_ENV = app_env;

        loadconfig();
    }

    public static void loadconfig(String file) {
        configFile = file;
        instance = new ConfigReader();
    }

    public ConfigReader() {
        configMap = new HashMap<>();
        init();
    }

    private void init() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(configFile));
            String line = br.readLine();
            while (line != null) {
                privateLine(line);
                line = br.readLine();
            }
        } catch (IOException ex) {
            System.out.println("Cannot load file config: " + configFile);
            System.exit(0);
        }
    }

    private void privateLine(String line) {
        if (line == null || line.isEmpty() || line.equals("")) {
            return;
        }
        if (line.startsWith("#")) {
            return;
        }

        if (line.startsWith("[") && line.endsWith("]")) {
            currentSection = line.substring(1, line.length() - 1);
        } else {
            String[] params = line.split("=");
            if (params.length >= 2) {
                String key = currentSection + "_" + params[0];
                String value = line.substring(params[0].length() + 1, line.length());

                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                configMap.put(key, value);
            }
        }

    }

    public static String getParam(String section, String key) throws Exception {
        if (instance == null) {
            loadconfig();
        }

        String realKey = section + "_" + key;
        String value = instance.configMap.get(realKey);

        if (value == null) {
            throw new Exception(String.format("Config [%s].%s not found.", section, key));
        }

        return value;

    }

    public static String getParam(String section, String key, String valueDefault) {
        if (instance == null) {
            loadconfig();
        }

        String realKey = section + "_" + key;
        String value = instance.configMap.get(realKey);
        if (value == null) {
            value = valueDefault;
        }

        return value;

    }

    public static Map<String, String> getAll() {
        if (instance == null) {
            loadconfig();
        }

        return instance.configMap;
    }

    public static Map<String, String> getSection(String sectionName) {
        if (instance == null) {
            loadconfig();
        }

        Map<String, String> result = new HashMap<>();
        Iterator<Map.Entry<String, String>> iter = instance.configMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.startsWith(sectionName + "_")) {
                result.put(key, value);
            }
        }

        return result;
    }

}
