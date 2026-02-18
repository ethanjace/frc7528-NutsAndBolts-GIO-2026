// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OuttakeConstants;

public class Outtake extends SubsystemBase {
  /** Creates a new Outtake. */

  //DECLARE
  private TalonFX outtake = new TalonFX(OuttakeConstants.OUTTAKE_ID);
  public Outtake() {
    outtake.getConfigurator().apply(OuttakeConstants.configs);
    outtake.getConfigurator().refresh(OuttakeConstants.currentLimits);
    outtake.getConfigurator().apply(OuttakeConstants.currentLimits);
  }

  public void takeOutTheTrash(double speed) {
    outtake.set(speed);
  }
  @Override
  public void periodic() {
  }
}
