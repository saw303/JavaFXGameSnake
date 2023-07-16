package be.webtechie.snake.component;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import static com.almasb.fxgl.dsl.FXGL.spawn;

public class AppleComponent extends Component {

    private Entity apple;
    @Override
    public void onAdded() {
        super.onAdded();
        this.apple = spawn("apple", 128, 64);
    }
}
