package mainApp.Hilfsklassen;

import java.util.Calendar;

public class Kalender {



public static String getCurrentDate() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR);
}

public static String getCurrentTimeWithSeconds(){
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
}

public static String getCurrentTime(){
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
}

}