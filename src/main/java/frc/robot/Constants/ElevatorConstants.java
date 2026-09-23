package frc.robot.constants;

public final class ElevatorConstants {
    // Motor CAN ID'leri
    public static final int kLeftMotorCanId = 9;
    public static final int kRightMotorCanId = 10;

    // Dişli Oranları ve Dönüşüm Faktörleri
    public static final double kGearRatio = 10.0; // Örn: 10:1 Redüktör
    public static final double kPulleyPitchDiameterMeters = 0.04; // Makara çapı (metre)
    public static final double kPositionFactor = (Math.PI * kPulleyPitchDiameterMeters) / kGearRatio; // Rotasyonu metreye çevirir

    // Güvenlik Limitleri (Metre cinsinden)
    public static final double kMinHeightMeters = 0.0;
    public static final double kMaxHeightMeters = 1.2; // Maksimum asansör yüksekliği

    // PID ve Feedforward (kG: Asansörün ağırlığını yenmek için verilen sabit voltaj)
    public static final double kP = 12.0;
    public static final double kI = 0.0;
    public static final double kD = 0.5;
    public static final double kG = 0.8; // Gravity Gain (Ağırlık dengesi)
}