package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class SetElevatorPosition extends Command {
    private final ElevatorSubsystem elevator;
    private final double targetHeight;

    public SetElevatorPosition(ElevatorSubsystem elevator, double targetHeight) {
        this.elevator = elevator;
        this.targetHeight = targetHeight;
        addRequirements(elevator); // Bu komut çalışırken başka bir komut asansörü çakışarak süremez
    }

    @Override
    public void execute() {
        elevator.goToHeight(targetHeight);
    }

    @Override
    public boolean isFinished() {
        // Hedefe ±2 cm yaklaştıysa komut biter
        return Math.abs(elevator.getHeightMeters() - targetHeight) < 0.02;
    }

    @Override
    public void end(boolean interrupted) {
        // Komut bittiğinde veya iptal edildiğinde dur/sabit tut
        if (interrupted) {
            elevator.stop();
        }
    }
}