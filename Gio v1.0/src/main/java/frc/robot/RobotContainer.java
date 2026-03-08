// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import static edu.wpi.first.units.Units.*;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import static frc.robot.Constants.OperatorConstants.*;

import java.security.cert.LDAPCertStoreParameters;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.commands.IntakeUp;
import frc.robot.commands.OuttakeActivate;
import frc.robot.commands.AutoCommands.AutoIntake;
import frc.robot.commands.AutoCommands.AutoIntakeDisengage;
import frc.robot.commands.AutoCommands.AutoIntakeEngage;
import frc.robot.commands.AutoCommands.AutoShoot;
import frc.robot.commands.IntakeDown;
import frc.robot.commands.IntakeGo;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Outtake;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.lemonlight.Lemonlightuno;
import frc.robot.generated.*;


/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {


  public double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
  public double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

  // SUBSYSTEMS   ---   ---   ---   ---   ---
  public static Lemonlightuno limelight = new Lemonlightuno();
  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
  private final Intake intake = new Intake();
  private final Outtake outtake = new Outtake();

  // DRIVER   ---   ---   ---   ---   ---
  private final Telemetry logger = new Telemetry(MaxSpeed);
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
  private final SwerveRequest.RobotCentric rDrive = new SwerveRequest.RobotCentric();
  
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
  

    

  // CONTROLLERS    ---   ---   ---   ---   ---
  private final CommandXboxController driverController = new CommandXboxController(0);
  private final CommandXboxController operatorController = new CommandXboxController(1);

  // AUTO
  private final SendableChooser<String> autoChooser = new SendableChooser<>();

  

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureBindings();

    // AUTO
    Shuffleboard.getTab("AUTO")
                .add("SELECTION: ", autoChooser)
                .withWidget(BuiltInWidgets.kComboBoxChooser)
                .withPosition(0, 0)
                .withSize(2, 1);
    
    Shuffleboard.getTab("AUTO")
                .add("on RED ALLIANCE: ", false)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .getEntry();

    // AUTO COMMANDS
    NamedCommands.registerCommand("AutoIntake", new AutoIntake(intake, 2));
    NamedCommands.registerCommand("AutoShoot", new AutoShoot(outtake, 1));
    NamedCommands.registerCommand("AutoIntakeEngage", new AutoIntakeEngage(intake, 1));
    NamedCommands.registerCommand("AutoIntakeDisengage", new AutoIntakeDisengage(intake, 1));
    
    // AUTO SELECTOR (In Shuffleboard)
    autoChooser.setDefaultOption("TestAuto", "TestAuto");
    autoChooser.addOption("TestAutoShoot", "TestAutoShoot");
    autoChooser.addOption("Auto2", "Auto2");
    //add rest of auto options after testing!!

    SmartDashboard.putData("So many choices: ", autoChooser);

    

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the {@link Trigger#Trigger(java.util.function.BooleanSupplier)}
   * constructor with an arbitrary predicate, or via the named factories in
   * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses
   * for {@link CommandXboxController Xbox}/
   * {@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() { 

    // DRIVE
    drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                     .withVelocityY(driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)  
                     .withRotationalRate(-driverController.getRightX() * MaxAngularRate) // Rotate clockwise with positive X (right)
            )
        );
    
    // CONTROLS
    
    // INTAKE MECHANISM
    driverController.leftTrigger().whileTrue(new IntakeGo(intake));   // Runs Intake [ LT ]
    driverController.a().whileTrue(new IntakeUp(intake));   // Disengages Intake     [ A ]     // Maybe change these two to Auto command?
    driverController.b().whileTrue(new IntakeDown(intake)); // Engages Intake        [ B ]     //
    
    // OUTTAKE MECHANISM
    driverController.rightTrigger().whileTrue(new OuttakeActivate(outtake)); // Runs Outtake  [ RT ]

    // CLIMB MECHANISM    [ LB ] / [ RB ]
    

    // LIMELIGHT (probably doesn't work)
    driverController.leftBumper()
    .whileTrue(
          drivetrain.applyRequest(() ->
          rDrive.withVelocityX(limelight.getY() * -0.1)
                .withVelocityY(limelight.getX() * -0.05)));
    }
    
  

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    String selectedAuto = autoChooser.getSelected();
    return drivetrain.getAutonomousCommand(selectedAuto);
  }
}
 