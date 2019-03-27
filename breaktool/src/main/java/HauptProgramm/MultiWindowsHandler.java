package HauptProgramm;

import javafx.stage.Stage;

import java.util.LinkedList;

public class MultiWindowsHandler {

    private static int i = 0;
    private static Stage openStage;

    public MultiWindowsHandler() {

    }

    public static void add () {
        i=1;
       // System.out.println("Up");
    }

    public static int getSize() {
        return i;
    }

    public static void remove (){
        if (i>0)
        i=0;
        openStage=null;
        //System.out.println("Down");
    }

    public static void setStage(Stage s){
        openStage=s; i=1;
    }

    public static Stage getOpenStage() {
        return openStage;
    }
}

