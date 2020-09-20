package com.github.amirsassi.cards.game.api.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.amirsassi.cards.game.api.domain.Deck;
import com.github.amirsassi.cards.game.api.domain.Game;
import com.github.amirsassi.cards.game.api.domain.Player;
import com.github.amirsassi.cards.game.api.exception.GameAlreadyExistsException;
import com.github.amirsassi.cards.game.api.exception.GameDoesNotHavePlayersException;
import com.github.amirsassi.cards.game.api.exception.GameNotFoundException;
import com.github.amirsassi.cards.game.api.exception.PlayerNotFoundException;
import com.github.amirsassi.cards.game.api.service.GameService;

@RestController
@RequestMapping("/gameapi")
public class GameController {

    @Autowired
    private GameService gameService;

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    public GameController() {

        super();
    }

    /**
     * POST method to create a Game
     * @param game the game
     * @return HTTP status 201 if OK or HTTP status 400 with a message with the error
     */
    @PostMapping("/{game}")
    public ResponseEntity<String> createGame(
        @PathVariable final Game game) {

        try {
            this.gameService.createGame(game);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (final GameAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    /**
     * DELETE method to delete a Game
     * @param gameId the game id
     * @return HTTP status 204 if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @DeleteMapping("/{gameId}")
    public ResponseEntity<String> deleteGame(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.deleteGame(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * POST method to create a Deck and add it to the Game
     * @param gameId the game id
     * @return HTTP status 201 if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @PostMapping("/{gameId}/deck")
    public ResponseEntity<String> addDeck(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.addDeckToGameDeck(gameId, new Deck());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (final GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * POST method to create a Player
     * @param gameId the game id
     * @return HTTP status 201 and the object Player if OK or HTTP status 404 if the game passed is not found (with message error)
     */
    @PostMapping("/{gameId}/player")
    public ResponseEntity<?> addPlayer(
        @PathVariable final Integer gameId) {

        try {
            final Player player = new Player(this.atomicInteger.getAndIncrement());
            this.gameService.addPlayer(gameId, player);
            return ResponseEntity.status(HttpStatus.CREATED).body(player);

        } catch (final GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * DELETE method to delete a Player from the Game
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 204 if OK or HTTP status 404 if the game or the player passed is not found (with message error)
     */
    @PostMapping("/{gameId}/player/{playerId}")
    public ResponseEntity<String> deletePlayer(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {

            this.gameService.removePlayer(gameId, playerId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * PUT method to deal a card to a Player from the Game
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 200 or HTTP status 404
     */
    @PostMapping("/{gameId}/player/{playerId}/deal")
    public ResponseEntity<?> dealCards(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {

            this.gameService.dealCardsToAPlayer(gameId, playerId);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    /**
     * GET method to get player cards
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 200 or HTTP status 404
     */
    @GetMapping("/{gameId}/cards")
    public ResponseEntity<?> getPlayerCards(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getPlayerCards(gameId, playerId));
        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    /**
     * GET method to get a player
     * @param gameId the game id
     * @param playerId the player id
     * @return HTTP status 200 or HTTP status 404
     */
    @GetMapping("/{gameId}/player/{playerId}")
    public ResponseEntity<?> getPlayer(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            final Player player = this.gameService.getPlayer(gameId);
            return ResponseEntity.status(HttpStatus.OK).body(player);
        } catch (final GameNotFoundException | PlayerNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    /**
     * GET method to get all players
     * @param gameId the game id
     * @return HTTP status 200 or HTTP status 404
     */
    @GetMapping("/{gameId}/players")
    public ResponseEntity<?> getPlayers(
        @PathVariable final Integer gameId) {

        try {
            final List<Player> players = this.gameService.getSortedPlayers(gameId);
            return ResponseEntity.status(HttpStatus.OK).body(players);
        } catch (final GameDoesNotHavePlayersException | GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    /**
     * GET method to get all undealt cards
     * @param gameId the game id
     * @return HTTP status 200 or HTTP status 404
     */
    @GetMapping("/{gameId}/undealt/cards")
    public ResponseEntity<?> getUndealtCards(
        @PathVariable final Integer gameId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getUndealtCards(gameId));
        } catch (final GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    /**
     * GET method to get all remaining undealt cards
     * @param gameId the game id
     * @return HTTP status 200 or HTTP status 404
     */
    @GetMapping("/{gameId}/remaining/cards")
    public ResponseEntity<?> getSortedRemainingUndealtCards(
        @PathVariable final Integer gameId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getSortedRemainingUndealtCards(gameId));
        } catch (final GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    /**
     * POST method to shuffle all cards in a game
     * @param gameId the game id
     * @return HTTP status 204 or HTTP status 404
     */
    @PostMapping("/{gameId}/shuffle")
    public ResponseEntity<?> shuffle(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.shuffle(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final GameNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

}
