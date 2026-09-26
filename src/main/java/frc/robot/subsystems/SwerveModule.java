package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.constants.DriveConstants;

public class SwerveModule {
    private final SparkMax driveMotor;
    private final SparkMax turnMotor;

    private final double encoderOffset; // Offset saklamak icin eklendi

    private final PIDController turnPIDController = new PIDController(0.5, 0.0, 0.0);

    public SwerveModule(int driveCanId, int turnCanId, double encoderOffset) {
        this.encoderOffset = encoderOffset;

        driveMotor = new SparkMax(driveCanId, MotorType.kBrushless);
        turnMotor = new SparkMax(turnCanId, MotorType.kBrushless);

        // --- 1. SuRus MOTORU KONFİGuRASYONU ---
        SparkMaxConfig driveConfig = new SparkMaxConfig();
        driveConfig.encoder
            .positionConversionFactor(DriveConstants.kDriveEncoderPositionFactor)
            .velocityConversionFactor(DriveConstants.kDriveEncoderVelocityFactor);

        driveMotor.configure(driveConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // --- 2. DoNus MOTORU KONFİGuRASYONU ---
        SparkMaxConfig turnConfig = new SparkMaxConfig();
        turnConfig.encoder
            .positionConversionFactor(DriveConstants.kTurnEncoderPositionFactor)
            .velocityConversionFactor(DriveConstants.kTurnEncoderVelocityFactor);

        turnMotor.configure(turnConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // PID'nin -PI ile +PI radyan arasinda kesintisiz donmesini saglar
        turnPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    /** Modulun anlik donme acisini offset dahil hesaplar (Radyan) */
    private Rotation2d getTurnAngle() {
        // Enkoder konumundan offset cikarilarak gercek aci bulunur
        double rawRadians = turnMotor.getEncoder().getPosition();
        return Rotation2d.fromRadians(rawRadians - encoderOffset);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            driveMotor.getEncoder().getVelocity(), // m/s
            getTurnAngle()
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            driveMotor.getEncoder().getPosition(), // metre
            getTurnAngle()
        );
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        // 1. En kisa donus rotasini hesapla (Optimize et)
        SwerveModuleState state = SwerveModuleState.optimize(desiredState, getTurnAngle());

        // 2. Surus motoru gucu (-1.0 ile 1.0 arasi)
        double driveOutput = state.speedMetersPerSecond / DriveConstants.kMaxSpeedMetersPerSecond;
        driveMotor.set(driveOutput);

        // 3. Donus motoru PID hesabi ve sinirlandirma (Clamp)
        double turnOutput = turnPIDController.calculate(getTurnAngle().getRadians(), state.angle.getRadians());
        turnOutput = MathUtil.clamp(turnOutput, -1.0, 1.0); // Motor gucunun %100'u asmasini onler
        
        turnMotor.set(turnOutput);
    }

    public void stop() {
        driveMotor.set(0);
        turnMotor.set(0);
    }
}