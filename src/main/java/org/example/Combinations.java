package org.example;

public enum Combinations {
    ROYAL_FLASH(10),
    STREET_FLASH(9),
    QUAD(8),
    FULL_HOUSE(7),
    FLASH(6),
    STREET(5),
    SET(4),
    TWO_PAIRS(3),
    PAIR(2),
    ELDER_CARD(1);

    final int value;

    Combinations(int value) {
        this.value = value;
    }
}
