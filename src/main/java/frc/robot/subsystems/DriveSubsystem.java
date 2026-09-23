// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
    private final SwerveModule frontLeft = new SwerveModule(
        DriveConstants.kFrontLeftDriveCanId, DriveConstants.kFrontLeftTurnCanId, DriveConstants.kFrontLeftEncoderOffset);
    private final SwerveModule frontRight = new SwerveModule(
        DriveConstants.kFrontRightDriveCanId, DriveConstants.kFrontRightTurnCanId, DriveConstants.kFrontRightEncoderOffset);
    private final SwerveModule backLeft = new SwerveModule(
        DriveConstants.kBackLeftDriveCanId, DriveConstants.kBackLeftTurnCanId, DriveConstants.kBackLeftEncoderOffset);
    private final SwerveModule backRight = new SwerveModule(
        DriveConstants.kBackRightDriveCanId, DriveConstants.kBackRightTurnCanId, DriveConstants.kBackRightEncoderOffset);

    private final Pigeon2 gyro = new Pigeon2(DriveConstants.kPigeonCanId);

    public DriveSubsystem() {
        zeroHeading();
    }

    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        SwerveModuleState[] swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(
            fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, getHeading())
                : new ChassisSpeeds(xSpeed, ySpeed, rot)
        );

        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);

        frontLeft.setDesiredState(swerveModuleStates[0]);
        frontRight.setDesiredState(swerveModuleStates[1]);
        backLeft.setDesiredState(swerveModuleStates[2]);
        backRight.setDesiredState(swerveModuleStates[3]);
    }

    public void zeroHeading() {
        gyro.reset();
    }

    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(gyro.getYaw().getValueAsDouble());
    }
}