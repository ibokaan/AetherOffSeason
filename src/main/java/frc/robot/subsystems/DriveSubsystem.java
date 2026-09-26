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

public class DriveSubsystem extends SubsystemBase {// Swerve modulunun surus ve donus motorlarini ve enkoder ofsetini baslatir
    private final SwerveModule frontLeft = new SwerveModule(DriveConstants.kFrontLeftDriveCanId, DriveConstants.kFrontLeftTurnCanId, DriveConstants.kFrontLeftEncoderOffset);
    private final SwerveModule frontRight = new SwerveModule(DriveConstants.kFrontRightDriveCanId, DriveConstants.kFrontRightTurnCanId, DriveConstants.kFrontRightEncoderOffset); 
    private final SwerveModule backLeft = new SwerveModule(DriveConstants.kBackLeftDriveCanId, DriveConstants.kBackLeftTurnCanId, DriveConstants.kBackLeftEncoderOffset);
    private final SwerveModule backRight = new SwerveModule(DriveConstants.kBackRightDriveCanId, DriveConstants.kBackRightTurnCanId, DriveConstants.kBackRightEncoderOffset);

    private final Pigeon2 gyro = new Pigeon2(DriveConstants.kPigeonCanId); // Gyro (Pigeon2) sensoru, robotun yonunu ve acisini olcmek icin kullanilir.

    public DriveSubsystem() {
        zeroHeading();
    }

    public void zeroHeading() {
        gyro.setYaw(0.0); // Gyro acisini sifirlar
    }

    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        // 1. ChassisSpeeds nesnesini olustur
        ChassisSpeeds speeds = fieldRelative
                ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, getRotation2d()) // Rotation2d olmali
                : new ChassisSpeeds(xSpeed, ySpeed, rot);

        // 2. onus sirasindaki eksen kaymasini onlemek icin discretize uygular
        speeds = ChassisSpeeds.discretize(speeds, 0.02);

        // 3. ChassisSpeeds'i SwerveModuleState dizisine cevirir
        SwerveModuleState[] swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(speeds);

        // 4. Tekerlek hiz sinirlarini asmamasi icin normalize eder
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DriveConstants.kMaxSpeedMetersPerSecond);

        // 5. Her module durumlari gonderir
        frontLeft.setDesiredState(swerveModuleStates[0]);
        frontRight.setDesiredState(swerveModuleStates[1]);
        backLeft.setDesiredState(swerveModuleStates[2]);
        backRight.setDesiredState(swerveModuleStates[3]);
        
}
}