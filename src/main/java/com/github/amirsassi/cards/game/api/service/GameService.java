package com.github.amirsassi.cards.game.api.service;

import java.util.List;
import java.util.Map;

import com.github.amirsassi.cards.game.api.domain.Card;
import com.github.amirsassi.cards.game.api.domain.Deck;
import com.github.amirsassi.cards.game.api.domain.Game;
import com.github.amirsassi.cards.game.api.domain.Player;
import com.github.amirsassi.cards.game.api.domain.Suit;
import com.github.amirsassi.cards.game.api.exception.GameAlreadyExistsException;
import com.github.amirsassi.cards.game.api.exception.GameDoesNotHavePlayersException;
import com.github.amirsassi.cards.game.api.exception.GameNotFoundException;
import com.github.amirsassi.cards.game.api.exception.PlayerNotFoundException;

public interface GameService {

    void createGame(
        Game game)
        throws GameAlreadyExistsException,
        GameNotFoundException;

    void deleteGame(
        Integer gameId)
        throws GameNotFoundException;

    void addDeckToGameDeck(
        Integer gameId,
        Deck deck)
        throws GameNotFoundException;

    void dealCardsToAPlayer(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    void addPlayer(
        Integer gameId,
        Player player)
        throws GameNotFoundException;

    void removePlayer(
        Integer gameId,
        Integer playerId)
        throws PlayerNotFoundException,
        GameNotFoundException;

    List<Player> getSortedPlayers(
        Integer gameId)
        throws GameNotFoundException,
        GameDoesNotHavePlayersException;

    Player getPlayer(
        Integer gameId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    List<Card> getPlayerCards(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    List<Card> getRmainingCards(
        Integer gameId);

    Map<Suit, Integer> getUndealtCards(
        Integer gameId)
        throws GameNotFoundException;

    Map<Card, Integer> getSortedRemainingUndealtCards(
        Integer gameId);

    void shuffle(
        Integer gameId);

}
