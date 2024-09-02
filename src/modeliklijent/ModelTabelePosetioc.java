/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeliklijent;

import domenskeKlase.Atelje;
import domenskeKlase.Posetilac;
import formemain.PretragaAteljea;
import formemain.PretragaPosetioca;
import klijentkontroler.KlijentKontroler;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nina
 */
public class ModelTabelePosetioc extends AbstractTableModel{

    private ArrayList<Posetilac> lista;
    private String[] kolone={"Ime prezime","Kontakt","Mesto"};
    private String parametar = "";

    public ModelTabelePosetioc() {
        try {
           lista = KlijentKontroler.getInstance().getAllPosetilac();
        } catch (Exception ex) {
            Logger.getLogger(ModelTabelePosetioc.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        Posetilac p = lista.get(row); 

        switch (column) {
            case 0:
                return p.getImePrezimePosetioca();
            case 1:
                return p.getKontakt();
            case 2:
                return p.getMesto().toString();
            default:
                return null;
        }
    }

    public Posetilac getSelectedPosetilac(int row) {
        return lista.get(row);
    }


    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            if (!parametar.equals("")) {
                ArrayList<Posetilac> novaLista = new ArrayList<>();
                for (Posetilac p : lista) {
                    if(p.getImePrezimePosetioca().toLowerCase().contains(parametar.toLowerCase())){
                        novaLista.add(p);
                    }
                }
                if(novaLista.size()>0){
                    PretragaPosetioca.setPoruka("Sistem je pronasao posetioca po zadatoj vrednosti");
                }
                else{
                    PretragaPosetioca.setPoruka("Sistem nije pronasao posetioca po zadatoj vrednosti");
                }
                lista = novaLista;
            }

            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
