package Engine;

import Engine.Debug.Debug;

public class SoundEngine {


    public static float DefaultVolume = 0.75f;
    public static float GeneralVolume = 1f;

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
    public void setDefaultVolume(float NewVol) throws IllegalArgumentException{
        if (!(NewVol > 1f && NewVol < 0f)){DefaultVolume = NewVol;}
        else{throw new IllegalArgumentException("Float value out of bounds");}
    }
    

    /**
     * Returns the default volume value
     * @return
     */    
    public float getDefaultVolume(){return DefaultVolume;}

    /**
     * Sets the general volume for all sounds. Their volume will be multiplied by the general value
     * @param NewVol
     */
    public void setGeneralVolume(float NewVol) throws IllegalArgumentException{
        if (!(NewVol > 1f && NewVol < 0f)){GeneralVolume = NewVol;}
        else{throw new IllegalArgumentException("Float value out of bounds");}
    }
    
    /**
     * Returns the general volume value
     * @return
     */    
    public float getGeneralVolume(){return GeneralVolume;}
}
