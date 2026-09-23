package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveSubsystem;

public class ZeroGyroCommand extends InstantCommand { 
    public ZeroGyroCommand(DriveSubsystem driveSubsystem) {
        super(driveSubsystem::zeroHeading, driveSubsystem);
    }
}