package mainApp.toolClasses;


import mainApp.MainGui;

import java.awt.*;
import java.io.*;
import java.util.Calendar;
import java.util.LinkedList;

public class Entrymanagement {
    private LinkedList<Entry> pausensituationen;
    private LinkedList<String> rückrufeListe;
    private int zahlAnrufe = 0;
    private LinkedList<String> reasonString;

    private File backupFolder = new File(System.getProperty("user.home") + File.separator + "/documents" + File.separator + "/Breaktool");
    private File backup = new File(backupFolder.getAbsolutePath(), "BreaktoolExport.ser");

    public Entrymanagement() {
        this(new LinkedList<>(), new LinkedList<>(), 0, new LinkedList<>());
    }

    public Entrymanagement(LinkedList<Entry> pausensituationen,
                           LinkedList<String> rückrufeListe,
                           int zahlAnrufe,
                           LinkedList<String> reasonString) {

        this.pausensituationen = pausensituationen;
        this.rückrufeListe = rückrufeListe;
        this.zahlAnrufe = zahlAnrufe;
        this.reasonString = reasonString;

    }

    public LinkedList<Entry> getPausensituationen() {
        return pausensituationen;
    }

    public void setPausensituationen(LinkedList<Entry> pausensituationen) {
        this.pausensituationen = pausensituationen;
    }

    public LinkedList<String> getRückrufeListe() {
        return rückrufeListe;
    }

    public void setRückrufeListe(LinkedList<String> rückrufeListe) {
        this.rückrufeListe = rückrufeListe;
    }

    public int getZahlAnrufe() {
        return zahlAnrufe;
    }

    public void setZahlAnrufe(int zahlAnrufe) {
        this.zahlAnrufe = zahlAnrufe;
    }

    public LinkedList<String> getReasonString() {
        return reasonString;
    }

    public void setReasonString(LinkedList<String> reasonString) {
        this.reasonString = reasonString;
    }

    public void addReasonCode(Entry e) {
        pausensituationen.add(e);
    }

    public void addRückruf(String e) {
        rückrufeListe.add(e);
    }

    public void addBegründungAnruf(String e) {
        reasonString.add(e);
    }

    public void sichern() {
        new File(System.getProperty("user.home") + File.separator + "documents" + File.separator + "Breaktool").mkdirs();

        try (ObjectOutputStream t = new ObjectOutputStream(new FileOutputStream(backup)); ObjectInputStream m = new ObjectInputStream(new FileInputStream(backup))) {

            String datum = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "." + Calendar.getInstance().get(Calendar.MONTH) + "." + Calendar.getInstance().get(Calendar.YEAR);
            t.writeObject(datum);
            t.writeObject(pausensituationen);
            t.writeObject(rückrufeListe);
            t.writeInt(zahlAnrufe);
            t.writeObject(reasonString);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void restore() {
        if (backup.exists()) {
            try (ObjectInputStream t = new ObjectInputStream(new FileInputStream(backup))) {
                t.readObject();
                pausensituationen = (LinkedList<Entry>) t.readObject();
                rückrufeListe = (LinkedList<String>) t.readObject();
                zahlAnrufe = t.readInt();
                reasonString = (LinkedList<String>) t.readObject();
            } catch (ClassNotFoundException | IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public void reset() {
        pausensituationen = new LinkedList<>();
        reasonString = new LinkedList<>();
        rückrufeListe = new LinkedList<>();
        zahlAnrufe = 0;
        sichern();
        MainGui.refreshToolTip();

    }

    public void exportAlsTxt(Writer m) {
        try (BufferedWriter t = new BufferedWriter(m)) {
            // Calendar s = Calendar.getInstance();
            // t.write(("Terminbericht:
            // "+s.get(Calendar.DAY_OF_MONTH)+"."+s.get(Calendar.MONTH)+"."+s.get(Calendar.YEAR)+":\n
            // \n").getBytes());
            t.write("Reasoncode(s):");
            if(MainGui.getVerwaltung().getPausensituationen().size()==0){
                t.write(" Keine\n\n");
            }else{
                t.write("\n");
                for (Entry e : pausensituationen) {
                    t.write(e.toString());
                }
            }

            t.write("Rückruf(e): ");
            if (MainGui.getVerwaltung().getRückrufeListe().size()==0){
                t.write("Keine\n");
            }else{
                t.write("\n");
                for (String e : rückrufeListe) {
                    t.write((e + "\n"));
                }
            }

            t.write(("\nAnruf(e): " + zahlAnrufe));
            if (reasonString.size()==0){

            }else{
                t.write(", weil:\n");
                for (String e : reasonString) {
                    t.write((e));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
        public String toString () {
            String ausgabe="";

            ausgabe += ("Hallo zusammen,\n\n");
            ausgabe += ("Reasoncodes: ");
            if(pausensituationen.size()==0){
                ausgabe += ("Keine\n\n");
            }else{
                ausgabe+="\n";
                for (Entry e : pausensituationen) {
                    ausgabe += e.toString();
                }
            }

            ausgabe += ("Rückrufe: ");
            if(rückrufeListe.size()==0){
                ausgabe += ("Keine\n");
            }else{
                ausgabe += ("\n");
                for (String e : rückrufeListe) {
                    ausgabe += e + "\n";
                }
            }
            ausgabe += ("\nAnruf(e): " + zahlAnrufe);
            if(reasonString.size()==0){
               ausgabe+="\n";
            }else{
                ausgabe += ((", weil:\n"));
                for (String e : reasonString) {
                    ausgabe += e;
                }
            }

            return ausgabe;
        }

    public void openFile(){
        if(!Desktop.isDesktopSupported()){
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        File file = new File(System.getProperty("user.home") + File.separator + "documents" + File.separator + "Breaktool"+ File.separator +"BreakToolExport.txt");
        try {
            exportAlsTxt(new FileWriter(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            desktop.open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}