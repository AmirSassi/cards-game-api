package com.github.amirsassi.cards.game.api.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Integer playerId;

    private String playerName;

    private List<Card> cards = new ArrayList<>();

    public Player(final Integer pPlayerId) {

        this.playerId = pPlayerId;
    }

    public Integer getPlayerId() {

        return this.playerId;
    }

    public String getPlayerName() {

        return this.playerName;
    }

    public void setPlayerId(
        final Integer pPlayerId) {

        this.playerId = pPlayerId;
    }

    public void setPlayerName(
        final String pPlayerName) {

        this.playerName = pPlayerName;
    }

    public List<Card> getCards() {

        return this.cards;
    }

    public void setCards(
        final List<Card> pCards) {

        this.cards = pCards;
    }

}
