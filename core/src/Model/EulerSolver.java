package Model;

import org.graalvm.compiler.lir.amd64.vector.AMD64VectorMove;

public class EulerSolver extends Solver{

    Vector2d position;
    Vector2d velocity;
    Vector2d acceleration;

    public EulerSolver(String ab) {
        super(ab);
    }
    @Override
    public void setNextPositions(double solverStepSize) {
        currentPosX = currentPosX + solverStepSize*velocityX(solverStepSize);
        currentPosY = currentPosY + solverStepSize*velocityY(solverStepSize);
        currentVelX = velocityX(solverStepSize);
        currentVelY = velocityY(solverStepSize);
        position = nextPosition(position, velocity, this.solverStepSize);
        velocity = nextVelocity(velocity, acceleration, stepSize );

    }

    /**
     *
     * @param currentVelocity
     * @param currentAcceleration
     * @param stepSize
     * @return nextVelocity
     */
    public Vector2d nextVelocity(Vector2d currentVelocity, Vector2d currentAcceleration, double stepSize){
        return new Vector2d(currentVelocity.getX()+currentAcceleration.getX()*stepSize,currentVelocity.getY()+currentAcceleration.getY()*stepSize);
    }

    /**
     * @param currentPosition
     * @param currentVelocity
     * @param stepSize
     * @return the next postion
     */
    public Vector2d nextPosition(Vector2d currentPosition, Vector2d currentVelocity, double stepSize) {
        return new Vector2d(currentPosition.getX()+velocity.getX()*stepSize,currentPosition.getY()+velocity.getY()*stepSize);
    }

    @Override
    public void setPosition(Vector2d v) {
        this.position=v;
    }

    @Override
    public void setVelocity(Vector2d velocity) {
        this.velocity=velocity;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
}
