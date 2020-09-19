package com.github.amirsassi.cards.game.api.exception;

public class GameAlreadyExistsException
    extends Exception {

    private static final String GAME_ALREADY_EXISTS = "Game %d already exists";

    public GameAlreadyExistsException(final Integer gameId) {

        super(String.format(GAME_ALREADY_EXISTS, gameId));

    }
}
