package frc.robot.commands.drive;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;

public class TeleopDriveCommand extends Command {
    private final DriveSubsystem driveSubsystem;
    private final DoubleSupplier translationSup;
    private final DoubleSupplier strafeSup;
    private final DoubleSupplier rotationSup;

    public TeleopDriveCommand( // TeleopDriveCommand yapıcı
            DriveSubsystem driveSubsystem,
            DoubleSupplier translationSup,
            DoubleSupplier strafeSup,
            DoubleSupplier rotationSup) {
        this.driveSubsystem = driveSubsystem;
        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        addRequirements(driveSubsystem);
    }

    @Override // Bu Komut çalıştırılmak üzere zamanlandığında art arda çağrılır.
    public void execute() { 
        driveSubsystem.drive(
            translationSup.getAsDouble(),
            strafeSup.getAsDouble(),
            rotationSup.getAsDouble(),
            true // Field-Centric (Sürücüye göre yön)
        );
    }
}