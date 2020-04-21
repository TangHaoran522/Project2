package Model;

public class EulerSolver extends Solver{

    public EulerSolver(String ab) {
        super(ab);
    }
    @Override
    public void setNextPositions(double solverStepSize) {
        currentPosX = currentPosX + solverStepSize*velocityX(solverStepSize);
        currentPosY = currentPosY + solverStepSize*velocityY(solverStepSize);
        currentVelX = velocityX(solverStepSize);
        currentVelY = velocityY(solverStepSize);
    }

    @Override
    public void setPosition(Vector2d v) {

    }

    @Override
    public void setVelocity(Vector2d v) {

    }

    @Override
    public Vector2d getPosition() {
        return null;
    }
}
