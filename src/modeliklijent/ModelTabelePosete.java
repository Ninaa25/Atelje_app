/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeliklijent;

import domenskeKlase.Atelje;
import domenskeKlase.PosetaAteljeu;
import domenskeKlase.Termin;
import formemain.PretragaPoseta;
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
public class ModelTabelePosete extends AbstractTableModel {

    private ArrayList<PosetaAteljeu> lista;
    private String[] kolone = {"Atelje", "Dan", "Vreme", "Posetilac"};
    private String parametar = "";

    public void setParametar(String parametar) {
        this.parametar = parametar;
        osveziTabelu();
    }

    public ModelTabelePosete() {
        try {
            lista = new ArrayList<>();
        } catch (Exception ex) {
            Logger.getLogger(ModelTabelePosete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModelTabelePosete(ArrayList<PosetaAteljeu> posete) {
        lista = posete;
    }

    public ArrayList<PosetaAteljeu> getLista() {
        return lista;
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

    @Override
    public Object getValueAt(int row, int column) {
        
        PosetaAteljeu pt = lista.get(row);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        switch (column) {
            case 0:
                return pt.getTermin().getAtelje().getNazivAteljea();
            case 1:
                return pt.getTermin().getDanUNedelji();
            case 2:
                return sdf.format(pt.getTermin().getVremeTermina());
            case 3:
                return pt.getPosetilac().toString();
            default:
                return null;
        }
    }

    public void dodaj(PosetaAteljeu pt) {
        lista.add(pt);
        fireTableDataChanged();
    }

    public PosetaAteljeu getSelectedPoseta(int row) {
        return lista.get(row);
    }

    public boolean postoji(Termin t) {
        for (PosetaAteljeu poseta : lista) {
            if (t.getDanUNedelji().equals(poseta.getTermin().getDanUNedelji())) {
                int result = t.getVremeTermina().compareTo(poseta.getTermin().getVremeTermina());
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

    private void osveziTabelu() {
        ArrayList<PosetaAteljeu> posete = new ArrayList<>();
        for (PosetaAteljeu posetaAteljeu : lista) {
            if (posetaAteljeu.getTermin().getAtelje().getNazivAteljea().toLowerCase().contains(parametar.toLowerCase())
                    || posetaAteljeu.getPosetilac().getImePrezimePosetioca().toLowerCase().contains(parametar.toLowerCase())) {
                posete.add(posetaAteljeu);
            }
        }
        if (posete.size() > 0) {
            PretragaPoseta.setPoruka("Sistem je pronasao posete po zadatoj vrednosti");
        } else {
            PretragaPoseta.setPoruka("Sistem nije pronasao posete po zadatoj vrednosti");
        }
        lista = posete;
        fireTableDataChanged();
    }

}
