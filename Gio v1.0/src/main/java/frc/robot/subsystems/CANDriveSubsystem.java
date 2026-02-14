// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.swerve.SwerveRequest;
// // import com.revrobotics.spark.SparkBase.PersistMode;
// // import com.revrobotics.spark.SparkBase.ResetMode;
// // import com.revrobotics.spark.SparkLowLevel.MotorType;
// // import com.revrobotics.spark.SparkMax;
// // import com.revrobotics.spark.config.SparkMaxConfig;

// import com.ctre.phoenix6.swerve.SwerveRequest;

// import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import static frc.robot.Constants.DriveConstants.*;

// public class CANDriveSubsystem extends SubsystemBase {
//   private final SparkMax leftLeader;
//   private final SparkMax leftFollower;
//   private final SparkMax rightLeader;
//   private final SparkMax rightFollower;

//   private final DifferentialDrive drive;

//   public CANDriveSubsystem() {

//     final SwerveRequest.ApplyRobotSpeeds m_pathApplyRobotSpeeds = new SwerveRequest.ApplyRobotSpeeds();
//     // create brushed motors for drive
//     TalonFX leftLeader = new TalonFX(LEFT_LEADER_ID);
//     TalonFX leftFollower = new TalonFX(LEFT_FOLLOWER_ID);
//     TalonFX rightLeader = new TalonFX(RIGHT_LEADER_ID);
//     TalonFX rightFollower = new TalonFX(RIGHT_FOLLOWER_ID);


//     // Set can timeout. Because this project only sets parameters once on
//     // construction, the timeout can be long without blocking robot operation. Code
//     // which sets or gets parameters during operation may need a shorter timeout.
//     // leftLeader.set(250);
//     // rightLeader.set(250);
//     // leftFollower.set(250);
//     // rightFollower.set(250);

//     // Create the configuration to apply to motors. Voltage compensation
//     // helps the robot perform more similarly on different
//     // battery voltages (at the cost of a little bit of top speed on a fully charged
//     // battery). The current limit helps prevent tripping
//     // breakers.
//     SparkMaxConfig config = new SparkMaxConfig();
//     config.voltageCompensation(12);
//     config.smartCurrentLimit(DRIVE_MOTOR_CURRENT_LIMIT);

//     // Set configuration to follow each leader and then apply it to corresponding
//     // follower. Resetting in case a new controller is swapped
//     // in and persisting in case of a controller reset due to breaker trip
//     config.follow(leftLeader);
//     leftFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
//     config.follow(rightLeader);
//     rightFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

//     // Remove following, then apply config to right leader
//     config.disableFollowerMode();
//     rightLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
//     // Set config to inverted and then apply to left leader. Set Left side inverted
//     // so that postive values drive both sides forward
//     config.inverted(true);
//     leftLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
//   }

//   @Override
//   public void periodic() {
//   }

//   public void driveArcade(double xSpeed, double zRotation) {
//     drive.arcadeDrive(xSpeed, zRotation);
//   }

// }
