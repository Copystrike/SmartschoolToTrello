package dev.copystrike.smtr.bridge.trello;

import com.julienvey.trello.domain.Card;
import dev.copystrike.smtr.core.email.Email;
import dev.copystrike.smtr.core.email.enums.Profession;

/**
 * Created by Nick on 26/12/20 15:16.
 */
public class TrelloHelper {

    /**
     * returns a card object based on email object.
     * @param email The email you want to turn into a card
     * @return the card that is made from the information of the email object
     */
    public static Card createCard(Email email){
        Card card = new Card();
        card.setName(email.getDescription().toLowerCase());
        card.setDesc(email.getFormatted());
        card.setDue(java.sql.Date.valueOf(email.getDeadline()));
        return card;
    }

    /**
     * returns the label id from trello
     * @param profession Profession
     * @return The label ID
     */
    public static String professionLabelID(Profession profession){
        switch (profession) {
            case PNEUMATIC -> {
                return "5fb7ae4ff9511d583983506e";
            }
            case MAVO -> {
                return "5fb7ae4ff9511d583983505e";
            }
            case CAD -> {
                return "5fb7b22e6a05a136ec64343a";
            }
            case TECH -> {
                return "5fb7ae4ff9511d5839835068";
            }
            case ELE -> {
                return "5fb7ae4ff9511d5839835066";
            }
            case ENGELS -> {
                return "5fe75a4b10f1604a124a374c";
            }
            case PREL -> {
                return "5fb7b1c802862b6db2be6afb";
            }
            case NEDERLANDS -> {
                return "5fe75a7b898ea0180cebc2d0";
            }
            case ECONOMIE -> {
                return "5fe75a9bf3c06a335f9d5ea3";
            }
            case PLC -> {
                return "5fb7ae4ff9511d5839835060";
            }
            case WISKUNDE -> {
                return "5fb7ae4ff9511d583983506a";
            }
            case ZEDELEER -> {
                return "5fe75acc2446961117d3105b";
            }
            case LO -> {
                return "5fe75adfac267a354f0254d1";
            }
        }
        return null;
    }
}
