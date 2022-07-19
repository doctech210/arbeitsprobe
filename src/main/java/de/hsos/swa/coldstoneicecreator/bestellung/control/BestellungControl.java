package de.hsos.swa.coldstoneicecreator.bestellung.control;

import java.util.List;

import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenEigen;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.BestellpostenHaus;
import de.hsos.swa.coldstoneicecreator.bestellung.entity.Bestellung;

public interface BestellungControl {
    
    public List<Bestellung> bestellungenAbfragen(Long kundeId);

    public Bestellung bestellungAbfragen(Long bestellId, Long kundeId);

    public Bestellung bestellungAnlegen(Bestellung bestellungen, Long kundeId);

    public boolean bestellungLoeschen(Long bestellId, Long kundeId);

    public boolean hauskreationAnpassen(Bestellung bestellung, int kreationsnummer, BestellpostenHaus bestellpostenHaus);

    public boolean eigenkreationAnpassen(Bestellung bestellung, int kreationsnummer, BestellpostenEigen bestellpostenEigen);
}
