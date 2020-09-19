package com.github.amirsassi.cards.game.api.exception;

public class GameNotFoundException
    extends Exception {

    private static final String GAME_NOT_FOUND = "Game %d not found";

    public GameNotFoundException(final Integer gameId) {

        super(String.format(GAME_NOT_FOUND, gameId));

    }

}
