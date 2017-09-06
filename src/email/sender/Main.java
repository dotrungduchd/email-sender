/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email.sender;

import email.sender.buz.EmailSenderBuz;
import email.sender.config.AppConfig;
import email.sender.config.LogUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author ducdt
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            logger.info("##### email-sender starting...");
            LogUtil.init();

            if (!AppConfig.init()) {
                logger.info("##### LOAD CONFIG FAIL!!!!");
                System.exit(1);
            }
            
            new EmailSenderBuz().sendMail();

            logger.info("##### email-sender start SUCCESSFULLY!");
            logger.info("######################################");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }
    }

}
