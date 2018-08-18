/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email.sender.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ducdt
 */
public class TicketConfig {

    public String filename = "";
    public String template = "";
    public int delayMilisec = 0;

    public List<TicketCode> ticketCodes = new ArrayList<>();
}
