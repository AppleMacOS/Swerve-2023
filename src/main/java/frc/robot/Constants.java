// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public interface CAN {
    int FL_DRIVE_MOTOR = 7;
    int FR_DRIVE_MOTOR = 3;
    int BL_DRIVE_MOTOR = 2;
    int BR_DRIVE_MOTOR = 8;

    int FL_ROTATION_MOTOR = 13;
    int FR_ROTATION_MOTOR = 10;
    int BL_ROTATION_MOTOR = 6;
    int BR_ROTATION_MOTOR = 12;

    int GOBBEUR = 50;

    int MONTEUR1 = 51;
    int MONTEUR2 = 54;
    int AVANCEUR = 52;
    int REPLIEUR = 53;

  }

  public interface DIGITAL {
    int FL_ENCODER = 0;
    // Pour fils custom: Bleu 1, Meuve 2, Gris 3, Blanc 4, Noir 5
    int FR_ENCODER = 1;
    int BL_ENCODER = 2;
    int BR_ENCODER = 3;

    int CLOCK = 5;
    int CHIP_SELECT = 4;

    int STAGE1 = 6;
    int STAGE2 = 7;
    int STAGE3 = 8;

    int RENTRER = 9;
    int SORTI = 10;
  }

  public interface USB {
    int DRIVER_CONTROLLER = 0;
    int DRIVER_CONTROLLERCOPILOT = 1;
  }

  public interface PCM{
    int PISTON_PINCE_FORWARD = 4;
    int PISTON_PINCE_REVERSE = 5;
  }
}
