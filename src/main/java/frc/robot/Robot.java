package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot { // Ana robot sınıfı, robotun yaşam döngüsünü yönetir.
    private Command autonomousCommand;
    private RobotContainer robotContainer;

    @Override // Robot başlatıldığında çağrılır.
    public void robotInit() { 
        robotContainer = new RobotContainer(); 
    }

    @Override // Her zaman çağrılır, robotun durumunu günceller.
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override // Otonom periyod başlatıldığında çağrılır.
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) { // Otonom komut varsa, onu başlat.
            autonomousCommand.schedule();
        }
    }

    @Override 
    public void teleopInit() { // Teleoperated periyod başlatıldığında çağrılır.
        if (autonomousCommand != null) { // Otonom komut çalışıyorsa, onu iptal et.
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll(); // Test moduna geçerken tüm komutları iptal et.
    }
}