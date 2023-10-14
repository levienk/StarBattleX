package starb.client;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SceneSwitcher {

    static Map<Class<? extends Parent>, Scene> scenes;
    static Stage stage;

    /**
     *  No constructor for this class.
     */
    private SceneSwitcher() {
    }

    public static void setScene(Class<? extends Parent> scene) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        if (scenes == null) {
            scenes = new HashMap<>();
        }

        if (!scenes.containsKey(scene)) {
            Scene initScene = new Scene(scene.getDeclaredConstructor().newInstance());
            scenes.put(scene, initScene);
        }

        stage.setScene(scenes.get(scene));

    }

    public static void setStage(Stage stage) {
        SceneSwitcher.stage = stage;
    }
}
