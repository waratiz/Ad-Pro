package se233.chapter5part1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.chapter5part1.model.Keys;

import static org.junit.jupiter.api.Assertions.*;

public class KeysTest {
    private Keys keys;

    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() {
        keys = new Keys();
    }

    @Test
    public void keyPressed_givenSingleKeyIsPressed_thenStateChangesToPressed() {
        keys.add(KeyCode.W);

        assertTrue(keys.isPressed(KeyCode.W), "Key W should be registered as pressed");
    }

    @Test
    public void keyPressed_givenMultipleKeysArePressedSimultaneously_thenAllStatesArePressed() {
        keys.add(KeyCode.W);
        keys.add(KeyCode.D);

        assertTrue(keys.isPressed(KeyCode.W), "Key W should be pressed");
        assertTrue(keys.isPressed(KeyCode.D), "Key D should be pressed");
        assertFalse(keys.isPressed(KeyCode.A), "Key A should not be pressed");
    }
}