package Bot;

import Model.PhysicsEngine;
import Model.Vector2d;
import com.mygdx.game.PuttingCourse;

public class Node {
    private Vector2d coordinates;
    private int costG;
    private double costh;
    private Node parent;
    private static Vector2d finish;
    private Vector2d lastShot;


    /**
     * first constructor
     * @param start position of the ball at the start
     * @param finish hole position
     */
    public Node(Vector2d start, Vector2d finish){
        this.coordinates = start;
        this.parent = this;
        this.finish = finish;

    }


    public Node( Node parent, Vector2d lastShot, int costG) {
        this.costG = costG;
        this.parent = parent;
        this.lastShot = lastShot;
    }


    /**
     * accessor for coordinates
     * @return x and y position
     */
    public Vector2d getCoordinates() {
        return coordinates;
    }

    /**
     * accessor for cost function
     * @return cost of node up to that point
     */
    public int getCostG() {
        return costG;
    }

    /**
     * accessor for cost function
     * @return cost of node until destination
     */
    public double getCosth() {
        return costh;
    }

    /**
     * accessor for last shot
     * @return the vector of the shot
     */
    public Vector2d getLastShot() {
        return lastShot;
    }

}
