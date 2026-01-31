package frc.robot.subsystems.lemonlight;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.lemonlight.config.AutoScoreRightConfig;

public class lemonlightuno extends SubsystemBase {

   private double ta;
   private double tx;
   private double ty;
   private double tl;
   private double ts;
   private double[] tAng;
   private int tv;

   NetworkTableEntry prelimtx;
   NetworkTableEntry prelimty;
   NetworkTableEntry prelimta;
   NetworkTableEntry prelimtl;
   NetworkTableEntry prelimts;
   NetworkTableEntry prelimtAng;
   NetworkTableEntry prelimtv;
   NetworkTableEntry prelimCamtran;
   NetworkTable table;
   NetworkTableInstance Inst;

   public final PIDController angleController = new PIDController(AutoScoreRightConfig.AnglePID.P, AutoScoreRightConfig.AnglePID.I,AutoScoreRightConfig.AnglePID.D); // needs to be tuned
   public final PIDController strafeController = new PIDController(AutoScoreRightConfig.StrafePID.P,AutoScoreRightConfig.StrafePID.I,AutoScoreRightConfig.StrafePID.D);
   public final PIDController distanceController = new PIDController(AutoScoreRightConfig.DistancePID.P, AutoScoreRightConfig.DistancePID.I,AutoScoreRightConfig.DistancePID.D);

   public lemonlightuno() {
      Inst = NetworkTableInstance.getDefault();
      table = Inst.getTable(LimelightIDs.Limelight.RightTableName);
      prelimta = table.getEntry("ta");
      prelimtx = table.getEntry("tx");
      prelimty = table.getEntry("ty");
      prelimtl = table.getEntry("tlong");
      prelimts = table.getEntry("tshort");
      prelimtAng = table.getEntry("botpose_targetspace");
      prelimtv = table.getEntry("tv");

      angleController.setTolerance(AutoScoreRightConfig.AngleTolerance);  // needs to be tuned
      strafeController.setTolerance(AutoScoreRightConfig.StrafeTolerance);
      distanceController.setTolerance(AutoScoreRightConfig.DistanceTolerance);
   }

   public void updateGameState(){
      ta = prelimta.getDouble(ta);
      tx = prelimtx.getDouble(0);
      ty = prelimty.getDouble(ty);
      tl = prelimtl.getDouble(tl);
      ts = prelimts.getDouble(ts);
      tv = (int) prelimtv.getInteger(tv);
      tAng = prelimtAng.getDoubleArray(new double[6]);
   }

   public double getArea(){
      ta = prelimta.getDouble(ta);
      return ta;
   }

   public double getX(){
      tx = prelimtx.getDouble(tx);
      return tx;
   }

   public double getY(){
      ty = prelimty.getDouble(ty);
      return ty;
   }

   public double getShort() {
      ts = prelimts.getDouble(ts);
      return ts;
   }

   public double getLong() {
      tl = prelimtl.getDouble(tl);
      return tl;
   }

   public double gettAng() {
      tAng = prelimtAng.getDoubleArray(new double[6]);
      double actAng = tAng[4];
      return actAng;
   }

   public boolean ifValidTag() {
      tv = (int) prelimtv.getInteger(tv);
      if (tv == 1) {
         return true;   
      }
      else {
         return false;
      }
      
   }

   public void visionMode(){
      NetworkTableInstance.getDefault().getTable("limelight-right").getEntry("ledMode").setNumber(3);
      NetworkTableInstance.getDefault().getTable("limelight-right").getEntry("camMode").setNumber(0);
   }

   public void cameraMode(){
      NetworkTableInstance.getDefault().getTable("limelight-right").getEntry("ledMode").setNumber(1);
      NetworkTableInstance.getDefault().getTable("limelight-right").getEntry("camMode").setNumber(1);
   }

   public void updateDashboard() {
	   SmartDashboard.putNumber("Right ta", getArea());
      SmartDashboard.putNumber("Right tx", getX());
      SmartDashboard.putNumber("Right ty", getY());
      SmartDashboard.putNumber("Right tl", getLong());
      SmartDashboard.putNumber("Right ts", getShort());
      SmartDashboard.putNumber("Right tAng", gettAng());
      SmartDashboard.putBoolean("Right tv", ifValidTag());

	}
}
