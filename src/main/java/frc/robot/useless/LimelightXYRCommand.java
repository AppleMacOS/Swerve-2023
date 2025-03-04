
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.useless;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.util.Range;

public class LimelightXYRCommand extends CommandBase {

  Drivetrain drivetrain;
  Limelight limelight;
  PIDController pidRotation;
  PIDController pid;
  double rangeX;
  double rangeY;

  public boolean stop = false;

  

  /** Creates a new LimelightYRotationCommand. */
  public LimelightXYRCommand(Drivetrain drivetrain, Limelight limelight, double rangeX, double rangeY) {
    // (
      this.limelight = limelight;
      this.drivetrain = drivetrain;
      this.rangeX = rangeX;
      this.rangeY = rangeY;
        // The controller that the command will use
       //pidRotation = new PIDController(0.2, 0, 0);
       pid = new PIDController(1.8, 0, 0);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    limelight.setLEDMode(3);

    //pidRotation.reset();
    pid.reset();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    stop = false;
    //pas sure si getRobotPosition marche, on veut que cela donne la distance relative a la cible et non au terrain
    
    double positionY = limelight.getRobotPosition()[1];
    double positionX = limelight.getRobotPosition()[0];

    double distanceX;
    double distanceY;
  
    double distanceXfin;
    double distanceYfin;

    if(isInverted()){
      distanceX = (rangeX - positionX) * -1;
      distanceY = (rangeY - positionY) * -1;
    }else{
      distanceX = rangeX - positionX;
      distanceY = rangeY - positionY;
    }

    System.out.println("Distance x (a):" + distanceX);
    System.out.println("Distance y (a):" + distanceY);

     if(Math.abs(distanceX) > Math.abs(distanceY)){
      distanceXfin = distanceX/Math.abs(distanceX);
      distanceYfin = distanceY/Math.abs(distanceX);
    }else{
      distanceXfin = distanceX/Math.abs(distanceY);
      distanceYfin = distanceY/Math.abs(distanceY);
    }

    System.out.println("Distance x:" + distanceXfin);
    System.out.println("Distance y:" + distanceYfin);


    //double distanceR = limelight.getRobotPosition()[5];
    double magnitude = Math.sqrt(Math.pow(positionX-rangeX, 2)+Math.pow(positionY-rangeY, 2));
    double speed = pid.calculate(magnitude, 0);

    System.out.println("magnitude:" +magnitude);
    System.out.println("speed factor:" + speed);

    //double rotation = pidRotation.calculate(0, distanceR);
    double rotation = 0.0;
    double speedX = 0;
    double speedY = 0;
    if(limelight.getIsTargetFound()){
      if(limelight.getRobotPosition()[0] >= rangeX-0.5 && limelight.getRobotPosition()[0] <= rangeX+0.5){
        //drivetrain.driveSwerve(0, Range.coerce(1, speed) * -distanceYfin, rotation, false);
        speedX = 0;
        speedY = Range.coerce(1, speed) * -distanceYfin;
        SmartDashboard.putString("condition", "y");
      }

      if(limelight.getRobotPosition()[1] >= rangeY-0.5 && limelight.getRobotPosition()[1] <= rangeY+0.5){
        //drivetrain.driveSwerve(Range.coerce(1, speed) * distanceXfin, 0, rotation, false);
        speedX = Range.coerce(1, speed) * distanceXfin;
        speedY = 0;
        SmartDashboard.putString("condition", "x");
      }

      if(limelight.getRobotPosition()[1] >= rangeY-0.5 && limelight.getRobotPosition()[1] <= rangeY+0.5 && limelight.getRobotPosition()[0] >= rangeX-0.5 && limelight.getRobotPosition()[0] <= rangeX+0.5) {
        //drivetrain.driveSwerve(0, 0, rotation, false);
        speedX = 0;
        speedY = 0;
        SmartDashboard.putString("condition", "fini");
        stop = true;
      }else{
        //drivetrain.driveSwerve(Range.coerce(1, speed) * distanceXfin, Range.coerce(1, speed) * -distanceYfin, rotation, false);
        speedX = Range.coerce(1, speed) * distanceXfin;
        speedY = Range.coerce(1, speed) * -distanceYfin;
        SmartDashboard.putString("condition", "xy");
      }


      SmartDashboard.putNumber("speedY", speedY);
      SmartDashboard.putNumber("speedX", speedX);
      drivetrain.driveSwerve(speedX, speedY, rotation, false);
    }else{
      stop = true;
    }

    SmartDashboard.putNumber("distanceX", rangeX - positionX);
    SmartDashboard.putNumber("distanceY", rangeY - positionY);
    SmartDashboard.putNumber("magnitude", magnitude);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    drivetrain.driveSwerve(0, 0, 0, false);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    limelight.setLEDMode(1);

    return stop;
  }

  public boolean isInverted(){
    double degree = limelight.getRobotPosition()[5];

    if(degree < 90 && degree > -90){
      return false;
    }else{
      return true;
    }
  }
}
