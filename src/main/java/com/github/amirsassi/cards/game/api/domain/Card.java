package com.github.amirsassi.cards.game.api.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Card {

    private Suit suit;

    private FaceValue faceValue;

    public Card(final Suit pSuit, final FaceValue pFaceValue) {

        super();
        this.suit = pSuit;
        this.faceValue = pFaceValue;
    }

    public Suit getSuit() {

        return this.suit;
    }

    public FaceValue getFaceValue() {

        return this.faceValue;
    }

    public void setSuit(
        final Suit pSuit) {

        this.suit = pSuit;
    }

    public void setFaceValue(
        final FaceValue pFaceValue) {

        this.faceValue = pFaceValue;
    }

    @Override
    public boolean equals(
        final Object other) {

        if (!(other instanceof Card)) {
            return false;
        }
        final Card castOther = (Card) other;
        return new EqualsBuilder().append(this.suit, castOther.suit).append(this.faceValue, castOther.faceValue)
            .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder().append(this.suit).append(this.faceValue).toHashCode();
    }
}
