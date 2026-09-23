package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {
    private final SparkMax driveMotor;
    private final SparkMax turnMotor;

    private final PIDController turnPIDController = new PIDController(0.5, 0.0, 0.0);

    public SwerveModule(int driveCanId, int turnCanId, double encoderOffset) {
        driveMotor = new SparkMax(driveCanId, MotorType.kBrushless);
        turnMotor = new SparkMax(turnCanId, MotorType.kBrushless);

        // PID'nin 360 derece sürekliliği (Continuous Input)
        turnPIDController.enableContinuousInput(-Math.PI, Math.PI);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(
            driveMotor.getEncoder().getVelocity(),
            new Rotation2d(turnMotor.getEncoder().getPosition())
        );
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
            driveMotor.getEncoder().getPosition(),
            new Rotation2d(turnMotor.getEncoder().getPosition())
        );
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        // En kısa dönüş yolunu hesaplar (Optimize)
        SwerveModuleState state = SwerveModuleState.optimize(desiredState, getState().angle);

        driveMotor.set(state.speedMetersPerSecond / 4.5);
        double turnOutput = turnPIDController.calculate(getState().angle.getRadians(), state.angle.getRadians());
        turnMotor.set(turnOutput);
    }

    public void stop() {
        driveMotor.set(0);
        turnMotor.set(0);
    }
}