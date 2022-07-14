package de.hsos.swa.coldstoneicecreator.bestellung.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellposten;
import de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao.KreationDAO;

public interface BestellpostenControl {
    
    public Bestellposten postenAnlegen(Long bestellId, Long pizzaId);

    public Bestellposten postenAnlegen2(Long bestellId, KreationDAO kreationDAO);
    
    public List<Bestellposten> bestellpostenAbfragen();

    public Bestellposten postenAbfragen(Long postenId);

    public boolean postenAendern(Long bestellId, Long postenId, int anzahl);

    public boolean postenLoeschen(Long postenId);
}
