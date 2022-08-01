//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dao;

import de.hsos.swa.coldstoneicecreator.kreationen.entity.Eigenkreation;

public class ZutatenIdDAO {

    private Long neueZutatId;
    private Eigenkreation eigenkreation;

    public ZutatenIdDAO(){
    }

    public ZutatenIdDAO(Long neueZutatId, Eigenkreation eigenkreation) {
        this.neueZutatId = neueZutatId;
        this.eigenkreation = eigenkreation;
    }

    public Long getNeueZutatId() {
        return this.neueZutatId;
    }

    public Eigenkreation getEigenkreation() {
        return this.eigenkreation;
    }
}
