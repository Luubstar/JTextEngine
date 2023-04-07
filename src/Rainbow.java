 


public class Rainbow implements Menu{

    String[] colores = {Colors.RED, Colors.YELLOW, Colors.GREEN,Colors.CYAN, Colors.BLUE, Colors.MAGENTA};
    int desfase = 0;
    @Override
    public String Frame() {
        String res = "";
        for(int i = 0; i < colores.length; i++){
            int index = i + desfase;
            if (index >= colores.length){index -= colores.length;}
            res += colores[index] + "█████████████████████████████████████████████\n" + Colors.SANE;
        }
        desfase++;
        if (desfase >= colores.length){desfase -= colores.length;}
        return res;
    }

    @Override
    public void InputSystem() {
    }
}
