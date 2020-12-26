package dev.copystrike.smtr.forwarding.trello;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import dev.copystrike.smtr.core.email.Email;
import dev.copystrike.smtr.core.email.EmailEvents;
import dev.copystrike.smtr.file.TrelloConfig;

import java.util.HashMap;

/**
 * Created by Nick on 26/12/20 14:00.
 */
public class TrelloBridge implements EmailEvents {

    private TrelloConfig trelloConfig;
    private TrelloBridge trelloBridgeApi;
    private Board board;

    public TrelloBridge() {
        this.trelloConfig = new TrelloConfig();
        Trello trelloApi = new TrelloImpl("trelloKey", "trelloAccessToken", new ApacheHttpClient());
        board = trelloApi.getBoard("5f97feeff90d5c770517abe4");
        System.out.println(board.fetchCards());
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

    }

    @Override
    public void onNewAssignmentButOverDue(Email email) {

    }
}
