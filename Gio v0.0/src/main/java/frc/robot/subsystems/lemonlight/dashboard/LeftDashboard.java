package frc.robot.subsystems.lemonlight.dashboard;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
// import frc.robot.commands.autoCommands.LimelightAuto.*;
// import frc.robot.subsystems.lemonlight.lemonlightdos;
import frc.robot.subsystems.lemonlight.config.AutoScoreLeft;


public class LeftDashboard {
    

    private static DoubleEntry StrafeToleranceEntry;
    private static DoubleEntry StrafeCalcAEntry;
    private static DoubleEntry StrafeCalcBEntry;
    private static DoubleEntry StrafeCalcCEntry;
    private static DoubleEntry AngleTargetEntry;
    private static DoubleEntry AngleToleranceEntry;
    private static DoubleEntry DistanceTargetEntry;
    private static DoubleEntry DistanceToleranceEntry;

    private static NetworkTableInstance nti = NetworkTableInstance.getDefault();
    private static NetworkTable aslTable = nti.getTable("Auto Score Left");

    public static void AddDashboard() {


        //STRAFE CONFIG
        SmartDashboard.putData("AutoScoreLeft Strafe PID",RobotContainer.limelight.strafeController);
        StrafeToleranceEntry = addEntryWithValue("Strafe Tolerance", AutoScoreLeft.StrafeTolerance);
        //strafeTarget = A * (B ^ target_area) + C
        StrafeCalcAEntry = addEntryWithValue("Strafe TargetCalc A", AutoScoreLeft.StrafeFunctionAValue);
        StrafeCalcBEntry = addEntryWithValue("Strafe TargetCalc B", AutoScoreLeft.StrafeFunctionBValue);
        StrafeCalcCEntry = addEntryWithValue("Strafe TargetCalc C", AutoScoreLeft.StrafeFunctionCValue);

        //ANGLE CONFIG
        SmartDashboard.putData("AutoScoreLeft Angle PID", RobotContainer.limelight.angleController);
        AngleTargetEntry = addEntryWithValue("Angle Target",AutoScoreLeft.AngleTarget);
        AngleToleranceEntry = addEntryWithValue("Angle Tolerance", AutoScoreLeft.AngleTolerance);

        //DISTANCE CONFIG
        SmartDashboard.putData("AutoScoreLeft Distance PID",RobotContainer.limelight.distanceController);
        DistanceTargetEntry = addEntryWithValue("Distance Target", AutoScoreLeft.DistanceTarget);
        DistanceToleranceEntry = addEntryWithValue("Distance Tolerance", AutoScoreLeft.DistanceTolerance);
    }

    public static void syncDashboard() {
        AutoScoreLeft.StrafeTolerance = StrafeToleranceEntry.get(AutoScoreLeft.StrafeTolerance);
        AutoScoreLeft.StrafeFunctionAValue = StrafeCalcAEntry.get(AutoScoreLeft.StrafeFunctionAValue);
        AutoScoreLeft.StrafeFunctionBValue = StrafeCalcBEntry.get(AutoScoreLeft.StrafeFunctionBValue);
        AutoScoreLeft.StrafeFunctionCValue = StrafeCalcCEntry.get(AutoScoreLeft.StrafeFunctionCValue);
        AutoScoreLeft.AngleTarget = AngleTargetEntry.get(AutoScoreLeft.AngleTarget);
        AutoScoreLeft.AngleTolerance = AngleToleranceEntry.get(AutoScoreLeft.AngleTolerance);
        AutoScoreLeft.DistanceTarget = DistanceTargetEntry.get(AutoScoreLeft.DistanceTarget);
        AutoScoreLeft.DistanceTolerance = DistanceToleranceEntry.get(AutoScoreLeft.DistanceTolerance);
        return;

    }


    private static DoubleEntry addEntryWithValue(String name, double defaultValue) {
        DoubleEntry newEntry = aslTable.getDoubleTopic(name).getEntry(defaultValue);
        newEntry.set(defaultValue);
        return newEntry;
    }

    
}
