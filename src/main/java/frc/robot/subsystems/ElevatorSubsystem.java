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

    // Yavas kalkis ve durus icin Profil Tabanli PID Controller
    private final ProfiledPIDController pidController = new ProfiledPIDController(
        ElevatorConstants.kP, ElevatorConstants.kI, ElevatorConstants.kD,
        new TrapezoidProfile.Constraints(2.0, 4.0) // Max Hiz (m/s) ve Max İvme (m/s²)
    );

    // Feedforward: kS (statik), kG (yercekimi), kV (hiz)
    private final ElevatorFeedforward feedforward = new ElevatorFeedforward(
        ElevatorConstants.kS, 
        ElevatorConstants.kG, 
        ElevatorConstants.kV
    );

    public ElevatorSubsystem() {
        leftMotor = new SparkMax(ElevatorConstants.kLeftMotorCanId, MotorType.kBrushless);
        rightMotor = new SparkMax(ElevatorConstants.kRightMotorCanId, MotorType.kBrushless);

        SparkMaxConfig leftConfig = new SparkMaxConfig();
        SparkMaxConfig rightConfig = new SparkMaxConfig();

        // 1. Enkoder donusum katsayisini dogrudan SparkMax'e tanimliyoruz (Motor turunu Metreye cevirir)
        leftConfig.encoder
            .positionConversionFactor(ElevatorConstants.kPositionFactor)
            .velocityConversionFactor(ElevatorConstants.kPositionFactor / 60.0);

        // 2. Yazilimsal Limitler (Soft Limits) - Mekanik kirilmalari onlemek icin
        leftConfig.softLimit
            .forwardSoftLimit(ElevatorConstants.kMaxHeightMeters)
            .forwardSoftLimitEnabled(true)
            .reverseSoftLimit(ElevatorConstants.kMinHeightMeters)
            .reverseSoftLimitEnabled(true);

        // Sag motor sol motoru takip eder ve yonu ters cevrilir
        rightConfig.follow(leftMotor, true);

        // Konfigurasyonlari motorlara yukle
        leftMotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Baslangicta enkoderi sifirla
        leftMotor.getEncoder().setPosition(0);
    }

    // Yukseklik Okuma (Metre) - Artik dogrudan metre doner
    public double getHeightMeters() {
        return leftMotor.getEncoder().getPosition();
    }

    // Manuel Motor Surme (Joystick ile)
    public void setPower(double speed) {
        // Soft limit tanimlandigi icin direkt set edebiliriz
        leftMotor.set(speed);
    }

    // Kapali Devre Hedef Yukseklige Gitme (PID + Feedforward)
    public void goToHeight(double targetHeightMeters) {
        double pidOutput = pidController.calculate(getHeightMeters(), targetHeightMeters);
        
        // Trapzoid profilinin o anki hedef hizini kullanarak Feedforward hesaplama
        double ffOutput = feedforward.calculate(pidController.getSetpoint().velocity);
        
        // Voltaj uygulayarak surme
        leftMotor.setVoltage(pidOutput + ffOutput);
    }

    public void stop() {
        leftMotor.set(0);
    }
}