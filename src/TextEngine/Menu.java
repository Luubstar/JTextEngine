package TextEngine;
 

public class Menu {
    /**
     * The function that will be called every frame
     */
    public void Update(){}
    /**
     * The function that will be called when a menu is started
     */
    public void Start(){}
    /**
     * The function that renders the frame
     * @return the string that will be rendered
     */
    public String Frame(){return "";}
    /**
     * Call when the console is resized
     */
    public void OnRescale(){}
}
