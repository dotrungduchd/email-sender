/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email.sender.buz;

import email.sender.config.AppConfig;
import email.sender.entity.TicketCode;
import email.sender.utils.ExceptionUtil;
import email.sender.utils.MailUtil;
import hapax.*;
import org.apache.log4j.Logger;

/**
 * @author ducdt
 */
public class EmailSenderBuz {

    private static final Logger logger = Logger.getLogger("EmailSenderBuz");
    private final TemplateDataDictionary templateDic = TemplateDictionary.create();
    private TemplateLoader templateLoader;
    private Template template;

    public void sendMail() {

        int count = 0;

        try {

            templateLoader = TemplateResourceLoader.create("email/sender/template/", false);
            template = templateLoader.getTemplate(AppConfig.ticketConfig.template);

            for (TicketCode ticket : AppConfig.ticketConfig.ticketCodes) {

                templateDic.setVariable("FULLNAME", ticket.fullname);
                templateDic.setVariable("CODE", ticket.code);

                ticket.content = template.renderToString(templateDic);

                if (AppConfig.ticketConfig.delayMilisec > 0) {
                    Thread.sleep(AppConfig.ticketConfig.delayMilisec);
                }

                if (AppConfig.isDebug) {
                    System.out.println("ticket content=" + ticket.content);
                } else {

                    if (AppConfig.mailConfig.mailImage == null
                            || AppConfig.mailConfig.mailImage.isEmpty()) {
                        MailUtil.sendEmail(AppConfig.mailConfig, ticket.emailAddress, ticket.content);
                    } else {
                        MailUtil.sendEmailImage(AppConfig.mailConfig, ticket.emailAddress, ticket.content);
                    }
                }

                count++;
            }

            logger.info("sendMail total = " + count);

        } catch (Exception ex) {
            logger.info("sendMail count = " + count);
            logger.error("sendMail exception : " + ExceptionUtil.getExInfo(ex));
        }
    }
}
