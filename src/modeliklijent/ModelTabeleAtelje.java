/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeliklijent;

import domenskeKlase.Atelje;
import formemain.PretragaAteljea;
import klijentkontroler.KlijentKontroler;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author nina
 */
public class ModelTabeleAtelje extends AbstractTableModel{

    private ArrayList<Atelje> lista;
    private String[] kolone={"Naziv ateljea","Lokacija","Umetnik"};
    private String parametar = "";

    public ModelTabeleAtelje() {
        try {
           lista = KlijentKontroler.getInstance().getAllAtelje();
        } catch (Exception ex) {
            Logger.getLogger(ModelTabeleAtelje.class.getName()).log(Level.SEVERE, null, ex);
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
        Atelje a = lista.get(row); 

        switch (column) {
            case 0:
                return a.getNazivAteljea();
            case 1:
                return a.getLokacija();
            case 2:
                return a.getUmetnik().toString();
            default:
                return null;
        }
    }

    public Atelje getSelectedAtelje(int row) {
        return lista.get(row);
    }


    public void setParametar(String parametar) {
        this.parametar = parametar;
        refreshTable();
    }

    public void refreshTable() {
        try {
            if (!parametar.equals("")) {
                ArrayList<Atelje> novaLista = new ArrayList<>();
                
                for (Atelje a : lista) {
                    if(a.getLokacija().toLowerCase().contains(parametar.toLowerCase())
                            || a.getNazivAteljea().toLowerCase().contains(parametar.toLowerCase())){
                        novaLista.add(a);
                    }
                }
                if(novaLista.size()>0){
                    PretragaAteljea.setPoruka("Sistem je pronasao ateljee po zadatoj vrednosti");
                }
                else{
                    PretragaAteljea.setPoruka("Sisten nije pronasao ateljee po zadatoj vrednosti");
                }
                lista = novaLista;
            }

            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
