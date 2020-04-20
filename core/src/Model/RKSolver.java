package Model;

public class RKSolver extends Solver {

    public RKSolver(String ab) {
        super(ab);
    }

    @Override
    public void setNextPositions(double solverStepSize) {
        double k1 = solverStepSize*currentVelX;
        double k2 = solverStepSize*velocityX(0.5*solverStepSize);
        double k3 = solverStepSize*velocityX(0.5*solverStepSize);
        double k4 = solverStepSize*velocityX(solverStepSize);
        currentPosX = currentPosX + (1.0/6.0)*(k1+2*k2+2*k3+k4);

        k1 =solverStepSize*currentVelY;
        k2 = solverStepSize*velocityY(0.5*solverStepSize);
        k3 = solverStepSize*velocityY(0.5*solverStepSize);
        k4 = solverStepSize*velocityY(solverStepSize);
        currentPosY = currentPosY + (1.0/6.0)*(k1+2*k2+2*k3+k4);

        currentVelX = velocityX(solverStepSize);
        currentVelY = velocityY(solverStepSize);
    }
}
