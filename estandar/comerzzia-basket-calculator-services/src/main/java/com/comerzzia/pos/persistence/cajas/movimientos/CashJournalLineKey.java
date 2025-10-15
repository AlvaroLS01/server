package com.comerzzia.pos.persistence.cajas.movimientos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalLineKey {
    protected String activityId;

    protected String cashJournalUid;

    protected Integer lineId;
}