package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.ElevatorConstants;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.subsystems.ElevatorSubsystem;

public class RobotContainer {
    private final ElevatorSubsystem elevator = new ElevatorSubsystem();
    private final CommandXboxController driverController = new CommandXboxController(0);

    public RobotContainer() {
        configureButtonBindings();

        // Varsayılan Sürüş: Joystick Sol Y ekseni ile manuel asansör kontrolü
        elevator.setDefaultCommand(
            new RunCommand(
                () -> elevator.setPower(-driverController.getLeftY()),
                elevator
            )
        );
    }

    private void configureButtonBindings() {
        // 'A' Butonu -> Tabana İndir
        driverController.a().onTrue(
            new SetElevatorPosition(elevator, ElevatorConstants.kHomePositionMeters)
        );

        // 'X' Butonu -> Düşük Hedef Seviyesi (Low Goal)
        driverController.x().onTrue(
            new SetElevatorPosition(elevator, ElevatorConstants.kLowGoalMeters)
        );

        // 'Y' Butonu -> Yüksek Hedef Seviyesi (High Goal)
        driverController.y().onTrue(
            new SetElevatorPosition(elevator, ElevatorConstants.kHighGoalMeters)
        );
    }

    public Command getAutonomousCommand() {
        // Otonom periyotta asansörü doğrudan yüksek hedefe kaldırır
        return new SetElevatorPosition(elevator, ElevatorConstants.kHighGoalMeters);
    }
}