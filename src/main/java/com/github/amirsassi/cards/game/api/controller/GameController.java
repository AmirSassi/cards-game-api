package com.github.amirsassi.cards.game.api.controller;

import java.util.List;

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
import com.github.amirsassi.cards.game.api.service.GameService;

@RestController
@RequestMapping("/gameapi")
public class GameController {

    @Autowired
    private GameService gameService;

    public GameController() {

        super();
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<String> createGame(
        @PathVariable final Integer gameId) {

        try {
            final Game game = new Game();
            game.setGameId(gameId);
            this.gameService.createGame(game);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<String> deleteGame(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.deleteGame(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/{gameId}/deck")
    public ResponseEntity<String> addDeck(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.addDeckToGameDeck(gameId, new Deck());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/{gameId}/player")
    public ResponseEntity<?> addPlayer(
        @PathVariable final Integer gameId) {

        try {
            final Player player = new Player(1);
            this.gameService.addPlayer(gameId, player);
            return ResponseEntity.status(HttpStatus.OK).body(player);

        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/{gameId}/player/{playerId}")
    public ResponseEntity<String> deletePlayer(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {

            this.gameService.removePlayer(gameId, playerId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/{gameId}/player/{playerId}/deal")
    public ResponseEntity<?> dealCards(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {

            this.gameService.dealCardsToAPlayer(gameId, playerId);
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/{gameId}/deck")
    public ResponseEntity<?> getPlayerCards(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getPlayerCards(gameId, playerId));
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/{gameId}/player/{playerId}")
    public ResponseEntity<?> getPlayer(
        @PathVariable final Integer gameId,
        @PathVariable final Integer playerId) {

        try {
            final Player player = this.gameService.getPlayer(gameId);
            return ResponseEntity.status(HttpStatus.OK).body(player);
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/{gameId}/players")
    public ResponseEntity<?> getPlayers(
        @PathVariable final Integer gameId) {

        try {
            final List<Player> players = this.gameService.getSortedPlayers(gameId);
            return ResponseEntity.status(HttpStatus.OK).body(players);
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/{gameId}/deck")
    public ResponseEntity<?> getUndealtCards(
        @PathVariable final Integer gameId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getUndealtCards(gameId));
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/{gameId}/deck")
    public ResponseEntity<?> getSortedRemainingUndealtCards(
        @PathVariable final Integer gameId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getSortedRemainingUndealtCards(gameId));
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/{gameId}/deck/cards")
    public ResponseEntity<?> listRemaingCards(
        @PathVariable final Integer gameId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.gameService.getRmainingCards(gameId));
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @PostMapping("/{gameId}/shuffle")
    public ResponseEntity<?> shuffle(
        @PathVariable final Integer gameId) {

        try {
            this.gameService.shuffle(gameId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

}
