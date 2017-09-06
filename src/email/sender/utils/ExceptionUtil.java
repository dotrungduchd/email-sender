/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email.sender.utils;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 *
 * @author annv
 */
public class ExceptionUtil {

    public static String getExInfo(Exception ex) {
        return String.format("{Exception:%s -- StackTrace:%s -- RootCause:%s}",
                ex.toString(),
                ExceptionUtils.getStackTrace(ex),
                ExceptionUtils.getRootCauseMessage(ex));
    }

}
