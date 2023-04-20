package Engine.Debug;

import java.text.DecimalFormat;

import Engine.Colors;

public class ProfileMeasure {
    private String Tag;
    private String Type;
    private Colors Color;

    private long timeStart;
    private long timeEnd;
    private long timeAcummulated;
    private int measures;

    private float LastTimeData = 1;
    
    public ProfileMeasure(String T, String type, Colors c){
        Tag = T;
        Type = type;
        Color = c;
    }

    public ProfileMeasure(String T){this(T, "General", Colors.White);}

    public String getType(){return Type;}
    public void setType(String  type){Type = type;}

    public void StartMeasure(){timeStart = System.currentTimeMillis();}
    public void EndMeasure(){
        timeEnd = System.currentTimeMillis();
        timeAcummulated += timeEnd-timeStart;
        measures += 1;
        if (measures >= Profiler.GetMeasuresUntilData()){
            LastTimeData = ((float) timeAcummulated)/(float) measures;
            measures = 0;
            timeAcummulated = 0;
        }
    }

    public float getLastTimeData(){
        return LastTimeData;}
        
    public String getLastTimeDataString(){
        DecimalFormat formater = new DecimalFormat("#.####");
        return formater.format(LastTimeData);
    }

    public String getTag(){return Tag;}
    public Colors getColor(){return Color;}

    public void reset(){
        timeAcummulated = 0;
        measures = 0;
        LastTimeData = 0;
    }
}
