/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email.sender.config;

import java.io.File;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author huypva
 */
public class LogUtil {

    public static boolean initialized = false;
    private static String APP_PATH = System.getProperty("apppath");
    private static String APP_ENV = System.getProperty("appenv");

    public static boolean init() {
        return init("");
    }

    public static boolean init(String apppath, String app_env) {
        APP_PATH = apppath;
        APP_ENV = app_env;
        return init("");
    }

    public static boolean init(String prefix) {
        if (initialized) {
            return true;
        }

        System.out.println("apppath=" + APP_PATH);
        System.out.println("appenv=" + APP_ENV);

        if (APP_ENV == null) {
            APP_ENV = "";
        }
        if (!"".equals(APP_ENV)) {
            APP_ENV = APP_ENV + ".";
        }

        String file = APP_PATH + File.separator + prefix + "conf" + File.separator + APP_ENV + "log4j.ini";

        System.out.println("file-log4j=" + file);
        return initFile(file);
    }

    public static boolean initFile(String file) {
        if (initialized) {
            return true;
        }
        if (new File(file).exists()) {
            PropertyConfigurator.configure(file);
            initialized = true;
        }
        return initialized;
    }
}
