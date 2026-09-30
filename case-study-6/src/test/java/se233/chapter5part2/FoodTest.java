package se233.chapter5part2;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.casestudy6.model.Food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class FoodTest {
    private Food food;

    @BeforeEach
    public void setup() {
        food = new Food(new Point2D(0, 0));
    }
    @Test
    public void respawn_shouldBeOnNewPosition() {
        food.respawn();
        assertFalse(food.getPosition().equals(new Point2D(0, 0)),
                "Food position should change after respawn");
    }
}
