package com.comerzzia.pos.persistence.cajas;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashJournalHdrKey {
    protected String activityId;

    protected String cashJournalUid;
}