package bgu.spl.mics.application.misc;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;


public class Parser {
    private static int Ewoks;
    private static Attack[] attacks;
    private static long R2D2Duration;
    private static long LandoDuration;

    public Parser(){ }

    public static int getNumOfEwoks(){return Ewoks;}
    public void setNumOfEwoks(int Ewoks){this.Ewoks=Ewoks;}

    public static long getLandoDuration() { return LandoDuration; }
    public void setLandoDuration(long Lando) { LandoDuration = Lando; }

    public static long getR2D2Duration() {
        return R2D2Duration;
    }
    public void setR2D2Duration(long R2D2) { R2D2Duration = R2D2; }


    public static Attack[] getAttacks() { return attacks; }
    public void setAttacks(Attack[] attacks) { this.attacks = attacks; }



}

