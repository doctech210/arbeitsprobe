//@author Marvin Gels 868603

package de.hsos.swa.coldstoneicecreator.kreationen.boundary.dto;

import java.util.List;

public class KreationIdDTO {

    public Long eissorte1Id;
    public Long eissorte2Id;
    public List<Long> zutatenId;
    public Long sauceId;
    public Long anzahl;
    public String name;

    public KreationIdDTO (){
    }

    public KreationIdDTO (Long eissorte1Id, Long eissorte2Id, List<Long> zutatenId, Long sauceId, Long anzahl, String name) {
        this.eissorte1Id = eissorte1Id;
        this.eissorte2Id = eissorte2Id;
        this.zutatenId = zutatenId;
        this.sauceId = sauceId;
        this.anzahl = anzahl;
        this.name = name;
    }

} 