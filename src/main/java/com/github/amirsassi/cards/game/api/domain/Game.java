package com.github.amirsassi.cards.game.api.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Integer gameId;

    private List<Player> players = new ArrayList<>();

    private List<Deck> decks;

    public Game() {

        super();
    }

    public Integer getGameId() {

        return this.gameId;
    }

    public List<Player> getPlayers() {

        return this.players;
    }

    public void setGameId(
        final Integer pGameId) {

        this.gameId = pGameId;
    }

    public void setPlayers(
        final List<Player> pPlayers) {

        this.players = pPlayers;
    }

    public List<Deck> getDecks() {

        return this.decks;
    }

    public void setDecks(
        final List<Deck> pDecks) {

        this.decks = pDecks;
    }

}
