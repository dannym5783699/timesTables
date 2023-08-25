/**
 * @author Danny Metcalfe
 * This is the main class that will run the application and create a circle visualization
 * using the CircleVsualization class to run the visualization.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Starts the application and visualization
 */
public class Main extends Application {

    /**
     * Start is overridden to have the desired window be created with the visualization.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Times Tables");
        BorderPane rootLayout = new BorderPane();
        CircleVisualizer visualization = new CircleVisualizer(rootLayout);
        primaryStage.setScene(new Scene(rootLayout, 1200, 800));
        visualization.drawLine(12);
        primaryStage.show();
        //Need timer.
    }

}
