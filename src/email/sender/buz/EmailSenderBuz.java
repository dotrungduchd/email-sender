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
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;
import org.apache.log4j.Logger;

/**
 *
 * @author ducdt
 */
public class EmailSenderBuz {

    private static final Logger logger = Logger.getLogger("EmailSenderBuz");
    private final TemplateDataDictionary templateDic = TemplateDictionary.create();
    private TemplateLoader templateLoader;
    private Template template;

    public void sendMail() {
        try {

            templateLoader = TemplateResourceLoader.create("email/sender/template/", false);
            template = templateLoader.getTemplate(AppConfig.ticketConfig.template);

            for (TicketCode ticket : AppConfig.ticketConfig.ticketCodes) {

//                TemplateDataDictionary ticketTemplate = templateDic.addSection("ALERT_API");
//                ticketTemplate.setVariable("FULLNAME", ticket.fullname);
//                ticketTemplate.setVariable("CODE", ticket.code);
                templateDic.setVariable("FULLNAME", ticket.fullname);
                templateDic.setVariable("CODE", ticket.code);

                templateDic.showSection("ALERT_TRANS_SUCCESS");

                ticket.content = template.renderToString(templateDic);

                if (AppConfig.isDebug) {
                    System.out.println("ticket content=" + ticket.content);
                } else {
                    MailUtil.sendEmail(AppConfig.mailConfig, ticket.emailAddress, ticket.content);
                }
            }

        } catch (Exception ex) {
            logger.error("sendMail exception : " + ExceptionUtil.getExInfo(ex));
        }
    }
}
