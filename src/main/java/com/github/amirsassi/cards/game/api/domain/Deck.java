package com.github.amirsassi.cards.game.api.domain;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private final List<Card> cards = new ArrayList<>(52);

    public Deck() {

        super();
        initCards();
    }

    private void initCards() {

        for (final Suit suit : Suit.values()) {
            for (final FaceValue faceValue : FaceValue.values()) {

                this.cards.add(new Card(suit, faceValue));

            }
        }
    }

    public List<Card> getCards() {

        return this.cards;
    }

}
