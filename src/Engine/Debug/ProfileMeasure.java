package Engine.Debug;

import java.text.DecimalFormat;

import Engine.Colors;
import Engine.Engine;

public class ProfileMeasure {
    private String Tag;
    private String Type;
    private Colors Color;

    private long timeStart;
    private long timeEnd;
    private long timeAcummulated;
    private int measures;

    private float LastTimeData = 1;
    
    /**
     * Creates a custom ProfileMeasure, with tag T, type, and Color c
     * @param T
     * @param type
     * @param c
     */
    public ProfileMeasure(String T, String type, Colors c){
        Tag = T;
        Type = type;
        Color = c;
    }

    /**
     * Creates a ProfileMeasure, with tag T, type "General", and color "White"
     * @param T
     */
    public ProfileMeasure(String T){this(T, "General", Colors.White);}

    /**
     * Returns the type of the measure
     * @return
     */
    public String getType(){return Type;}
    /**
     * Sets the type of the measure
     * @param type
     */
    public void setType(String  type){Type = type;}

    /**
     * Starts the time of the measure
     */
    public void StartMeasure(){timeStart = System.currentTimeMillis();}
    /**
     * Ends the measure and if it has sufficient measures, it will calculate the average and render a new frame
     */
    public void EndMeasure(){
        timeEnd = System.currentTimeMillis();
        timeAcummulated += timeEnd-timeStart;
        measures += 1;
        if (measures >= Profiler.GetMeasuresUntilData()){
            LastTimeData = ((float) timeAcummulated)/(float) measures;
            measures = 0;
            timeAcummulated = 0;
            Engine.Render();
        }
    }

    /**
     * Returns the last data of the measure
     * @return
     */
    public float getLastTimeData(){
        return LastTimeData;}
        
    /**
     * Returns the last data as a formated string
     * @return
     */
    public String getLastTimeDataString(){
        DecimalFormat formater = new DecimalFormat("#.####");
        return formater.format(LastTimeData);
    }

    /**
     * Returns the tag of the measure
     * @return
     */
    public String getTag(){return Tag;}
    /**
     * Returns the color of the measure
     * @return
     */
    public Colors getColor(){return Color;}

    /**
     * Resets the measure
     */
    public void reset(){
        timeAcummulated = 0;
        measures = 0;
        LastTimeData = 0;
    }
}
