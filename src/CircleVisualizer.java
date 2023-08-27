/**
 * @author Danny Metcalfe
 * This class does the work of creating a circle and finding the points to draw lines
 * within the circle.
 */

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.Random;

/**
 * A Circle Visualizer has a BorderPane and the number of points around the circle to start a line from.
 * It also has a Circle and the multiplier for points.
 */
public class CircleVisualizer {
    //A BorderPane is needed to put the circle and lines in.
    private final BorderPane locationScene;

    //Need to know how many points to go through on the circle.
    private int numPoints;

    //Need to know what multiplier to use and can be decimal.
    private double multiplier;
    private double multIncrement = .1;

    //Has a full outside circle.
    private final Circle fullCircle;

    // Another pane is used to put all the lines and the circle into.
    private final Pane drawingPane;

    //Current point
    private int currentPoint;

    //Interval between each times table.
    private long intervalTime;

    //Is this visual paused
    private boolean pause = true;


    //Color to draw with.
    private Color drawColor = Color.BLACK;



    /**
     * Sets up the circle outline and the Pane.
     * Also sets up default points and multiplier for now.
     * @param locationScene this is the borderPane where the drawing Pane will be placed into the center.
     */
    public CircleVisualizer(BorderPane locationScene){
        this.drawingPane = new Pane();
        this.locationScene = locationScene;
        fullCircle = new Circle(500, 400, 300);
        fullCircle.setFill(null);
        fullCircle.setStroke(Color.DARKSALMON);
        drawingPane.getChildren().add(fullCircle);
        locationScene.setCenter(drawingPane);
        numPoints = 500;
        multiplier = 2;
    }


    /**
     * Finds an angle with a points starting on the left side and going clockwise.
     * @param point requires a point on a circle such as first or 5th point around the circle.
     *              where the first point is on the left going clockwise.
     * @return returns the angle in radians.
     */
    public double findAngle(double point){
        return (((-1)*(point)) / numPoints)*(2*(Math.PI))+Math.PI;
    }


    /**
     * Draws a line from the current point to the next point using the multiplier.
     */
    public void drawLines(){

        while(true && !pause) {
            final double nextPoint = (multiplier * this.currentPoint) % numPoints;
            //negative times radians plus pi for next angle starting left side.
            final double nextAng = findAngle(nextPoint);
            final double currentAng = findAngle(this.currentPoint);

            //Find coordinates.
            final double startX = (Math.cos(currentAng) * fullCircle.getRadius()) + fullCircle.getCenterX();
            final double startY = (-1 * Math.sin(currentAng) * fullCircle.getRadius()) + fullCircle.getCenterY();
            final double endX = (Math.cos(nextAng) * fullCircle.getRadius()) + fullCircle.getCenterX();
            final double endY = (-1 * Math.sin(nextAng) * fullCircle.getRadius()) + fullCircle.getCenterY();
            Line line = new Line(startX, startY, endX, endY);
            fullCircle.setStroke(drawColor);
            line.setFill(Color.RED);
            line.setStroke(drawColor);
            this.drawingPane.getChildren().add(line);
            this.setPoint((this.currentPoint + 1) % numPoints);

            //Change color and increment at the end of the circle.
            if (this.currentPoint == (numPoints - 1)) {
                Random colorPick = new Random();
                double redC = colorPick.nextDouble();
                double greenC = colorPick.nextDouble();
                double blueC = colorPick.nextDouble();
                drawColor = new Color(redC, greenC, blueC, 1);
                multiplier = multiplier + multIncrement;
                break;
            }
        }


    }

    /**
     * Gets the current point.
     * @return returns the current point.
     */
    public int getPoint(){
        return this.currentPoint;
    }

    /**
     * Sets the current point in the circle visualizer.
     * @param point Requires a point to set.
     */
    public void setPoint(int point){
        this.currentPoint = point;
    }

    /**
     * Gets the current multiplier value.
     * @return returns the multiplier value as a double.
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * Set the multiplier
     * @param newMult the new multiplier.
     */
    public void setMultiplier(double newMult){
        multiplier = newMult;
    }

    /**
     * Clears the lines from the pane.
     */
    public void clear(){
        drawingPane.getChildren().clear();
        drawingPane.getChildren().add(fullCircle);
    }

    /**
     * Gets the times between each times table circle.
     * @return the interval between each circle in milliseconds.
     */
    public long getIntervalTime(){
        return intervalTime;
    }

    /**
     * Sets the times between circle drawings.
     * @param intervalTime delay time in milliseconds
     */
    public void setIntervalTime(long intervalTime){
        this.intervalTime = intervalTime;
    }

    /**
     * Checks if the visualization is paused.
     * @return true if paused and false if not paused.
     */
    public boolean isPaused(){
        return pause;
    }

    /**
     * Changes if the visualization is paused or not.
     * @param pause boolean value to change to, true if setting paused and false if not.
     */
    public void setPause(boolean pause){
        this.pause  = pause;
    }

    /**
     * Changes the times table increment.
     * @param newIncrement requires a double to change the increment to.
     */
    public void setMultIncrement(double newIncrement){
        multIncrement = newIncrement;
    }

    /**
     * Sets the number of points on the circle.
     * @param numberOfPoints New number of points on teh circle.
     */
    public void setNumPoints(int numberOfPoints){
        this.numPoints = numberOfPoints;
    }

}
