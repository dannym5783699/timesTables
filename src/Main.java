/**
 * @author Danny Metcalfe
 * This is the main class that will run the application and create a circle visualization
 * using the CircleVsualization class to run the visualization.
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Duration;

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
     *
     */
    @Override
    public void start(Stage primaryStage){

        //How often lines are created in milliseconds.
        long intervalTime  = 10;
        //Multipler changed in milliseconds.
        long multipleIntTime = 10000;
        double multipleIncrement = 1;
        primaryStage.setTitle("Times Tables");
        BorderPane rootLayout = new BorderPane();
        CircleVisualizer visualization = new CircleVisualizer(rootLayout);
        primaryStage.setScene(new Scene(rootLayout, 1200, 800));
        visualization.setPoint(0);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            long lastInterval;
            long lastMultiple;

            @Override
            public void handle(long now) {
                if( (now- (Duration.ofMillis(intervalTime)).toNanos() ) > lastInterval){
                    visualization.drawLine(visualization.getPoint());

                    lastInterval = now;
                }
                if( (now - (Duration.ofMillis(multipleIntTime).toNanos() )) > lastMultiple ){
                    visualization.setMultiplier(visualization.getMultiplier()+multipleIncrement);
                    lastMultiple = now;
                }

            }
        };
        timer.start();

        VBox rightControls = new VBox();
        rootLayout.setRight(rightControls);
        rightControls.setAlignment(Pos.CENTER_LEFT);

        Button clearCircle = new Button("Clear Circle");
        rightControls.getChildren().add(clearCircle);
        clearCircle.setTranslateX(-30);
        clearCircle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                visualization.clear();
            }
        });

    }

}
