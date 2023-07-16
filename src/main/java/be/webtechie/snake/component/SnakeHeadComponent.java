package be.webtechie.snake.component;

import be.webtechie.snake.SnakeGameFactory;
import be.webtechie.snake.Variables;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

import static be.webtechie.snake.SnakeGameFactory.EntityType.SNAKE_HEAD;
import static be.webtechie.snake.Variables.LIVES;
import static be.webtechie.snake.Variables.PREVIOUS_POSITION;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.spawn;


public class SnakeHeadComponent extends Component {

    private static final Point2D DIRECTION_UP = new Point2D(0, -1);
    private static final Point2D DIRECTION_DOWN = new Point2D(0, 1);
    private static final Point2D DIRECTION_LEFT = new Point2D(-1, 0);
    private static final Point2D DIRECTION_RIGHT = new Point2D(1, 0);
    private Point2D direction = DIRECTION_RIGHT;

    // head - body - ...
    private List<Entity> bodyParts = new ArrayList<>();

    @Override
    public void onAdded() {
        bodyParts.add(entity);
        entity.setProperty(PREVIOUS_POSITION, entity.getPosition());
    }

    @Override
    public void onUpdate(double tpf) {
        Point2D previousPosition = entity.getPosition();
        entity.setProperty(PREVIOUS_POSITION, previousPosition);
        entity.translate(direction.multiply(32));

        checkForBounds();

        for (int i = 1; i < bodyParts.size(); i++) {
            var prevPart = bodyParts.get(i - 1);
            var part = bodyParts.get(i);

            Point2D prevPos = prevPart.getObject("prevPos");

            part.setProperty(PREVIOUS_POSITION, part.getPosition());
            part.setPosition(prevPos);
        }

        System.out.println("Current pos: " + previousPosition);

        Entity apple = FXGL.byType(SnakeGameFactory.EntityType.APPLE).get(0);
        if (apple.getPosition().equals(previousPosition)) {
            FXGL.byType(SNAKE_HEAD).get(0).getComponent(SnakeHeadComponent.class).grow();
            apple.translate(FXGLMath.random(0, getAppWidth() - 20), FXGLMath.random(0, getAppHeight() - 20));
        }
    }

    private void checkForBounds() {
        if (entity.getX() < 0)
            die();

        if (entity.getX() >= getAppWidth())
            die();

        if (entity.getY() < 0)
            die();

        if (entity.getY() >= getAppHeight())
            die();
    }

    public void die() {
        inc(LIVES, -1);

        // clean up body parts, apart from head
        bodyParts.stream()
                .skip(1)
                .forEach(Entity::removeFromWorld);

        bodyParts.clear();
        bodyParts.add(entity);

        entity.setPosition(0, 0);
        right();
    }

    public void up() {
        direction = DIRECTION_UP;
    }

    public void down() {
        direction = DIRECTION_DOWN;
    }

    public void left() {
        direction = DIRECTION_LEFT;
    }

    public void right() {
        direction = DIRECTION_RIGHT;
    }

    public void grow() {
        inc(Variables.SCORE, 1);

        var lastBodyPart = bodyParts.get(bodyParts.size() - 1);

        Point2D pos = lastBodyPart.getObject(PREVIOUS_POSITION);

        var body = spawn("snakeBody", pos);

        bodyParts.add(body);
    }

    public void log() {
        bodyParts.forEach(part -> {
            System.out.println(part.getPosition());
            System.out.println(part.getObject(PREVIOUS_POSITION).toString());
        });
    }
}
