package model.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

    @Test
    void roll() {
        assert (new Die()).roll() > 0 && (new Die()).roll() < 7;
    }
}