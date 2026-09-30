package se233.casestudy6.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import se233.casestudy6.model.Direction;
import se233.casestudy6.model.Food;
import se233.casestudy6.model.SpecialFood;
import se233.casestudy6.model.Snake;
import se233.casestudy6.view.GameStage;

public class GameLoop implements Runnable {
    private GameStage gameStage;
    private Snake snake;
    private Food food;
    private SpecialFood specialFood;
    private float interval = 1000.0f / 10;
    private boolean running;
    private int score;

    public GameLoop(GameStage gameStage, Snake snake, Food food) {
        this.snake = snake;
        this.gameStage = gameStage;
        this.food = food;
        this.specialFood = new SpecialFood();
        running = true;
    }

    public GameLoop(GameStage gameStage, Snake snake, Food food, SpecialFood specialFood) {
        this.snake = snake;
        this.gameStage = gameStage;
        this.food = food;
        this.specialFood = specialFood;
        running = true;
        score = 0;
    }

    private void keyProcess() {
        KeyCode curKey = gameStage.getKey();
        Direction curDirection = snake.getDirection();
        if (curKey == KeyCode.UP && curDirection != Direction.DOWN)
            snake.setDirection(Direction.UP);
        else if (curKey == KeyCode.DOWN && curDirection != Direction.UP)
            snake.setDirection(Direction.DOWN);
        else if (curKey == KeyCode.LEFT && curDirection != Direction.RIGHT)
            snake.setDirection(Direction.LEFT);
        else if (curKey == KeyCode.RIGHT && curDirection != Direction.LEFT)
            snake.setDirection(Direction.RIGHT);
        snake.move();
    }
    public void checkCollision() {
        if (snake.collided(food)) {
            snake.grow();
            food.respawn();
            score += food.getPoints();
        }
        checkSpecialFoodCollision();

        if (snake.checkDead()) {
            running = false;
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Game Over! Your score: " + score);
                alert.showAndWait();
                System.exit(0);
            });
        }
    }

    public void checkSpecialFoodCollision() {
        if (snake.getHead().equals(specialFood.getPosition())) {
            snake.grow();
            specialFood.respawn();
            score += specialFood.getPoints();
        }
    }

    private void redraw() {
        gameStage.render(snake, food, specialFood);
    }

    @Override
    public void run() {
        while (running) {
            keyProcess();
            checkCollision();
            redraw();
            try {
                Thread.sleep((long)interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public int getScore() {
        return score;
    }
}
