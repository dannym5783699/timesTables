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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
             ;

            @Override
            public void handle(long now) {
                long intervalNanos =Duration.ofMillis(visualization.getIntervalTime()).toNanos();

                if( (now- ( intervalNanos) > lastInterval) && !visualization.isPaused()){
                    visualization.clear();
                    visualization.drawLines();


                    lastInterval = now;
                }
//                if( (now - (Duration.ofMillis(multipleIntTime).toNanos() )) > lastMultiple ){
//                    visualization.setMultiplier(visualization.getMultiplier()+multipleIncrement);
//                    lastMultiple = now;
//                }


            }
        };
        timer.start();

        //Creating Control layouts on the left and right of the screen
        VBox rightControls = new VBox();
        rootLayout.setRight(rightControls);
        rightControls.setAlignment(Pos.CENTER);


        VBox leftControls  = new VBox();
        rootLayout.setLeft(leftControls);
        leftControls.setAlignment(Pos.CENTER);
        leftControls.setTranslateY(-75);

        //Creating controls
        Button clearCircle = new Button("Clear Circle");
        rightControls.getChildren().add(clearCircle);
        clearCircle.setTranslateX(-30);
        clearCircle.setOnMousePressed(new EventHandler<MouseEvent>() {
            /**
             * Control to clear the circle when teh button is clicked.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                visualization.clear();
            }
        });



        //Drawing speed slider and label.
        Slider visualizationSpeed = new Slider(0, 1000, 1);
        Label lineSpeedLabel = new Label("Drawing speed (ms)");
        leftControls.getChildren().add(lineSpeedLabel);
        leftControls.getChildren().add(visualizationSpeed);
        visualizationSpeed.setShowTickMarks(true);
        visualizationSpeed.setShowTickLabels(true);
        visualizationSpeed.setMajorTickUnit(200);
        visualizationSpeed.setTranslateX(50);

        lineSpeedLabel.setTranslateX(50);

        //When the mouse is moved around the slider the speed is updated.
        visualizationSpeed.setOnMouseMoved(new EventHandler<MouseEvent>() {
            /**
             * On mouse movement within the slider the interval is updated to the slider value.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                visualization.setIntervalTime( (long)visualizationSpeed.getValue());
            }
        });

        //Adding a pause button
        Button pauseButton = new Button("Start Visualization");
        rightControls.getChildren().add(pauseButton);
        pauseButton.setTranslateX(-30);
        pauseButton.setTranslateY(10);

        //When button is clicked it will start, pause, or resume the drawing.
        pauseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /**
             * Swaps the text between pause and resume and swaps the pause status on click.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                if(pauseButton.getText().equals("Pause")){
                    pauseButton.setText("Resume");
                }
                else{
                    pauseButton.setText("Pause");
                }
                visualization.setPause(!visualization.isPaused());
            }
        });


        //Slider and label to change the multiplier step.
        Label multiplierLabel = new Label("Multiplier step");
        leftControls.getChildren().add(multiplierLabel);
        multiplierLabel.setTranslateX(40);
        multiplierLabel.setTranslateY(25);

        Slider multiplierSteps = new Slider(.05, 5, .1);
        multiplierSteps.setMajorTickUnit(1);
        leftControls.getChildren().add(multiplierSteps);
        multiplierSteps.setTranslateX(50);
        multiplierSteps.setTranslateY(25);
        multiplierSteps.setShowTickLabels(true);

        //When mouse is moved within the slider the multiplier increment value is updated
        multiplierSteps.setOnMouseMoved(new EventHandler<MouseEvent>() {
            /**
             * Changes the times table increment when mouse is moved in the slider.
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                visualization.setMultIncrement(multiplierSteps.getValue());
            }
        });


        //Control to jump between times tables.
        Label timesTableLabel = new Label("Times Table");
        TextField timesTableChanger = new TextField();
        leftControls.getChildren().add(timesTableLabel);
        timesTableLabel.setTranslateX(40);
        timesTableLabel.setTranslateY(65);
        leftControls.getChildren().add(timesTableChanger);
        timesTableChanger.setTranslateX(50);
        timesTableChanger.setTranslateY(70);
        timesTableChanger.setPromptText("Enter a number");


        //When enter is pressed the value is taken and parsed.
        timesTableChanger.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * When enter is pressed the text is parsed to a double and an error is not.
             * The times table or multiplier is then updated.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    try {
                        if(timesTableChanger.getText().length() > 6){
                            throw new NumberFormatException();
                        }
                        visualization.setMultiplier(Double.parseDouble(timesTableChanger.getText()));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Number given");
                    }
                }
            }
        });


        //Adding control to adjust the number of points.
        Label pointNumLabel = new Label("Number of Points ");
        leftControls.getChildren().add(pointNumLabel);
        pointNumLabel.setTranslateY(100);
        pointNumLabel.setTranslateX(50);

        TextField numPointsField = new TextField();
        leftControls.getChildren().add(numPointsField);
        numPointsField.setTranslateY(105);
        numPointsField.setTranslateX(50);
        numPointsField.setPromptText("Enter a number");

        //Changing points when a number is entered.
        numPointsField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            /**
             * When enter is pressed the field text is parsed to an int, an error message otherwise.
             * The Number of points is updated based on this text field on enter.
             * @param event the event which occurred
             */
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    try{
                        if(numPointsField.getText().length() > 4){
                            throw new NumberFormatException();
                        }

                        int newVal = Integer.parseInt(numPointsField.getText());
                        if (newVal < 1){
                            throw new NumberFormatException();
                        }
                        visualization.setNumPoints(newVal);
                    }catch(NumberFormatException e){
                        System.out.println("Invalid number given");
                    }
                }
            }
        });



    }

}
