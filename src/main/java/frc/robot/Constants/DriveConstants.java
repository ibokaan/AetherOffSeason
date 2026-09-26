package frc.robot.constants;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public final class DriveConstants {
    // Gyro CAN ID (Pigeon2 veya NavX)
    public static final int kPigeonCanId = 13;

    // sasi Boyutlari (Tekerler arasi mesafe - metre)
    public static final double kTrackWidth = 0.60;  // Sol-sag teker arasi
    public static final double kWheelBase = 0.60;   // On-arka teker arasi

    // Swerve Kinematigi (Tekerlek konumlari)
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),   // On Sol
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),  // On Sag
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),  // Arka Sol
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2)  // Arka Sag
    );

    // On Sol Modul CAN ID'leri
    public static final int kFrontLeftDriveCanId = 1;
    public static final int kFrontLeftTurnCanId = 2;
    public static final double kFrontLeftEncoderOffset = 0.0;

    // On Sag Modul CAN ID'leri
    public static final int kFrontRightDriveCanId = 3;
    public static final int kFrontRightTurnCanId = 4;
    public static final double kFrontRightEncoderOffset = 0.0;

    // Arka Sol Modul CAN ID'leri
    public static final int kBackLeftDriveCanId = 5;
    public static final int kBackLeftTurnCanId = 6;
    public static final double kBackLeftEncoderOffset = 0.0;

    // Arka Sag Modul CAN ID'leri
    public static final int kBackRightDriveCanId = 7;
    public static final int kBackRightTurnCanId = 8;
    public static final double kBackRightEncoderOffset = 0.0;

    // Surus Limitleri
    public static final double kMaxSpeedMetersPerSecond = 4.5;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // rad/s

    public static final double kTurnGearRatio = 150.0 / 7.0; // Orn: MK4i Steering Gear Ratio
    public static final double kTurnEncoderPositionFactor = (2 * Math.PI) / kTurnGearRatio; // Motor turunu tekerlek radyanina cevirir
    public static final double kTurnEncoderVelocityFactor = kTurnEncoderPositionFactor / 60.0; // RPM'i rad/s'ye cevirir

    // Swerve Modul Disli Oranlari ve Olculeri
    public static final double kWheelDiameterMeters = 0.1016; // 4 inc tekerlek = ~0.1016m
    public static final double kDriveGearRatio = 6.75; // Orn: MK4i L2 Drive Gear Ratio
    public static final double kTurnGearRatio = 150.0 / 7.0; // Orn: MK4i Turn Gear Ratio

    // Encoder DOnusturme FaktOrleri
    // Surus Motoru: Rotasyonu Metreye cevirir
    public static final double kDriveEncoderPositionFactor = (Math.PI * kWheelDiameterMeters) / kDriveGearRatio;
    public static final double kDriveEncoderVelocityFactor = kDriveEncoderPositionFactor / 60.0; // RPM -> m/s

    // DOnus Motoru: Rotasyonu Radyana cevirir
    public static final double kTurnEncoderPositionFactor = (2 * Math.PI) / kTurnGearRatio;
    public static final double kTurnEncoderVelocityFactor = kTurnEncoderPositionFactor / 60.0; // RPM -> rad/s
}