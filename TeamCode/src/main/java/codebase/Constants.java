package codebase;

import com.qualcomm.robotcore.hardware.PIDCoefficients;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import codebase.movement.mecanum.MecanumCoefficientMatrix;
import codebase.movement.mecanum.MecanumCoefficientSet;
public class Constants {
    public static final PIDCoefficients MOVEMENT_PID_COEFFICIENTS = new PIDCoefficients(1, 0, 0.05);
    public static final PIDCoefficients DIRECTION_PID_COEFFICIENTS = new PIDCoefficients(0, 0, 0);

    public static double ROTATION_RADIUS_IN = 9.9851;
    public static double MAX_WHEEL_VELOCITY_INCHES_PER_SECOND = 1000;

    public static double PINPOINT_X_OFFSET;
    public static double PINPOINT_Y_OFFSET;
    public static final MecanumCoefficientMatrix MECANUM_COEFFICIENT_MATRIX = new MecanumCoefficientMatrix(
            new MecanumCoefficientSet(-1,1,-1,1),ROTATION_RADIUS_IN);
    public static class MotorConstants {
        public static double GOBILDA_5202_2402_0003_TICKS_PER_ROTATION = 28*3.7;
        public static double GOBILDA_5202_2402_0014_TICKS_PER_ROTATION = 28*13.7;
    }

    public static final List<String> phrases = List.of(
            "Coding: It Works",
            "The Universe is gone!!.... Never mind it's still here",
            "The Cake Is A Lie!",
            "; expected ",
            "',' or ')' expected",
            "Shaw!",
            "200 killed in rogue servo accident",
            "I say potato, you say 'ERROR cannot resolve line'",
            "There is no Spoon",
            "'Thinking noises'",
            "Bring me a shrubbery!",
            "Give me a second, I'm thinking",
            "Oh my god I'm blooming", //requested, wasn't me
            "duct tape!",
            "Are you working on the limelight, Elliot?",
            "Estimated blast radius: 200 meters",
            "bug free since 2038",
            "Gradle: probably doing something",
            "And then, the fire nation attacked",
            "Ba na nana na na nana, Ba na nana na na na 'tequila' ",
            "010000110110111101101110011001110111001001100001011101000111001100100000011011110110111000100000011001100110100101101110011001000110100101101110011001110010000001100001011011100010000001100101011000010111001101110100011001010111001000100000011001010110011101100111",
            "Gazuntite",
            "Maggie shot Mr. Burns",
            "Now owned by Amazon Web Services",
            "Do you know the muffin man?",
            "Don't push code Shawn",
            "Anish is short - Zack",
            "Gunpowder gelatine, dynamite with a lazer beam."
    );

    public static String brad(){
        Random random = new Random();
        int i = random.nextInt(phrases.size());

        return phrases.get(i);
    }
}
