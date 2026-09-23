package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase {
    private final SparkMax leftMotor;
    private final SparkMax rightMotor;

    // Yavaş kalkış ve duruş için Profil Tabanlı PID
    private final ProfiledPIDController pidController = new ProfiledPIDController(
        ElevatorConstants.kP, ElevatorConstants.kI, ElevatorConstants.kD,
        new TrapezoidProfile.Constraints(2.0, 4.0) // Maksimum Hız ve İvme
    );

    private final ElevatorFeedforward feedforward = new ElevatorFeedforward(0, ElevatorConstants.kG, 0);

    public ElevatorSubsystem() {
        leftMotor = new SparkMax(ElevatorConstants.kLeftMotorCanId, MotorType.kBrushless);
        rightMotor = new SparkMax(ElevatorConstants.kRightMotorCanId, MotorType.kBrushless);

        // Motor Konfigürasyonları
        SparkMaxConfig leftConfig = new SparkMaxConfig();
        SparkMaxConfig rightConfig = new SparkMaxConfig();

        // Sağ motoru sol motorun takipçisi yap ve yönünü ters çevir
        rightConfig.follow(leftMotor, true);

        leftMotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Enkoderi sıfırla
        leftMotor.getEncoder().setPosition(0);
    }

    // Yükseklik Okuma (Metre)
    public double getHeightMeters() {
        return leftMotor.getEncoder().getPosition() * ElevatorConstants.kPositionFactor;
    }

    // Manuel Motor Sürme (Joystick ile)
    public void setPower(double speed) {
        // Limit Kontrolü
        if ((getHeightMeters() >= ElevatorConstants.kMaxHeightMeters && speed > 0) ||
            (getHeightMeters() <= ElevatorConstants.kMinHeightMeters && speed < 0)) {
            leftMotor.set(0);
        } else {
            leftMotor.set(speed);
        }
    }

    // Kapalı Devre Hedef Yüksekliğe Gitme (PID)
    public void goToHeight(double targetHeightMeters) {
        double pidOutput = pidController.calculate(getHeightMeters(), targetHeightMeters);
        double ffOutput = feedforward.calculate(pidController.getSetpoint().velocity);
        
        leftMotor.setVoltage(pidOutput + ffOutput);
    }

    public void stop() {
        leftMotor.set(0);
    }
}