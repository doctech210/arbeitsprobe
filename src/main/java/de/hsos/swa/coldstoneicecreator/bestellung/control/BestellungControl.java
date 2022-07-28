package de.hsos.swa.coldstoneicecreator.bestellung.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;

public interface BestellungControl {
    
    public List<Bestellung> bestellungenAbfragen();

    public List<Bestellung> bestellungenAbfragen(Long nutzerId);
    
    public Bestellung bestellungAbfragen(Long bestellId);

    public Bestellung bestellungAbfragen(Long bestellId, Long nutzerId);

    public Bestellung bestellungAnlegen(Bestellung bestellungen, Long nutzerId);

    public boolean bestellungLoeschen(Long bestellId);

    public boolean bestellungLoeschen(Long bestellId, Long nutzerId);
    
    public boolean isBestellungLeer(Long bestellId);
}
