/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentkontroler;

import domenskeKlase.Atelje;
import domenskeKlase.Mesto;
import domenskeKlase.PosetaAteljeu;
import domenskeKlase.Posetilac;
import domenskeKlase.RadnikSistema;
import domenskeKlase.Termin;
import domenskeKlase.Umetnik;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import sesija.Sesija;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;
import transfer_operacije.StatusOdgovora;
import transfer_operacije.Operacije_radna_memorija;

/**
 *
 * @author nina
 */
public class KlijentKontroler {

    private static KlijentKontroler instance;

    private KlijentKontroler() {
    }

    public static KlijentKontroler getInstance() {
        if (instance == null) {
            instance = new KlijentKontroler();
        }
        return instance;
    }

    private Object sendRequest(int operation, Object data) throws Exception {
        
        KlijentskiZahtev req = new KlijentskiZahtev(operation, data);
        
        ObjectOutputStream out = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        out.writeObject(req);
        
        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());
        ServerskiOdgovor res = (ServerskiOdgovor) in.readObject();
        
        if (res.getResponseStatus().equals(StatusOdgovora.Error)) {
            throw res.getError();
        } else {
            return res.getData();
        }
    }

    public RadnikSistema login(RadnikSistema r) throws Exception {
        return (RadnikSistema) sendRequest(Operacije_radna_memorija.LOGIN, r);
    }

    public ArrayList<Umetnik> getAllUmentik() throws Exception {
        return (ArrayList<Umetnik>) sendRequest(Operacije_radna_memorija.GET_ALL_UMETNIK, null);
    }

    public void addAtelje(Atelje a) throws Exception {
        sendRequest(Operacije_radna_memorija.ADD_ATELJE, a);
    }

    public ArrayList<Atelje> getAllAtelje() throws Exception {
        return (ArrayList<Atelje>) sendRequest(Operacije_radna_memorija.GET_ALL_ATELJE, null);
    }

    public ArrayList<Termin> getAllTerminAteljea(Atelje a) throws Exception {
        return (ArrayList<Termin>) sendRequest(Operacije_radna_memorija.GET_ALL_TERMIN_ATELJEA, a);
    }

    public void updateAtelje(Atelje a) throws Exception {
        sendRequest(Operacije_radna_memorija.UPDATE_ATELJE, a);
    }

    public ArrayList<Mesto> getAllMesto() throws Exception {
        return (ArrayList<Mesto> ) sendRequest(Operacije_radna_memorija.GET_ALL_MESTO, null);
    }

    public void addPosetilac(Posetilac p) throws Exception {
        sendRequest(Operacije_radna_memorija.ADD_POSETILAC, p);
    }

    public ArrayList<Posetilac> getAllPosetilac() throws Exception {
        return (ArrayList<Posetilac>) sendRequest(Operacije_radna_memorija.GET_ALL_POSETILAC, null);
    }

    public ArrayList<Termin> getAllTermin() throws Exception {
        return (ArrayList<Termin>) sendRequest(Operacije_radna_memorija.GET_ALL_TERMIN, null);
    }

    public void addPoseta(PosetaAteljeu posetaAteljeu) throws Exception {
        sendRequest(Operacije_radna_memorija.ADD_POSETA_ATELJEU, posetaAteljeu);
    }

    public ArrayList<PosetaAteljeu> getAllPoseta() throws Exception{
        return (ArrayList<PosetaAteljeu>) sendRequest(Operacije_radna_memorija.GET_ALL_POSETA, null);
    }
    
    public void deletePoseta(PosetaAteljeu pa) throws Exception {
        sendRequest(Operacije_radna_memorija.DELETE_POSETA, pa);
    }

}
