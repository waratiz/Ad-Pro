package se233.casestudy4.controller;

import se233.casestudy4.model.GameCharacter;
import se233.casestudy4.view.GameStage;

public class DrawingLoop implements Runnable {
    private GameStage gameStage;
    private int frameRate;
    private float interval;
    private boolean running;
    public DrawingLoop(GameStage gameStage) {
        this.gameStage = gameStage;
        frameRate = 60;
        interval = 1000.0f / frameRate; // 1000 ms = 1 second
        running = true;
    }
    private void checkDrawCollisions(GameCharacter gameCharacter) {
        gameCharacter.checkReachGameWall();
        gameCharacter.checkReachHighest();
        gameCharacter.checkReachFloor();
    }
    private void paint(GameCharacter gameCharacter) {
        gameCharacter.repaint();
    }
    @Override
    public void run() {
        while (running) {
            float time = System.currentTimeMillis();
            for (GameCharacter gameCharacter : gameStage.getGameCharacters()) {
                checkDrawCollisions(gameCharacter);
                paint(gameCharacter);
            }
            time = System.currentTimeMillis() - time;
            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException e) {
                }
            } else {
                try {
                    Thread.sleep((long) (interval - (interval % time)));
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
