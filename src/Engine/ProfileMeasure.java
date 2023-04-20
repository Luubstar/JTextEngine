package Engine;

import java.text.DecimalFormat;

public class ProfileMeasure {
    private String Tag;
    private Colors Color;

    private long timeStart;
    private long timeEnd;
    private long timeAcummulated;
    private int measures;

    private float LastTimeData;
    
    public ProfileMeasure(String T, Colors c){
        Tag = T;
        Color = c;
    }

    public ProfileMeasure(String T){this(T, Colors.White);}

    public void StartMeasure(){timeStart = System.currentTimeMillis();}
    public void EndMeasure(){
        timeEnd = System.currentTimeMillis();
        timeAcummulated = timeEnd-timeStart;
        measures += 1;
        if (measures >= Profiler.GetMeasuresUntilData()){
            LastTimeData = ((float) timeAcummulated)/measures;
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
}
