package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

// Kendi olusturdugumuz paketler:
import frc.robot.constants.Constants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.commands.drive.TeleopDriveCommand;
import frc.robot.commands.drive.ZeroGyroCommand;
import frc.robot.commands.elevator.SetElevatorPosition;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

public class RobotContainer {
    // 1. Alt Sistem Tanimlamalari (Hem Swerve hem Elevator)
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final ElevatorSubsystem elevator = new ElevatorSubsystem();

    // 2. Surucu Kumandasi
    private final CommandXboxController driverController = 
        new CommandXboxController(Constants.kDriverControllerPort);

    public RobotContainer() {
        // Swerve Varsayilan Surus (Sol Analog = İlerleme/Kayma, Sag Analog = Donus)
        driveSubsystem.setDefaultCommand(
            new TeleopDriveCommand(
                driveSubsystem,
                () -> -driverController.getLeftY() * 4.5,
                () -> -driverController.getLeftX() * 4.5,
                () -> -driverController.getRightX() * Math.PI
            )
        );

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        // Gyro (on Yon) Sifirlama -> Start Butonu
        driverController.start().onTrue(new ZeroGyroCommand(driveSubsystem));

        // Asansor Yukseklik Seviyeleri (Buton atamalari)
        driverController.a().onTrue(new SetElevatorPosition(elevator, ElevatorConstants.kHomePositionMeters));
        driverController.x().onTrue(new SetElevatorPosition(elevator, ElevatorConstants.kLowGoalMeters));
        driverController.y().onTrue(new SetElevatorPosition(elevator, ElevatorConstants.kHighGoalMeters));
    }

    public Command getAutonomousCommand() {
        // Otonom periyotta asansoru kaldirir
        return new SetElevatorPosition(elevator, ElevatorConstants.kHighGoalMeters);
    }
}