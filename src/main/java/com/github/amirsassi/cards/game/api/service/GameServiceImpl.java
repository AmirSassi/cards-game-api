package com.github.amirsassi.cards.game.api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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

public class GameServiceImpl
    implements GameService {

    private final List<Game> games = new ArrayList<>();

    private static final Random RANDOM = new Random();

    private static final int MAX_CARDS = 52;

    @Override
    public void createGame(
        final Game pGame)
        throws GameAlreadyExistsException {

        final Optional<Game> foundedGame =
            this.games.stream().filter(game -> game.getGameId().equals(pGame.getGameId())).findFirst();

        if (foundedGame.isPresent()) {
            throw new GameAlreadyExistsException(pGame.getGameId());
        } else {
            this.games.add(pGame);
        }
    }

    @Override
    public void deleteGame(
        final Integer gameId)
        throws GameNotFoundException {

        final Game game = findGameById(gameId);
        this.games.remove(game);
    }

    @Override
    public void addPlayer(
        final Integer gameId,
        final Player player)
        throws GameNotFoundException {

        final Game game = findGameById(gameId);
        game.getPlayers().add(player);
    }

    @Override
    public void removePlayer(
        final Integer gameId,
        final Integer playerId)
        throws PlayerNotFoundException,
        GameNotFoundException {

        final Game game = findGameById(gameId);
        final List<Player> players = game.getPlayers();
        final Player player = getPlayer(players, playerId);
        players.remove(player);

    }

    @Override
    public Player getPlayer(
        final Integer gameId)
        throws GameNotFoundException,
        PlayerNotFoundException {

        final Game game = findGameById(gameId);
        return getPlayer(game.getPlayers(), gameId);
    }

    @Override
    public List<Player> getSortedPlayers(
        final Integer gameId)
        throws GameNotFoundException,
        GameDoesNotHavePlayersException {

        final Map<Player, Integer> playersWithTotalCardsValue = new HashMap<>();

        final Game game = findGameById(gameId);
        final List<Player> players = game.getPlayers();
        if (players.isEmpty()) {
            throw new GameDoesNotHavePlayersException(gameId);
        }

        for (final Player player : players) {
            final List<Card> cards = player.getCards();
            Integer value = 0;
            for (final Card card : cards) {
                value += card.getFaceValue().getValue();
                playersWithTotalCardsValue.put(player, value);
            }
        }

        final Map<Player, Integer> sortedMap = getSortedMap(playersWithTotalCardsValue);

        return new ArrayList<>(sortedMap.keySet());
    }

    @Override
    public void addDeckToGameDeck(
        final Integer gameId,
        final Deck deck)
        throws GameNotFoundException {

        final Game game = findGameById(gameId);
        game.getDecks().add(deck);
    }

    @Override
    public void dealCardsToAPlayer(
        final Integer gameId,
        final Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException {

        final Game game = findGameById(gameId);
        final Player player = getPlayer(game.getPlayers(), playerId);
        final List<Card> cards = player.getCards();
        if (cards.size() < MAX_CARDS) {
            final List<Deck> decks = game.getDecks();

            for (final Deck deck : decks) {
                final List<Card> deckCards = deck.getCards();
                if (!deckCards.isEmpty()) {
                    cards.add(deckCards.get(0));
                }
            }
            player.setCards(cards);
        }
    }

    @Override
    public List<Card> getPlayerCards(
        final Integer gameId,
        final Integer playerId)
        throws GameNotFoundException,
        PlayerNotFoundException {

        final Game game = findGameById(gameId);
        final Player player = getPlayer(game.getPlayers(), playerId);

        return player.getCards();
    }

    @Override
    public Map<Suit, Long> getUndealtCards(
        final Integer gameId)
        throws GameNotFoundException {

        final List<Card> cards = new ArrayList<>();
        final Game game = findGameById(gameId);
        final List<Deck> decks = game.getDecks();

        for (final Deck deck : decks) {
            cards.addAll(deck.getCards());
        }

        return cards.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));
    }

    @Override
    public Map<Suit, Map<FaceValue, List<Card>>> getSortedRemainingUndealtCards(
        final Integer gameId)
        throws GameNotFoundException {

        final List<Card> cards = new ArrayList<>();
        final Game game = findGameById(gameId);
        final List<Deck> decks = game.getDecks();

        for (final Deck deck : decks) {
            cards.addAll(deck.getCards());
        }

        return cards.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.groupingBy(Card::getFaceValue)));

    }

    @Override
    public void shuffle(
        final Integer gameId)
        throws GameNotFoundException {

        final Game game = findGameById(gameId);
        final List<Deck> decks = game.getDecks();

        for (final Deck deck : decks) {

            final List<Card> cards = deck.getCards();

            if (cards != null && !cards.isEmpty()) {
                final int size = cards.size();

                for (int i = 0; i < size; i++) {
                    final int newIndex = i + RANDOM.nextInt(size - i);
                    swap(cards, i, newIndex);
                }
            }
        }
    }

    private <T> void swap(
        final List<T> list,
        final int index,
        final int value) {

        final T objectContent = list.get(index);
        list.set(index, list.get(value));
        list.set(value, objectContent);
    }

    private Player getPlayer(
        final List<Player> players,
        final Integer playerId)
        throws PlayerNotFoundException {

        return players.stream().filter(player -> player.getPlayerId().equals(playerId)).findFirst()
            .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }

    public Game findGameById(
        final Integer gameId)
        throws GameNotFoundException {

        return this.games.stream().filter(game -> game.getGameId().equals(gameId)).findFirst()
            .orElseThrow(() -> new GameNotFoundException(gameId));

    }

    private Map<Player, Integer> getSortedMap(
        final Map<Player, Integer> unsortedMap) {

        final Map<Player, Integer> reverseSortedMap = new HashMap<>();

        unsortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        return reverseSortedMap;
    }

}
