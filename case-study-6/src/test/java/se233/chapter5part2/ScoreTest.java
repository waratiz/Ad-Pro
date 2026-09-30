package se233.chapter5part2;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import se233.casestudy6.controller.GameLoop;
import se233.casestudy6.model.Food;
import se233.casestudy6.model.Snake;
import se233.casestudy6.view.GameStage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    @Test
    public void testConsumingFoodAwardsOnePoint() {
        GameStage gameStage = new GameStage();
        Snake snake = new Snake(new Point2D(5, 5));

        Food food = new Food(new Point2D(5, 5));

        GameLoop gameLoop = new GameLoop(gameStage, snake, food);

        assertEquals(0, gameLoop.getScore(), "Initial score should be 0");

        gameLoop.checkCollision();

        assertEquals(1, gameLoop.getScore(), "Consuming food should award exactly 1 point");
    }
}