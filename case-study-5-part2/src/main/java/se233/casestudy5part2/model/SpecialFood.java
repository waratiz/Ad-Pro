package se233.casestudy5part2.model;

import javafx.geometry.Point2D;

public class SpecialFood extends Food {
    public SpecialFood(Point2D position) {
        super(position);
    }

    public SpecialFood() {
        super();
    }

    @Override
    public int getPoints() {
        return 5;
    }
}