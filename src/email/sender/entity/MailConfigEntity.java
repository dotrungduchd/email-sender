/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email.sender.entity;

import javax.mail.Address;

/**
 *
 * @author dotrungduchd
 */
public class MailConfigEntity {

    public String mailHost = "";
    public String mailPort = "";
    public String mailFrom = "";
    public String mailFromName = "";
    public Address[] mailTo;
    public String mailSubject = "";
    public String mailContentType = "text/html; charset=utf-8";
    public String mailEnvironment = "";

}
