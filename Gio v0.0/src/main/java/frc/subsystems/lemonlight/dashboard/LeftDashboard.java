package frc.robot.subsystems.lemonlight.dashboard;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.commands.autoCommands.LimelightAuto.*;
import frc.robot.subsystems.lemonlight.lemonlightdos;
import frc.robot.subsystems.lemonlight.config.AutoScoreLeftconfig;

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
        StrafeToleranceEntry = addEntryWithValue("Strafe Tolerance", AutoScoreLeftconfig.StrafeTolerance);
        //strafeTarget = A * (B ^ target_area) + C
        StrafeCalcAEntry = addEntryWithValue("Strafe TargetCalc A", AutoScoreLeftconfig.StrafeFunctionAValue);
        StrafeCalcBEntry = addEntryWithValue("Strafe TargetCalc B", AutoScoreLeftconfig.StrafeFunctionBValue);
        StrafeCalcCEntry = addEntryWithValue("Strafe TargetCalc C", AutoScoreLeftconfig.StrafeFunctionCValue);

        //ANGLE CONFIG
        SmartDashboard.putData("AutoScoreLeft Angle PID", RobotContainer.limelight.angleController);
        AngleTargetEntry = addEntryWithValue("Angle Target",AutoScoreLeftconfig.AngleTarget);
        AngleToleranceEntry = addEntryWithValue("Angle Tolerance", AutoScoreLeftconfig.AngleTolerance);

        //DISTANCE CONFIG
        SmartDashboard.putData("AutoScoreLeft Distance PID",RobotContainer.limelight.distanceController);
        DistanceTargetEntry = addEntryWithValue("Distance Target", AutoScoreLeftconfig.DistanceTarget);
        DistanceToleranceEntry = addEntryWithValue("Distance Tolerance", AutoScoreLeftconfig.DistanceTolerance);
    }

    public static void syncDashboard() {
        AutoScoreLeftconfig.StrafeTolerance = StrafeToleranceEntry.get(AutoScoreLeftconfig.StrafeTolerance);
        AutoScoreLeftconfig.StrafeFunctionAValue = StrafeCalcAEntry.get(AutoScoreLeftconfig.StrafeFunctionAValue);
        AutoScoreLeftconfig.StrafeFunctionBValue = StrafeCalcBEntry.get(AutoScoreLeftconfig.StrafeFunctionBValue);
        AutoScoreLeftconfig.StrafeFunctionCValue = StrafeCalcCEntry.get(AutoScoreLeftconfig.StrafeFunctionCValue);
        AutoScoreLeftconfig.AngleTarget = AngleTargetEntry.get(AutoScoreLeftconfig.AngleTarget);
        AutoScoreLeftconfig.AngleTolerance = AngleToleranceEntry.get(AutoScoreLeftconfig.AngleTolerance);
        AutoScoreLeftconfig.DistanceTarget = DistanceTargetEntry.get(AutoScoreLeftconfig.DistanceTarget);
        AutoScoreLeftconfig.DistanceTolerance = DistanceToleranceEntry.get(AutoScoreLeftconfig.DistanceTolerance);
        return;

    }


    private static DoubleEntry addEntryWithValue(String name, double defaultValue) {
        DoubleEntry newEntry = aslTable.getDoubleTopic(name).getEntry(defaultValue);
        newEntry.set(defaultValue);
        return newEntry;
    }

    
}
