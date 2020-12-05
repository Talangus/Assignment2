package bgu.spl.mics.application.misc;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;


public class Parser {
    private static int Ewoks;
    private Attack[] attacks;
    private long R2D2Duration;
    private long LandoDuration;

    public Parser(){ }

    public static int getNumOfEwoks(){return Ewoks;}
    public void setNumOfEwoks(int ewoks){Ewoks=ewoks;}

    public long getLandoDuration() { return LandoDuration; }
    public void setLandoDuration(long landoDuration) { LandoDuration = landoDuration; }

    public long getR2D2Duration() {
        return R2D2Duration;
    }
    public void setR2D2Duration(long r2D2Duration) { R2D2Duration = r2D2Duration; }


    public Attack[] getAttacks() { return attacks; }
    public void setAttacks(Attack[] attacks) { this.attacks = attacks; }



}

