package Engine;

import Engine.Debug.Debug;

public class SoundEngine {

    public static float GeneralVolume = 0.75f;

    /**
     * Plays a Sound 1 time
     * @param Sonido
     */
    public static void playSound(Sound S) {
        S.start();
    }
 
    /**
     * Plays a Sound and repeats it looptimes
     * @param Sonido
     * @param Looptimes
     */
    public static void playSound(Sound S, int Looptimes) {
        S.Loops = Looptimes;
        S.start();
    }

    /**
     * Stops the play of a Sound
     * @param S
     */
    public static void stopSound(Sound S){
        try{
            S.clip.close();
            S.AudioIS.close();
        }
        catch(Exception e){Debug.LogError(e.getMessage());}
    }

    /**
     * Sets the default volume of all sounds
     * @param NewVol
     */
    public void setDefaultVolume(float NewVol){
        try{
            if (!(NewVol > 1 && NewVol < 0)){GeneralVolume = NewVol;}
            else{throw new Exception("Float value out of bounds");}
        }
        catch(Exception e){Debug.LogError(e.getMessage());}
    }
    

    /**
     * Returns the default volume value
     * @return
     */    
    public float getDefaultVolume(){return GeneralVolume;}
}
