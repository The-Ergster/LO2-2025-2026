package codebase.actions;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhraseAction implements Action{
    public PhraseAction(){
        List<String> phrases = new ArrayList<String>();

        // Add elements to the list
            phrases.add("Coding: It Works");
            phrases.add("The Universe is gone!!.... Never mind it's still here");
            phrases.add("The Cake Is A Lie!");
            phrases.add("; expected ");
            phrases.add("Shaw!");
            phrases.add("200 killed in rogue servo accident");
            phrases.add("I say potato, you say 'ERROR cannot resolve line'");
            phrases.add("There is no Spoon");
            phrases.add("'Thinking noises'");
            phrases.add("Bring me a shrubbery!");
            phrases.add("Pull The Lever Kronk");
            phrases.add("'gasping noises'");
            phrases.add("'wheel work'");
            phrases.add("robot-core");
            phrases.add("Auto-bots, Roll out!");

        Random random = new Random();
        int element = random.nextInt(15);

        telemetry.addLine(phrases.get(element));
        telemetry.update();
    }

    @Override
    public void init() {

    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public void loop() {

    }
}
