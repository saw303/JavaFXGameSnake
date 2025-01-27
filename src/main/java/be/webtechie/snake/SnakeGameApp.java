package be.webtechie.snake;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameState;

import be.webtechie.snake.component.SnakeHeadComponent;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SnakeGameApp extends GameApplication {

  /** Reference to the factory which will defines how all the types must be created. */
  private final SnakeGameFactory snakeGameFactory = new SnakeGameFactory();

  /**
   * Player object we are going to use to provide to the factory so it can start a bullet from the
   * player center.
   */
  private Entity player;

  /**
   * Main entry point where the application starts.
   *
   * @param args Start-up arguments
   */
  public static void main(String[] args) {
    // Launch the FXGL game application
    launch(args);
  }

  /**
   * General game settings. For now only the title is set, but a longer list of options is
   * available.
   *
   * @param settings The settings of the game which can be further extended here.
   */
  @Override
  protected void initSettings(GameSettings settings) {
    settings.setWidth(64 * 15);
    settings.setHeight(64 * 15);
    settings.setTitle("Joshuas Snake Game");
    settings.setTicksPerSecond(10);
  }

  /**
   * General game variables. Used to hold the points and lives.
   *
   * @param vars The variables of the game which can be further extended here.
   */
  @Override
  protected void initGameVars(Map<String, Object> vars) {
    vars.put(Variables.SCORE, 0);
    vars.put(Variables.LIVES, 5);
  }

  @Override
  protected void initUI() {
    Text scoreLabel = getUIFactoryService().newText("Score", Color.BLACK, 22);
    Text scoreValue = getUIFactoryService().newText("", Color.BLACK, 22);
    Text livesLabel = getUIFactoryService().newText("Lives", Color.BLACK, 22);
    Text livesValue = getUIFactoryService().newText("", Color.BLACK, 22);

    scoreLabel.setTranslateX(20);
    scoreLabel.setTranslateY(20);

    scoreValue.setTranslateX(90);
    scoreValue.setTranslateY(20);

    livesLabel.setTranslateX(getAppWidth() - 100);
    livesLabel.setTranslateY(20);

    livesValue.setTranslateX(getAppWidth() - 30);
    livesValue.setTranslateY(20);

    scoreValue.textProperty().bind(getGameState().intProperty(Variables.SCORE).asString());
    livesValue.textProperty().bind(getGameState().intProperty(Variables.LIVES).asString());

    getGameScene().addUINodes(scoreLabel, scoreValue, livesLabel, livesValue);
  }

  /**
   * Input configuration, here you configure all the input events like key presses, mouse clicks,
   * etc.
   */
  @Override
  protected void initInput() {
    onKeyDown(
        KeyCode.LEFT,
        () ->
            this.player
                .getComponentOptional(SnakeHeadComponent.class)
                .ifPresent(SnakeHeadComponent::left));
    onKeyDown(
        KeyCode.RIGHT,
        () ->
            this.player
                .getComponentOptional(SnakeHeadComponent.class)
                .ifPresent(SnakeHeadComponent::right));
    onKeyDown(
        KeyCode.UP,
        () ->
            this.player
                .getComponentOptional(SnakeHeadComponent.class)
                .ifPresent(SnakeHeadComponent::up));
    onKeyDown(
        KeyCode.DOWN,
        () ->
            this.player
                .getComponentOptional(SnakeHeadComponent.class)
                .ifPresent(SnakeHeadComponent::down));

    onKeyDown(
        KeyCode.F,
        () -> {
          player.getComponent(SnakeHeadComponent.class).grow();
        });

    onKeyDown(
        KeyCode.G,
        () -> {
          player.getComponent(SnakeHeadComponent.class).log();
        });
  }

  /** Initialization of the game by providing the {@link EntityFactory}. */
  @Override
  protected void initGame() {
    getGameWorld().addEntityFactory(this.snakeGameFactory);

    // Add the player
    this.player = spawn("snakeHead", 0, 0);
  }
}
