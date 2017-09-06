/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email.sender.entity;

import javax.mail.Address;

/**
 *
 * @author ducdt
 */
public class TicketCode {

    public String fullname = "";
    public String email = "";
    public Address[] emailAddress = new Address[1];
    public String code = "";
    public String content = "";

}
