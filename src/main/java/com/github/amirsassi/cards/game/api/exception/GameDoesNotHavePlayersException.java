package com.github.amirsassi.cards.game.api.exception;

public class GameDoesNotHavePlayersException
    extends Exception {

    private static final String GAME_DOES_NOT_HAVE_PLAYERS = "Game %d does not have players";

    public GameDoesNotHavePlayersException(final Integer gameId) {

        super(String.format(GAME_DOES_NOT_HAVE_PLAYERS, gameId));

    }

}
