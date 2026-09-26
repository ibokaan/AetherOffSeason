package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class SetElevatorPosition extends Command {
    private final ElevatorSubsystem elevator;
    private final double targetHeight;

    public SetElevatorPosition(ElevatorSubsystem elevator, double targetHeight) {
        this.elevator = elevator;
        this.targetHeight = targetHeight;
        addRequirements(elevator); // Bu komut calisirken baska bir komut asansoru cakisarak suremez
    }

    @Override
    public void execute() {
        elevator.goToHeight(targetHeight);
    }

    @Override
    public boolean isFinished() {
        // Hedefe ±2 cm yaklastiysa komut biter
        return Math.abs(elevator.getHeightMeters() - targetHeight) < 0.02;
    }

    @Override
    public void end(boolean interrupted) {
        // Komut bittiginde veya iptal edildiginde dur/sabit tut
        if (interrupted) {
            elevator.stop();
        }
    }
}