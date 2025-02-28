package pl.karatesan;

import javafx.scene.paint.Color;
import pl.karatesan.gfx.Graphics;
import pl.karatesan.gfx.ObjectManager;
import pl.karatesan.shapes.BallFx;

public class Main {

    public static void main(String[] args) {

        Graphics.startEngine();
 //       ObjectManager manager = new ObjectManager();
   //     Graphics gtx = new Graphics();
//        double ballRadius = 5.0;
//        double mapRadius = (gtx.getWindowHeight() - 10) / 2.0;
//        Vector2D mapCenter = new Vector2D(gtx.getWindowWidth() / 2.0, gtx.getWindowHeight() / 2.0);
//        BallFx map = new BallFx();
//        map.setRadius(mapRadius);
//        map.setCenter(mapCenter);
//        map.setFill(Color.TRANSPARENT);
//        map.setStroke(Color.WHITE);
//        manager.setMap(map);
//        for (int i = 0; i < 1; i++) {
//            BallFx circle = new BallFx();
//            int red = (int) (Math.random() * 255);
//            int green = (int) (Math.random() * 255);
//            int blue = (int) (Math.random() * 255);
//            Color color = Color.rgb(red, green, blue);
//            circle.setFill(color);
//            // generate coordinates for a ball - that are inside parent circle
//            double angle = Math.random() * 360;
//            double randomDistance = (mapRadius - ballRadius) * Math.sqrt(Math.random());
//            double x = map.getCenterX() + randomDistance * Math.cos(angle);
//            double y = map.getCenterY() + randomDistance * Math.sin(angle);
//            circle.setCenterX(x);
//            circle.setCenterY(y);
//            double max_speed = mapRadius / 1.5;
//            circle.setVelocity(new Vector2D(Math.random() * 2 * max_speed - max_speed, Math.random() * 2 * max_speed - max_speed));
//            circle.setRadius(ballRadius);
//            manager.addObject(circle);
//        }
//        gtx.setWindowName("test");
//        gtx.showFps(true);

    }


}