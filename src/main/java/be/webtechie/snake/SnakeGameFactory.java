package be.webtechie.snake;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

import be.webtechie.snake.component.SnakeSkinComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * The factory which defines how each entity looks like
 */
public class SnakeGameFactory implements EntityFactory {

    /**
     * Types of objects we are going to use in our game.
     */
    public enum EntityType {
        SNAKE
    }

    @Spawns("snake")
    public Entity newShark(SpawnData data) {
        SnakeSkinComponent skin = new SnakeSkinComponent();
        var channel = new AnimationChannel(skin.getImages(), Duration.millis(250));
        return entityBuilder()
                .from(data)
                .type(EntityType.SNAKE)
                .viewWithBBox(new AnimatedTexture(channel).loop())
                //.viewWithBBox(new Rectangle(30, 30, Color.BLUE))
                .collidable()
                .build();
    }
}
