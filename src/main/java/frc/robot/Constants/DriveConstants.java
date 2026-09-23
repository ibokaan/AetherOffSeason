package frc.robot.constants;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public final class DriveConstants {
    // Gyro CAN ID (Pigeon2 veya NavX)
    public static final int kPigeonCanId = 13;

    // Şasi Boyutları (Tekerler arası mesafe - metre)
    public static final double kTrackWidth = 0.60;  // Sol-sağ teker arası
    public static final double kWheelBase = 0.60;   // Ön-arka teker arası

    // Swerve Kinematiği (Tekerlek konumları)
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),   // Ön Sol
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),  // Ön Sağ
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),  // Arka Sol
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2)  // Arka Sağ
    );

    // Ön Sol Modül CAN ID'leri
    public static final int kFrontLeftDriveCanId = 1;
    public static final int kFrontLeftTurnCanId = 2;
    public static final double kFrontLeftEncoderOffset = 0.0;

    // Ön Sağ Modül CAN ID'leri
    public static final int kFrontRightDriveCanId = 3;
    public static final int kFrontRightTurnCanId = 4;
    public static final double kFrontRightEncoderOffset = 0.0;

    // Arka Sol Modül CAN ID'leri
    public static final int kBackLeftDriveCanId = 5;
    public static final int kBackLeftTurnCanId = 6;
    public static final double kBackLeftEncoderOffset = 0.0;

    // Arka Sağ Modül CAN ID'leri
    public static final int kBackRightDriveCanId = 7;
    public static final int kBackRightTurnCanId = 8;
    public static final double kBackRightEncoderOffset = 0.0;

    // Sürüş Limitleri
    public static final double kMaxSpeedMetersPerSecond = 4.5;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // rad/s
}