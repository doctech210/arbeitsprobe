package de.hsos.swa.coldstoneicecreator.bestellung.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;

public interface BestellungControl {
    
    public List<Bestellung> bestellungenAbfragen();

    public List<Bestellung> bestellungenAbfragen(Long kundeId);
    
    public Bestellung bestellungAbfragen(Long bestellId);

    public Bestellung bestellungAbfragen(Long bestellId, Long kundeId);

    public Bestellung bestellungAnlegen(Bestellung bestellungen, Long kundeId);

    public boolean bestellungLoeschen(Long bestellId);

    public boolean bestellungLoeschen(Long bestellId, Long kundeId);
}
