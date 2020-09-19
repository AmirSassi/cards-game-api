package com.github.amirsassi.cards.game.api.exception;

public class PlayerNotFoundException
    extends Exception {

    private static final String PLAYER_NOT_FOUND = "Player %d not found";

    public PlayerNotFoundException(final Integer playerId) {

        super(String.format(PLAYER_NOT_FOUND, playerId));
    }

}
