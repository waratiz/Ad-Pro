package se233.chapter5part1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.chapter5part1.model.GameCharacter;

import java.lang.reflect.Field; 

import static org.junit.jupiter.api.Assertions.*;

public class GameCharacterTest {
    Field xVelocityField, yVelocityField, yAccelerationField;
    private GameCharacter gameCharacter;

    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {
        });
    }

    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        gameCharacter = new GameCharacter(0, 30, 30, "assets/Character1.png", 4, 3, 2, 111, 97, KeyCode.A, KeyCode.D,
                KeyCode.W);
        xVelocityField = gameCharacter.getClass().getDeclaredField("xVelocity");
        yVelocityField = gameCharacter.getClass().getDeclaredField("yVelocity");
        yAccelerationField = gameCharacter.getClass().getDeclaredField("yAcceleration");
        xVelocityField.setAccessible(true);
        yVelocityField.setAccessible(true);
        yAccelerationField.setAccessible(true);
    }

    @Test
    public void respawn_givenNewGameCharacter_thenCoordinatesAre30_30() {
        gameCharacter.respawn();
        assertEquals(30, gameCharacter.getX(), "Initial x");
        assertEquals(30, gameCharacter.getY(), "Initial y");
    }

    @Test
    public void respawn_givenNewGameCharacter_thenScoreIs0() {
        gameCharacter.respawn();
        assertEquals(0, gameCharacter.getScore(), "Initial score");
    }

    @Test
    public void moveX_givenMoveRightOnce_thenXCoordinateIncreasedByXVelocity() throws IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveRight();
        gameCharacter.moveX();
        assertEquals(30 + xVelocityField.getInt(gameCharacter), gameCharacter.getX(), "Move right x");
    }

    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYVelocityIncreases() throws IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yVelocity1 = yVelocityField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yVelocity2 = yVelocityField.getInt(gameCharacter);
        assertTrue(yVelocity2 > yVelocity1, "Velocity is increasing");
    }

    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYAccelerationUnchanged() throws IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yAcceleration1 = yAccelerationField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yAcceleration2 = yAccelerationField.getInt(gameCharacter);
        assertTrue(yAcceleration1 == yAcceleration2, "Acceleration is not change");
    }

    @Test
    public void checkReachGameWall_givenXLessThanLeftBoundary_thenXIsRestrictedToLeftBoundary() throws NoSuchFieldException, IllegalAccessException {
        Field xField = gameCharacter.getClass().getDeclaredField("x");
        xField.setAccessible(true);
        xField.setInt(gameCharacter, -10);

        gameCharacter.checkReachGameWall();

        assertEquals(0, gameCharacter.getX(), "X coordinate restricted to left boundary");
    }

    @Test
    public void checkReachGameWall_givenXGreaterThanRightBoundary_thenXIsRestrictedToRightBoundary() throws NoSuchFieldException, IllegalAccessException {
        Field xField = gameCharacter.getClass().getDeclaredField("x");
        xField.setAccessible(true);

        int maxWidth = se233.chapter5part1.view.GameStage.WIDTH;
        xField.setInt(gameCharacter, maxWidth + 10);

        gameCharacter.checkReachGameWall();

        assertEquals(maxWidth, gameCharacter.getX(), "X coordinate restricted to right boundary");
    }

    @Test
    public void jump_givenCanJumpIsTrue_thenJumpSucceeds() throws NoSuchFieldException, IllegalAccessException {
        Field canJumpField = gameCharacter.getClass().getDeclaredField("canJump");
        Field yVelocityField = gameCharacter.getClass().getDeclaredField("yVelocity");
        Field isJumpingField = gameCharacter.getClass().getDeclaredField("isJumping");
        Field yMaxVelocityField = gameCharacter.getClass().getDeclaredField("yMaxVelocity");

        canJumpField.setAccessible(true);
        yVelocityField.setAccessible(true);
        isJumpingField.setAccessible(true);
        yMaxVelocityField.setAccessible(true);


        canJumpField.setBoolean(gameCharacter, true);
        int expectedMaxVelocity = yMaxVelocityField.getInt(gameCharacter);

        gameCharacter.jump();

        assertEquals(expectedMaxVelocity, yVelocityField.getInt(gameCharacter), "Y velocity set to max velocity");
        assertFalse(canJumpField.getBoolean(gameCharacter), "canJump becomes false");
        assertTrue(isJumpingField.getBoolean(gameCharacter), "isJumping becomes true");
    }

    @Test
    public void jump_givenCanJumpIsFalse_thenJumpNotPermitted() throws NoSuchFieldException, IllegalAccessException {
        Field canJumpField = gameCharacter.getClass().getDeclaredField("canJump");
        Field isJumpingField = gameCharacter.getClass().getDeclaredField("isJumping");

        canJumpField.setAccessible(true);
        isJumpingField.setAccessible(true);

        canJumpField.setBoolean(gameCharacter, false);
        boolean initialIsJumping = isJumpingField.getBoolean(gameCharacter);

        gameCharacter.jump();

        assertFalse(canJumpField.getBoolean(gameCharacter), "canJump remains false");
        assertEquals(initialIsJumping, isJumpingField.getBoolean(gameCharacter), "isJumping state unchanged");
    }

    @Test
    public void collided_givenHorizontalCollision_thenCharacterStopsAndPositionAdjusted() throws IllegalAccessException, NoSuchFieldException {
        GameCharacter targetCharacter = new GameCharacter(1, 100, 30, "assets/Character2.png", 4, 3, 2, 111, 97, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);

        gameCharacter.respawn();
        gameCharacter.moveRight();

        Field xField = gameCharacter.getClass().getDeclaredField("x");
        Field targetXField = targetCharacter.getClass().getDeclaredField("x");
        xField.setAccessible(true);
        targetXField.setAccessible(true);

        xField.setInt(gameCharacter, 80);
        targetXField.setInt(targetCharacter, 100);

        boolean isCollided = gameCharacter.collided(targetCharacter);

        assertFalse(isCollided , "Method executes successfully");
        assertTrue(gameCharacter.getX() <= 100, "X position is adjusted after collision");
    }

    @Test
    public void collided_givenVerticalCollision_thenScoreIncreasesAndTargetRespawns() throws IllegalAccessException, NoSuchFieldException {
        GameCharacter targetCharacter = new GameCharacter(1, 30, 100, "assets/Character2.png", 4, 3, 2, 111, 97, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);

        gameCharacter.respawn();

        Field isFallingField = gameCharacter.getClass().getDeclaredField("isFalling");
        Field yField = gameCharacter.getClass().getDeclaredField("y");
        Field targetYField = targetCharacter.getClass().getDeclaredField("y");

        isFallingField.setAccessible(true);
        yField.setAccessible(true);
        targetYField.setAccessible(true);

        isFallingField.setBoolean(gameCharacter, true);
        yField.setInt(gameCharacter, 90);
        targetYField.setInt(targetCharacter, 100);

        int initialScore = gameCharacter.getScore();

        boolean isCollided = gameCharacter.collided(targetCharacter);

        assertTrue(isCollided, "Vertical collision returns true");
        assertEquals(initialScore + 1, gameCharacter.getScore(), "Score increases by 1 upon vertical collision");
    }


}