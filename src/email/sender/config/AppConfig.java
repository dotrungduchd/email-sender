/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email.sender.config;

import com.google.gson.Gson;
import email.sender.entity.MailConfigEntity;
import email.sender.entity.TicketCode;
import email.sender.entity.TicketConfig;
import email.sender.utils.ExceptionUtil;
import java.io.File;
import java.util.Scanner;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import org.apache.log4j.Logger;

/**
 *
 * @author ducdt
 */
public class AppConfig {

    private static final Logger logger = Logger.getLogger(AppConfig.class);

    //email config
    public static MailConfigEntity mailConfig = new MailConfigEntity();

    public static TicketConfig ticketConfig = new TicketConfig();

    public static boolean isDebug;

    public static Gson gson = new Gson();

    public static boolean init() {

        try {

            loadDebugConfig();

            loadMailConfig();

            loadTicketConfig();

            return true;
        } catch (Exception ex) {
            logger.error(ExceptionUtil.getExInfo(ex));
        }
        return false;
    }

    private static void loadMailConfig() throws Exception {

        loadMailConfig(mailConfig, "mailConfig");
    }

    private static void loadMailConfig(MailConfigEntity config, String mailConfigName) throws Exception {

        logger.info("########################## loadMailConfig #################");

        config.mailFrom = ConfigReader.getParam(mailConfigName, "from");
        config.mailHost = ConfigReader.getParam(mailConfigName, "host");
        config.mailPort = ConfigReader.getParam(mailConfigName, "port");
        String[] emails = ConfigReader.getParam(mailConfigName, "to").split(";");
        config.mailTo = new Address[emails.length];
        for (int i = 0; i < emails.length; i++) {
            String email = emails[i];
            config.mailTo[i] = new InternetAddress(email);
        }
        config.mailSubject = ConfigReader.getParam(mailConfigName, "subject");
        config.mailEnvironment = ConfigReader.getParam(mailConfigName, "environment");

        logger.info(" + [mailConfigEntity] " + mailConfigName + " = " + gson.toJson(config));
        logger.info("########################## loadMailConfig #################");
    }

    private static void loadTicketConfig() throws Exception {

        logger.info("########################## loadTicketConfig ###############");
        ticketConfig.filename = ConfigReader.getParam("ticket", "filename");
        ticketConfig.template = ConfigReader.getParam("ticket", "template");

        logger.info(String.format(" + [loadTicketConfig] fileName: %s", ticketConfig.filename));
        logger.info(String.format(" + [loadTicketConfig] template: %s", ticketConfig.template));

        loadTicketCode();

        logger.info("########################## loadTicketConfig ###############");
    }

    private static void loadTicketCode() throws Exception {

        logger.info("########################## loadTicketCode ################");
        Scanner scanner = new Scanner(new File(ticketConfig.filename));
        Scanner dataScanner = null;
        int index = 0;

        while (scanner.hasNextLine()) {

            dataScanner = new Scanner(scanner.nextLine());
            dataScanner.useDelimiter(",");
            TicketCode ticket = new TicketCode();

            while (dataScanner.hasNext()) {
                String data = dataScanner.next();
                switch (index) {
                    case 0:
                        ticket.fullname = data;
                        break;
                    case 1:
                        ticket.email = data;
                        if (!data.contains("@") || data.contains(" ")) {
                            throw new Exception(String.format("email invalid '%s'", data));
                        }
                        ticket.emailAddress[0] = new InternetAddress(data);
                        break;
                    case 2:
                        ticket.code = data;
                        break;
                    default:
                        throw new Exception("data invalid " + data);
                }
                index++;
            }
            index = 0;
            ticketConfig.ticketCodes.add(ticket);
        }

        scanner.close();

        for (TicketCode ticket : ticketConfig.ticketCodes) {
//            System.out.println(String.format("'%s'", ticket.fullname));
//            System.out.println(String.format("'%s'", ticket.email));
//            System.out.println(String.format("'%s'", ticket.code));
//            System.out.println(String.format("%s\t\t\t%s\t\t%s", ticket.fullname, ticket.email, ticket.code));
//            System.out.println(gson.toJson(ticket));
        }

        System.out.println("count=" + ticketConfig.ticketCodes.size());
        logger.info("########################## loadTicketCode ################");
    }

    private static void loadDebugConfig() throws Exception {

        logger.info("########################## loadDebugConfig ###############");
        isDebug = Boolean.parseBoolean(ConfigReader.getParam("debug", "isDebug"));

        logger.info(String.format(" + [loadDebugConfig] isDebug: %s", isDebug));
        logger.info("########################## loadDebugConfig ###############");
    }
}
