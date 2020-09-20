package com.github.amirsassi.cards.game.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.amirsassi.cards.game.api.domain.Card;
import com.github.amirsassi.cards.game.api.domain.Deck;
import com.github.amirsassi.cards.game.api.domain.Game;
import com.github.amirsassi.cards.game.api.domain.Player;
import com.github.amirsassi.cards.game.api.domain.Suit;
import com.github.amirsassi.cards.game.api.exception.GameAlreadyExistsException;
import com.github.amirsassi.cards.game.api.exception.GameNotFoundException;
import com.github.amirsassi.cards.game.api.exception.PlayerNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GameServiceImpUnitTest {

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    public GameServiceImpUnitTest() {

        super();
    }

    @Test
    void givenGameWhenCallCreateThenGameIsCreated()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();

        // When
        this.gameServiceImpl.createGame(game);

        // Then
        assertNotNull(this.gameServiceImpl.findGameById(game.getGameId()));
    }

    @Test
    void givenGameWhenGameExistsThenGameIsNotCreated()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();

        // When
        this.gameServiceImpl.createGame(game);

        // Then
        assertThrows(GameAlreadyExistsException.class, () -> this.gameServiceImpl.createGame(game));
    }

    @Test
    void givenGameWhenDeleteGameThenTheGameIsDeleted()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();

        // When
        this.gameServiceImpl.createGame(game);
        final Integer gameId = game.getGameId();
        this.gameServiceImpl.deleteGame(gameId);

        // Then
        assertThrows(GameNotFoundException.class, () -> this.gameServiceImpl.findGameById(gameId));
    }

    @Test
    void givenGameWhenAddDeckThenDeckIsAdded()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        // When
        this.gameServiceImpl.createGame(game);
        this.gameServiceImpl.addDeckToGameDeck(game.getGameId(), new Deck());

        // Then
        final Game gameReturned = this.gameServiceImpl.findGameById(game.getGameId());
        assertTrue(!gameReturned.getDecks().isEmpty());

    }

    @Test
    void givenGameWhenAddPlayerThenPlayerIsAdded()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        final Player player = new Player(1);
        // When
        this.gameServiceImpl.createGame(game);
        this.gameServiceImpl.addPlayer(game.getGameId(), player);

        // Then
        final Game gameReturned = this.gameServiceImpl.findGameById(game.getGameId());
        assertFalse(gameReturned.getPlayers().isEmpty());
        final Player playerReturned = gameReturned.getPlayers().iterator().next();
        assertEquals(player, playerReturned);

    }

    @Test
    void givenGameWhenRemovePlayerThenPlayerIsRemoved()
        throws GameAlreadyExistsException,
        GameNotFoundException,
        PlayerNotFoundException {

        // Given
        final Game game = createGame();
        final Player player = new Player(1);
        this.gameServiceImpl.createGame(game);
        this.gameServiceImpl.addPlayer(game.getGameId(), player);

        // When
        this.gameServiceImpl.removePlayer(game.getGameId(), player.getPlayerId());

        // Then
        final Game gameReturned = this.gameServiceImpl.findGameById(game.getGameId());
        assertTrue(gameReturned.getPlayers().isEmpty());

    }

    @Test
    void givenGameAndPlayerWhenDealCardsThenPlayerReceiveCard()
        throws GameAlreadyExistsException,
        GameNotFoundException,
        PlayerNotFoundException {

        // Given
        final Game game = createGame();
        this.gameServiceImpl.createGame(game);
        this.gameServiceImpl.addDeckToGameDeck(game.getGameId(), new Deck());
        final Player player = new Player(1);
        this.gameServiceImpl.addPlayer(game.getGameId(), player);

        // When
        this.gameServiceImpl.dealCardsToAPlayer(game.getGameId(), player.getPlayerId());
        // Then
        final List<Card> cards = game.getPlayers().get(0).getCards();

        assertEquals(NumberUtils.INTEGER_ONE, cards.size());

    }

    @Test
    void givenGameAndCardsWhenGetUndealtCardsThenMapIsReturned()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        this.gameServiceImpl.createGame(game);
        this.gameServiceImpl.addDeckToGameDeck(game.getGameId(), new Deck());

        // When
        final Map<Suit, Long> undealtCards = this.gameServiceImpl.getUndealtCards(game.getGameId());

        // Then
        assertNotNull(undealtCards);
        assertFalse(undealtCards.isEmpty());
        undealtCards.entrySet().stream().forEach(entry -> {
            assertEquals(13, entry.getValue());
        });

    }

    @Test
    void givenGameAndCardsWhenShuffleThenShuffleIsCalled()
        throws GameAlreadyExistsException,
        GameNotFoundException {

        // Given
        final Game game = createGame();
        this.gameServiceImpl.createGame(game);
        this.gameServiceImpl.addDeckToGameDeck(game.getGameId(), new Deck());

        final Set<Card> set = new HashSet<>(game.getDecks().get(0).getCards());
        // When
        this.gameServiceImpl.shuffle(game.getGameId());
        // Then
        assertNotEquals(set, game.getDecks().get(0).getCards());
    }

    private Game createGame() {

        final Game game = new Game();

        game.setGameId(1);
        game.setDecks(new ArrayList<>());
        game.setPlayers(new ArrayList<>());
        return game;
    }

}
