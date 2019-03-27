package HauptProgramm.Hilfsklassen;

import java.io.Serializable;
import java.util.Calendar;

public class Eintrag implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5164799513451475644L;
    private Calendar anfangszeit;
    private Calendar endzeit;
    private String zustand;
    private String nachricht;

    public Eintrag() {
        this("", "");
    }

    public Eintrag(String zustand, String nachricht) {
        this.anfangszeit = Calendar.getInstance();
        this.endzeit = null;
        this.zustand = zustand;
        this.nachricht = nachricht;
    }

    public void setEndzeit() {
        this.endzeit = Calendar.getInstance();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((anfangszeit == null) ? 0 : anfangszeit.hashCode());
        result = prime * result + ((endzeit == null) ? 0 : endzeit.hashCode());
        result = prime * result + ((nachricht == null) ? 0 : nachricht.hashCode());
        result = prime * result + ((zustand == null) ? 0 : zustand.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Eintrag other = (Eintrag) obj;
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
            if (other.zustand != null)
                return false;
        } else if (!zustand.equals(other.zustand))
            return false;
        return true;
    }

    public Calendar getAnfangszeit() {
        return anfangszeit;
    }

    public void setAnfangszeit(Calendar anfangszeit) {
        this.anfangszeit = anfangszeit;
    }

    public String getZustand() {
        return zustand;
    }

    public void setZustand(String zustand) {
        this.zustand = zustand;
    }

    public String getNachricht() {
        return nachricht;
    }

    public void setNachricht(String nachricht) {
        this.nachricht = nachricht;
    }

    public Calendar getEndzeit() {
        return endzeit;
    }

    @Override
    public String toString() {

        return String.format("%02d", anfangszeit.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", anfangszeit.get(Calendar.MINUTE))
                + ":" + String.format("%02d", anfangszeit.get(Calendar.SECOND)) + " Uhr" + " - " + String.format("%02d", endzeit.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", endzeit.get(Calendar.MINUTE))
                + ":" + String.format("%02d", endzeit.get(Calendar.SECOND)) + " Uhr" + "\n"
                + zustand + "\n"
                + nachricht + "\n \n";

    }
}
