package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot { // Ana robot sinifi, robotun yasam dongusunu yonetir.
    private Command autonomousCommand;
    private RobotContainer robotContainer;

    @Override // Robot baslatildiginda cagrilir.
    public void robotInit() { 
        robotContainer = new RobotContainer(); 
    }

    @Override // Her zaman cagrilir, robotun durumunu gunceller.
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override // Otonom periyod baslatildiginda cagrilir.
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) { // Otonom komut varsa, onu baslat.
            autonomousCommand.schedule();
        }
    }

    @Override 
    public void teleopInit() { // Teleoperated periyod baslatildiginda cagrilir.
        if (autonomousCommand != null) { // Otonom komut calisiyorsa, onu iptal et.
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {}

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll(); // Test moduna gecerken tum komutlari iptal et.
    }
}