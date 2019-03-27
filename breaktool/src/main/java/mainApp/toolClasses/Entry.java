package mainApp.toolClasses;

import java.io.Serializable;
import java.util.Calendar;

public class Entry implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5164799513451475644L;
    private Calendar anfangszeit;
    private Calendar endzeit;
    private String zustand;
    private String nachricht;

    public Entry(String zustand, String nachricht) {
        this.anfangszeit = Calendar.getInstance();
        this.endzeit = null;
        this.zustand = zustand;
        this.nachricht = nachricht;
    }

    public final void setEndzeit() {
        this.endzeit = Calendar.getInstance();
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((anfangszeit == null) ? 0 : anfangszeit.hashCode());
        result = prime * result + ((endzeit == null) ? 0 : endzeit.hashCode());
        result = prime * result + ((nachricht == null) ? 0 : nachricht.hashCode());
        result = prime * result + ((zustand == null) ? 0 : zustand.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Entry other = (Entry) obj;
        if (anfangszeit == null) {
            if (other.anfangszeit != null)
                return false;
        } else if (!anfangszeit.equals(other.anfangszeit))
            return false;
        if (endzeit == null) {
            if (other.endzeit != null)
                return false;
        } else if (!endzeit.equals(other.endzeit))
            return false;
        if (nachricht == null) {
            if (other.nachricht != null)
                return false;
        } else if (!nachricht.equals(other.nachricht))
            return false;
        if (zustand == null) {
            return other.zustand == null;
        } else return zustand.equals(other.zustand);
    }

    public final String getZustand() {
        return zustand;
    }

    public final String getNachricht() {
        return nachricht;
    }

    @Override
    public final String toString() {

        return String.format("%02d", anfangszeit.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", anfangszeit.get(Calendar.MINUTE))
                + ":" + String.format("%02d", anfangszeit.get(Calendar.SECOND)) + " Uhr" + " - " + String.format("%02d", endzeit.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", endzeit.get(Calendar.MINUTE))
                + ":" + String.format("%02d", endzeit.get(Calendar.SECOND)) + " Uhr" + "\n"
                + zustand + "\n"
                + nachricht + "\n \n";

    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        throw new java.io.NotSerializableException("mainApp.toolClasses.Entry");
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        throw new java.io.NotSerializableException("mainApp.toolClasses.Entry");
    }
}
