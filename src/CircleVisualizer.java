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

    private int currentPoint;

    private long intervalTime;

    private boolean pause = true;



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
        numPoints = 360;
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
            line.setFill(Color.BLACK);
            this.drawingPane.getChildren().add(line);
            this.setPoint((this.currentPoint + 1) % numPoints);
            if (this.currentPoint == (numPoints - 1)) {
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
     * @param newMult
     */
    public void setMultiplier(double newMult){
        multiplier = newMult;
    }

    public void clear(){
        drawingPane.getChildren().clear();
        drawingPane.getChildren().add(fullCircle);
    }

    public long getIntervalTime(){
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime){
        this.intervalTime = intervalTime;
    }

    public boolean isPaused(){
        return pause;
    }

    public void setPause(boolean pause){
        this.pause  = pause;
    }

    public void setMultIncrement(double newIncrement){
        multIncrement = newIncrement;
    }


}
