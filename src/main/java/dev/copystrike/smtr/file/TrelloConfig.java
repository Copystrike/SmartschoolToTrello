package dev.copystrike.smtr.file;

/**
 * Created by Nick on 26/12/20 14:24.
 */
public class TrelloConfig extends Config {

    public TrelloConfig() {
        super("trellocredentials.json");
    }

    public String getTrelloKey(){
        return ((String) getConfig().get("trello_key"));
    }

    public String getTrelloToken(){
        return ((String) getConfig().get("trello_token"));
    }

    public String getTrelloBoardId() {
        return ((String) getConfig().get("trello_board_id"));
    }
}
