package starb.client.ui.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
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

    /**
     *  Sets the scene to the given scene.
     *  If the scene has not been created yet, then it will create it.
     *  If the scene has been created, then it will just set the scene to that.
     *
     *
     * @param scene The scene to set to.
     * @param params The parameters to pass to the constructor of the scene.
     * @throws NoSuchMethodException If there is no constructor that matches the given parameters.
     * @throws InvocationTargetException If the constructor throws an exception.
     * @throws InstantiationException If the constructor throws an exception.
     * @throws IllegalAccessException If the constructor throws an exception.
     */
    public static void setScene(Class<? extends Parent> scene, Object... params) throws
            NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        if (scenes == null) {
            scenes = new HashMap<>();
        }

        if (!scenes.containsKey(scene)) {

            boolean foundMatchingConstructor = false;

            Constructor<?> cons = null;

            /*
             *  When creating a new scene, we need to find the constructor in that class
             *  that matches the parameters we are given.
             *
             *  For some scenes, there are constructors with varying parameters.
             *  For example, the PuzzleScene class has a constructor that takes in a
             *  Board object and a String object.
             *
             *  This also checks to see if we can actually create a scene with the given
             *  parameters.
             *
             *  If we can't, then we throw a NoSuchMethodException.
             */

            for (int i = 0; i < scene.getDeclaredConstructors().length; i++) {


                // oh, god this is ugly
                cons = scene.getDeclaredConstructors()[i];

                // These question marks are a mystery to me.
                Class<?>[] conClasses = new Class<?>[cons.getParameterCount()];

                for (int j = 0; j < cons.getParameterCount(); j++) {
                    conClasses[j] = cons.getParameters()[j].getType();
                }

                Class<?>[] paramsClasses = new Class<?>[params.length];

                for (int j = 0; j < params.length; j++) {
                    paramsClasses[j] = params[j].getClass();
                }

                if (Arrays.equals(conClasses, paramsClasses)) {
                    foundMatchingConstructor = true;
                    break;
                }
            }

            if (!foundMatchingConstructor) {

                throw new NoSuchMethodException("No constructor found for " + scene.getName() +
                        " with the given parameters.");
            }

            Scene initScene =
                    new Scene((Parent) cons.newInstance(params));
            scenes.put(scene, initScene);
        }

        stage.setScene(scenes.get(scene));

    }

    public static void setScene(Class<? extends Parent> scene) throws
            InvocationTargetException, NoSuchMethodException, InstantiationException,
            IllegalAccessException {

        Object[] blankParams = {};

        setScene(scene, blankParams);

    }

    public static void setStage(Stage stage) {
        SceneSwitcher.stage = stage;
    }
}
