package mainApp.toolClasses;

public class Calendar {


    public static String getCurrentDate() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        return calendar.get(java.util.Calendar.DAY_OF_MONTH) + "." + (calendar.get(java.util.Calendar.MONTH) + 1) + "." + calendar.get(java.util.Calendar.YEAR);
    }

    public static String getCurrentTimeWithSeconds() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        return calendar.get(java.util.Calendar.HOUR_OF_DAY) + ":" + calendar.get(java.util.Calendar.MINUTE) + ":" + calendar.get(java.util.Calendar.SECOND);
    }

    public static String getCurrentTime() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        return calendar.get(java.util.Calendar.HOUR_OF_DAY) + ":" + calendar.get(java.util.Calendar.MINUTE);
    }

}