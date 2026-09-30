package se233.chapter5part2;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import se233.casestudy5part2.controller.GameLoop;
import se233.casestudy5part2.model.Food;
import se233.casestudy5part2.model.Snake;
import se233.casestudy5part2.model.SpecialFood;
import se233.casestudy5part2.view.GameStage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialFoodTest {

    @Test
    public void testConsumingSpecialFoodAwardsFivePoints() {
        GameStage gameStage = new GameStage();
        Snake snake = new Snake(new Point2D(5, 5));
        Food normalFood = new Food(new Point2D(10, 10));
        SpecialFood specialFood = new SpecialFood(new Point2D(5, 5));

        GameLoop gameLoop = new GameLoop(gameStage, snake, normalFood, specialFood);

        assertEquals(0, gameLoop.getScore(), "Initial score should be 0");

        gameLoop.checkSpecialFoodCollision();

        assertEquals(5, gameLoop.getScore(), "Consuming special food should award exactly 5 points");
    }
}