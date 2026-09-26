package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.constants.DriveConstants;

public class SwerveModule {
    private final SparkMax driveMotor;
    private final SparkMax turnMotor;

    private final PIDController turnPIDController = new PIDController(0.5, 0.0, 0.0);

    public SwerveModule(int driveCanId, int turnCanId, double encoderOffset) {
        driveMotor = new SparkMax(driveCanId, MotorType.kBrushless);
        turnMotor = new SparkMax(turnCanId, MotorType.kBrushless);

        // --- 1. SÜRÜŞ MOTORU KONFİGÜRASYONU (YENİ EKLENEN) ---
        SparkMaxConfig driveConfig = new SparkMaxConfig();
        driveConfig.encoder
            .positionConversionFactor(DriveConstants.kDriveEncoderPositionFactor)
            .velocityConversionFactor(DriveConstants.kDriveEncoderVelocityFactor);

        driveMotor.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // --- 2. DÖNÜŞ MOTORU KONFİGÜRASYONU (YENİ EKLENEN) ---
        SparkMaxConfig turnConfig = new SparkMaxConfig();
        turnConfig.encoder
            .positionConversionFactor(DriveConstants.kTurnEncoderPositionFactor)
            .velocityConversionFactor(DriveConstants.kTurnEncoderVelocityFactor);

        turnMotor.configure(turnConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // PID'nin -PI ile +PI arasında kesintisiz dönmesini sağlar
        turnPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            driveMotor.getEncoder().getVelocity(), // m/s cinsinden okur
            new Rotation2d(turnMotor.getEncoder().getPosition()) // radyan cinsinden okur
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            driveMotor.getEncoder().getPosition(), // Metre cinsinden okur
            new Rotation2d(turnMotor.getEncoder().getPosition()) // radyan cinsinden okur
        );
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        SwerveModuleState state = SwerveModuleState.optimize(desiredState, getState().angle);

        driveMotor.set(state.speedMetersPerSecond / DriveConstants.kMaxSpeedMetersPerSecond);
        double turnOutput = turnPIDController.calculate(getState().angle.getRadians(), state.angle.getRadians());
        turnMotor.set(turnOutput);
    }

    public void stop() {
        driveMotor.set(0);
        turnMotor.set(0);
    }
}