package frc.robot.constants;

public final class ElevatorConstants {
    // Motor CAN ID'leri
    public static final int kLeftMotorCanId = 9;
    public static final int kRightMotorCanId = 10;

    // Disli Oranlari ve Donusum Faktorleri
    public static final double kGearRatio = 10.0; // orn: 10:1 Reduktor
    public static final double kPulleyPitchDiameterMeters = 0.04; // Makara capi (metre)
    public static final double kPositionFactor = (Math.PI * kPulleyPitchDiameterMeters) / kGearRatio; // Rotasyonu metreye cevirir

    // Guvenlik Limitleri (Metre cinsinden)
    public static final double kMinHeightMeters = 0.0;
    public static final double kMaxHeightMeters = 1.2; // Maksimum asansor yuksekligi

    // PID ve Feedforward (kG: Asansorun agirligini yenmek icin verilen sabit voltaj)
    public static final double kP = 12.0; // Proportional Gain (Orantisal Kazanc)
    public static final double kI = 0.0; // Integral Gain (İntegral Kazanc)
    public static final double kD = 0.5; // Derivative Gain (Turev Kazanc)
    public static final double kG = 0.8; // Gravity Gain (Agirlik dengesi)
    public static final double kS = 0.2; // Static Gain (Statik Voltaj)
    public static final double kV = 1.0; // Velocity Gain (Hiz Voltaji)

    public static final double kHomePositionMeters = 0.0;
    public static final double kLowGoalMeters = 0.5;
    public static final double kHighGoalMeters = 1.2;
}