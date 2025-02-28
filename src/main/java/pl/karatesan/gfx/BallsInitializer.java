package pl.karatesan.gfx;

import javafx.scene.paint.Color;
import pl.karatesan.Vector2D;
import pl.karatesan.shapes.BallFx;

public class BallsInitializer implements GameInitializer{

    double ballRadius = 5.0;
    double windowWidth = 800;
    double windowHeight = 600;
    double mapRadius = (windowHeight - 10) /2;
    Vector2D mapCenter = new Vector2D(windowWidth / 2.0, windowHeight / 2.0);
    double max_speed = mapRadius / 1.5;

    @Override
    public void initialize(ObjectManager manager, Graphics gtx) {

        BallFx map = new BallFx();
        map.setRadius(mapRadius);
        map.setCenter(mapCenter);
        map.setFill(Color.TRANSPARENT);
        map.setStroke(Color.WHITE);
        manager.setMap(map);
        for (int i = 0; i < 50; i++) {
            BallFx circle = new BallFx();
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            Color color = Color.rgb(red, green, blue);
            circle.setFill(color);
            // generate coordinates for a ball - that are inside parent circle
            double angle = Math.random() * 360;
            double randomDistance = (mapRadius - ballRadius) * Math.sqrt(Math.random());
            double x = map.getCenterX() + randomDistance * Math.cos(angle);
            double y = map.getCenterY() + randomDistance * Math.sin(angle);
            circle.setCenterX(x);
            circle.setCenterY(y);
            circle.setVelocity(new Vector2D(Math.random() * 2 * max_speed - max_speed, Math.random() * 2 * max_speed - max_speed));
            circle.setRadius(ballRadius);
            manager.addObject(circle);
        }
    }
}
