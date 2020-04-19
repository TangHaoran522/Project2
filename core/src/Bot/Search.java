package Bot;

import Model.Vector2d;
import com.mygdx.game.PhysicsEngine;
import com.mygdx.game.PuttingCourse;

import java.util.LinkedList;

public class Search {
    private PuttingCourse course;
    private PhysicsEngine engine;
    private double radius;

    /**
     * Constructor
     * @param course terrain on which we need to do the research
     * @param engine phyisics engine to simulate shots
     */
    public Search(PuttingCourse course, PhysicsEngine engine) {
        this.course = course;
        this.engine = engine;
    }

    public Node search(){
        Node start = new Node(course.get_start_position(), course.get_flag_position());
        LinkedList<Node> stack = new LinkedList<>();
        LinkedList<Node> explored = new LinkedList<>();
        stack.add(start);

        return start;
    }

    private double distance(Vector2d n, Vector2d p){
        double x = (n.getX()-p.getX()), y = (n.getY()-p.getY());
        return Math.sqrt(x*x+y*y);
    }
}
