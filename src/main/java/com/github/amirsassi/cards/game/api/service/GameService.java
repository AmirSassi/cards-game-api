package com.github.amirsassi.cards.game.api.service;

import java.util.List;
import java.util.Map;

import com.github.amirsassi.cards.game.api.domain.Card;
import com.github.amirsassi.cards.game.api.domain.Deck;
import com.github.amirsassi.cards.game.api.domain.FaceValue;
import com.github.amirsassi.cards.game.api.domain.Game;
import com.github.amirsassi.cards.game.api.domain.Player;
import com.github.amirsassi.cards.game.api.domain.Suit;
import com.github.amirsassi.cards.game.api.exception.GameAlreadyExistsException;
import com.github.amirsassi.cards.game.api.exception.GameDoesNotHavePlayersException;
import com.github.amirsassi.cards.game.api.exception.GameNotFoundException;
import com.github.amirsassi.cards.game.api.exception.PlayerNotFoundException;

/**
 * Interface for the game service
 * @author Amir.Sassi
 */
public interface GameService {

    /**
     * method to create a game
     * @param game object
     * @throws GameAlreadyExistsException if the game id already exists
     */
    void createGame(
        Game game)
        throws GameAlreadyExistsException;

    /**
     * method to delete a game
     * @param gameId the game id
     * @throws GameNotFoundException if the game doesn't exist
     */
    void deleteGame(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to add a deck to the game
     * @param gameId the game id
     * @param deck the deck object
     * @throws GameNotFoundException if the game doesn't exist
     */
    void addDeckToGameDeck(
        Integer gameId,
        Deck deck)
        throws GameNotFoundException;

    void dealCardsToAPlayer(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    /**
     * method to add a player to the game
     * @param gameId the game id
     * @param player the player object
     * @throws GameNotFoundException if the game doesn't exist
     */
    void addPlayer(
        Integer gameId,
        Player player)
        throws GameNotFoundException;

    /**
     * method to remove a player to the game
     * @param gameId the game id
     * @param playerId the player id
     * @throws GameNotFoundException if the game doesn't exist
     * @throws PlayerNotFoundException if the player doesn't exist
     */
    void removePlayer(
        Integer gameId,
        Integer playerId)
        throws PlayerNotFoundException,
        GameNotFoundException;

    /**
     * method to get sorted players
     * @param gameId the game id
     * @return a list a player
     * @throws GameNotFoundException if the game doesn't exist
     * @throws GameDoesNotHavePlayersException if the player doesn't exist
     */
    List<Player> getSortedPlayers(
        Integer gameId)
        throws GameNotFoundException,
        GameDoesNotHavePlayersException;

    /**
     * method to get a single player
     * @param gameId the game id
     * @return a single player
     * @throws GameNotFoundException if the game doesn't exist
     * @throws PlayerNotFoundException if the player doesn't exist
     */
    Player getPlayer(
        Integer gameId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    /**
     * method to return a list of player cards
     * @param gameId the game id
     * @param playerId the player id
     * @return a list of cards
     * @throws GameNotFoundException if the game doesn't exist
     * @throws PlayerNotFoundException if the player doesn't exist
     */
    List<Card> getPlayerCards(
        Integer gameId,
        Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException;

    /**
     * a method to get the undealt cards
     * @param gameId the game id
     * @return return a Map with a key suit and count of cards as value
     * @throws GameNotFoundException if the game doesn't exist
     */
    Map<Suit, Long> getUndealtCards(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to get sorted undealt cards
     * @param gameId the game id
     * @return a Map of sorted cards by suit and face value
     * @throws GameNotFoundException if the game doesn't exist
     */
    Map<Suit, Map<FaceValue, List<Card>>> getSortedRemainingUndealtCards(
        Integer gameId)
        throws GameNotFoundException;

    /**
     * method to shuffle cards in the game
     * @param gameId the game id
     * @throws GameNotFoundException if the game doesn't exist
     */
    void shuffle(
        Integer gameId)
        throws GameNotFoundException;

}
