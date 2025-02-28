package pl.karatesan.shapes;

import javafx.scene.shape.Circle;
import pl.karatesan.Vector2D;

public class BallFx extends Circle implements GameObject<BallFx> {
    private Vector2D velocity;

    private static class ClipResult {
        Vector2D position; // Clipped absolute position
        Vector2D normal;   // Normal vector (towards center)
        double excessDistance; // Distance overshot

        ClipResult(Vector2D position, Vector2D normal, double excessDistance) {
            this.position = position;
            this.normal = normal;
            this.excessDistance = excessDistance;
        }
    }

    public BallFx() {
    }

    public BallFx(double centerX, double centerY, double radius, Vector2D velocity) {
        super(centerX, centerY, radius);
        this.velocity = velocity;
    }

    public BallFx(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        this.velocity = new Vector2D(0, 0);
    }

    @Override
    public void update(double deltaTime, GameObject<BallFx> map) {
        move(deltaTime, map);
    }

    @Override
    public BallFx getNode() {
        return this;
    }

    //Returns clipped position related to map center
    private ClipResult clipToMap(GameObject<BallFx> map, Vector2D newPosition) {

        double dx = newPosition.x - map.getNode().getCenterX();
        double dy = newPosition.y - map.getNode().getCenterY();
        //distance from center of the map to outer edge of ball
        double distanceToNewPosition = Math.sqrt(dx * dx + dy * dy) + getRadius();
        double mapRadius = map.getNode().getRadius();

        if (distanceToNewPosition >= mapRadius) {
            // Clip to the map’s radius minus the ball’s radius (tangent point)
            double clipFactor = mapRadius / distanceToNewPosition;
            double clippedDx = dx * clipFactor;
            double clippedDy = dy * clipFactor;
            double nx = -clippedDx / mapRadius; // Normalized
            double ny = -clippedDy / mapRadius;
            // Calculate excess movement
            double excessDistance = distanceToNewPosition - mapRadius;
            return new ClipResult(
                    new Vector2D(clippedDx, clippedDy),
                    new Vector2D(nx, ny),
                    excessDistance);
        }
        return new ClipResult(newPosition, null, 0);
    }

    public Vector2D calculateBounceVelocity(Vector2D normal, Vector2D velocity) {
        double dotProduct = velocity.x * normal.x + velocity.y * normal.y;
        return new Vector2D(velocity.x - 2 * dotProduct * normal.x,
                        velocity.y - 2 * dotProduct * normal.y);
    }

    public void move(double deltaTime, GameObject<BallFx> map) {
        double factor = deltaTime / 1_000_000_000.0;
        Vector2D newPosition = new Vector2D(
                getCenterX() + velocity.x * factor,
                getCenterY() + velocity.y * factor
        );
        ClipResult clipResult = clipToMap(map, newPosition);
        if (clipResult.excessDistance > 0) {
            // Calculate excess movement
            double speed = Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y);
            double excessTime = (clipResult.excessDistance / speed) * factor;
            // Reflect velocity
            velocity = calculateBounceVelocity(clipResult.normal, velocity);
            // Apply bounce with remaining time
            newPosition.x += velocity.x * (factor - excessTime);
            newPosition.y += velocity.y * (factor - excessTime);
        }
        setCenterX(newPosition.x);
        setCenterY(newPosition.y);
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D getCenterVector() {
        return new Vector2D(getCenterX(), getCenterY());
    }

    public void setCenter(Vector2D center){
        setCenterX(center.x);
        setCenterY(center.y);
    }

}
