// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.Joystick;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private DriveSubsystem drive = new DriveSubsystem();
  private ElevatorSubsystem elevator = new ElevatorSubsystem();

  private Joystick joystick = new Joystick(Constants.JOYSTICK_PORT);
  private int otonomMod = 0;

  public RobotContainer() {
    drive.setDefaultCommand(new RunCommand(() -> drive.sur(-joystick.getRawAxis(1)), drive))
    joystick_ayari();


  }
  
  private void joystick_ayari() {
    //Joystick drive baglantisi

    new JoystickButton(joystick, 3).whileTrue(new RunCommand(() -> elevator.sur(0.5), elevator)).onFalse(new RunCommand(() -> elevator.sur(0.0), elevator));

    new JoystickButton(joystick, 4).whileTrue(new RunCommand(() -> elevator.sur(-0.3), elevator)).onFalse(new RunCommand(() -> elevator.sur(0.0), elevator));


  }
 
}
