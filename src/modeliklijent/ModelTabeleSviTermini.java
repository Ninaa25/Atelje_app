/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeliklijent;

import domenskeKlase.Atelje;
import domenskeKlase.Termin;
import java.text.SimpleDateFormat;
import klijentkontroler.KlijentKontroler;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nina
 */
public class ModelTabeleSviTermini extends AbstractTableModel {

    private ArrayList<Termin> lista;
    private String[] kolone = {"Atelje", "Dan", "Vreme", "Cena"};

    public ModelTabeleSviTermini() {
        try {
            lista = KlijentKontroler.getInstance().getAllTermin();
        } catch (Exception ex) {
            Logger.getLogger(ModelTabeleSviTermini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Termin> getLista() {
        return lista;
    }

    public ModelTabeleSviTermini(ArrayList<Termin> termin) {
        lista = termin;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int i) {
        return kolone[i];
    }
    
    public Termin getSelectedTermin(int row){
        return lista.get(row);
    }

    @Override
    public Object getValueAt(int row, int column) {
        Termin t = lista.get(row);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        switch (column) {
            case 0:
                return t.getAtelje().getNazivAteljea();
            case 1:
                return t.getDanUNedelji();
            case 2:
                return sdf.format(t.getVremeTermina());
            case 3:
                return t.getCena();
            default:
                return null;
        }
    }

    public void dodaj(Termin t) {
        lista.add(t);
        fireTableDataChanged();
    }

    public boolean postoji(Termin t) {
        for (Termin termin : lista) {
            if (t.getDanUNedelji().equals(termin.getDanUNedelji())) {
                int result = t.getVremeTermina().compareTo(termin.getVremeTermina());
                if (result == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void obrisi(int row) {
        lista.remove(row);
        fireTableDataChanged();
    }

}
