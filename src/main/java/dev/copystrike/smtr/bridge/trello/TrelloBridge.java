package dev.copystrike.smtr.bridge.trello;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import dev.copystrike.smtr.core.email.Email;
import dev.copystrike.smtr.core.email.EmailEvents;
import dev.copystrike.smtr.core.email.enums.AssignmentType;
import dev.copystrike.smtr.file.TrelloConfig;

import java.util.HashMap;

/**
 * Created by Nick on 26/12/20 14:00.
 */
public class TrelloBridge implements EmailEvents {

    private final TrelloConfig trelloConfig;
    private final Board board;
    private final TrelloImpl trelloApi;
    private final TList[] tLists;

    public TrelloBridge() {
        this.trelloConfig = new TrelloConfig();
        trelloApi = new TrelloImpl(trelloConfig.getTrelloKey(), trelloConfig.getTrelloToken(), new ApacheHttpClient());
        board = trelloApi.getBoard("5fb7ae4ef9511d5839834fee");
        tLists = board.fetchLists().toArray(new TList[0]);
    }

    @Override
    public void preCycleLoop(HashMap<String, Email> emails) {

    }

    @Override
    public void cycleLoop(Email email) {

    }

    @Override
    public void onNewEmail(Email email) {

    }

    @Override
    public void onOldEmail(Email email) {

    }

    @Override
    public void onNewAssignment(Email email) {
        if (email.getAssignmentType() != AssignmentType.HOMEWORK) return;
        Card card = tLists[0].createCard(TrelloHelper.createCard(email));
        trelloApi.addLabelToCard(card.getId(), TrelloHelper.professionLabelID(email.getProfession()));
        System.out.println("New homework from class " + email.getProfession().name() + " has been added to the Trello");
    }

    @Override
    public void onNewAssignmentButOverDue(Email email) {

    }
}
