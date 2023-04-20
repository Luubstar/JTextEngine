package Engine.Debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.PieStyler.LabelType;
import org.knowm.xchart.style.Styler.ChartTheme;

import Engine.Colors;
import Engine.Engine;

public class Profiler {
    private static ArrayList<ProfileMeasure> ProfileData = new ArrayList<>();

    private static boolean Display;
    private static boolean showFPS;
    static String block = "━";
    static String verticalBlock = "┃";
    static String space = " ";
    static String title ="┃  Profiler:";

    static String filter = "All";
    private static int measuresTop = 30; 

    public static void setProfilerMode(boolean mode){Display = mode;}
    public  static boolean getProfilerMode(){return Display;}

    public static void setFPSMode(boolean mode){showFPS = mode;}
    public static boolean getFPSMode(){return showFPS;}
    public static String getFPS(){
        DecimalFormat formater = new DecimalFormat("#.##");
        return "FPS: " + formater.format((float)1000/getLastData("TickTime")) + "/" + formater.format((float)1000/Engine.frameTime());
    }

    public static void Start(){
        ProfileData.add(new ProfileMeasure("TickTime", "General" ,Colors.Yellow));
        ProfileData.add(new ProfileMeasure("Profiler", "Advance" ,Colors.Green));
        ProfileData.add(new ProfileMeasure("DebugLogger", "Advance" ,Colors.Red));
        ProfileData.add(new ProfileMeasure("Render", "Advance" ,Colors.Blue));
        ProfileData.add(new ProfileMeasure("WaitTime", "Advance" ,Colors.Yellow));
        ProfileData.add(new ProfileMeasure("Other", "Advance" ,Colors.Cyan));
    }

    public static void AddProfile(ProfileMeasure data){ProfileData.add(data);}
    public static void DeleteProfiles(){ProfileData.clear();}
    public static void ResetProfiles(){
        for (ProfileMeasure measure: ProfileData){measure.reset();}
    }
    public static void DeleteProfile(ProfileMeasure data){ProfileData.remove(data);}


    public static void StartMeasure(String tag){findByTag(tag).StartMeasure();}
    public static void EndMeasure(String tag){findByTag(tag).EndMeasure();}

    public static void SetMeasuresUntilData(int measures){measuresTop = measures;}
    public static int GetMeasuresUntilData(){return measuresTop;}

    public static float getLastData(String tag){return findByTag(tag).getLastTimeData();}

    public static float[] getAllLastData(){
        float[] data = new float[ProfileData.size()];
        for (int i = 0; i < ProfileData.size(); i++){
            data[i] = ProfileData.get(i).getLastTimeData();
        }
        return data;
    }

    public static ProfileMeasure findByTag(String tag){
        for (ProfileMeasure Measure:ProfileData){
            if (Measure.getTag().toLowerCase().equals(tag.toLowerCase())){return Measure;}
        }
        return null;
    }

    public static void setFilter(String f){filter = f.toLowerCase();}
    public static String getFilter(){return filter;}
    public static ArrayList<ProfileMeasure> getListByFilter(String f){
        if (f.toLowerCase().equals("all")){return ProfileData;}
        ArrayList<ProfileMeasure> FilteredData = new ArrayList<>();
        for (ProfileMeasure p: ProfileData){
            if (p.getType().toLowerCase().equals(f.toLowerCase())){FilteredData.add(p);}
        }
        return FilteredData;
    }

    public static void ProfilerToFile(String filepath, String filter) throws Exception{
        File file = new File(filepath);

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        String data = "";

        for (ProfileMeasure measure: getListByFilter(filter)){
            data += "[" + measure.getType() + ":" + measure.getTag() + "] -> " + measure.getLastTimeDataString() + "\n";
        }

        bw.write(data);
        bw.newLine();

        bw.close();
        fw.close();
    }

    public static void ExportPieChart(String filepath, String filter) throws Exception{
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Profilers").theme(ChartTheme.GGPlot2).build();


        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setPlotContentSize(.7);
        chart.getStyler().setLabelType(LabelType.NameAndPercentage);
        chart.getStyler().setLabelsDistance(1.15);
        chart.getStyler().setStartAngleInDegrees(90);
        ArrayList<ProfileMeasure> measures = getListByFilter(filter);
        for(ProfileMeasure measure : measures){
            chart.addSeries(measure.getTag(), measure.getLastTimeData());
        }

        BitmapEncoder.saveBitmapWithDPI(chart, filepath, BitmapFormat.PNG, 300);
    }

    public static void ExportPieChart(String filepath) throws Exception{
        ExportPieChart(filepath, getFilter());
    }

    public static String ProfilerToString(){
        try{
            String logstring = "";
            if (Engine.getWidth() == 0 || Engine.getHeight() == 0){
                Engine.updateSize();
            }
            if (getListByFilter(filter).size() > 0){
                logstring += "┏" + block.repeat(Engine.getWidth() -2) + "┓";
                logstring +=  title + space.repeat(Engine.getWidth() - title.length() - 1) + verticalBlock;
                ArrayList<ProfileMeasure> logs = getListByFilter(filter);

                for (int i = 0; i < getListByFilter(filter).size(); i++ ){
                    ProfileMeasure newlog = logs.get(i);
                    
                    String ColoredMessage = newlog.getColor().colorize(newlog.getTag() + " -> " + newlog.getLastTimeDataString() + " ms");
                    String message  = Colors.clearANSI(ColoredMessage);
                    String Color = newlog.getColor().toString();
                    
                    if (message.length() > Engine.getWidth()-2){
                        String[] SplitedLogs = Engine.splitString(message, Engine.getWidth() -7);
                        for (int a = 0; a < SplitedLogs.length; a++){
                            String splitedlog = SplitedLogs[a];
                            message  = Colors.clearANSI(splitedlog);
                            if (a == 0){logstring += verticalBlock + "  " + Color + message + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - message.length()) + verticalBlock;}    
                            else{logstring += verticalBlock + "  " + Color + message + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - message.length()) + verticalBlock;}
                        }
                    }
                    else{logstring += verticalBlock + "  " + ColoredMessage + Colors.SANE+ space.repeat(Engine.getWidth() - 4 - message.length()) + verticalBlock;}}
                logstring += "\n┗" + block.repeat(Engine.getWidth() -2) + "┛";
            }
            return logstring;
        }
        catch(Exception e){Debug.LogError(e.getMessage());return "";}
    }
}
