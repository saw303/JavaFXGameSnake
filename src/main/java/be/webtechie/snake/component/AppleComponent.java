package be.webtechie.snake.component;

import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class AppleComponent extends Component {


  @Override
  public void onAdded() {
    super.onAdded();
    spawn("apple", 128, 64);
  }
}
