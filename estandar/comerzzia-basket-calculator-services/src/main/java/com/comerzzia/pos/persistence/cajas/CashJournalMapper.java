package com.comerzzia.pos.persistence.cajas;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CashJournalMapper {
    int insertFechaAperturaDate(CashJournalHdr record);
    
    int insertFechaAperturaDateTime(CashJournalHdr record);

    List<CashJournalHdr> selectByExample(CashJournalExample example);

    CashJournalHdr selectByPrimaryKey(CashJournalHdrKey key);
    
    int cierreCajaDateByPrimaryKey(CashJournalHdr record);
    
    int cierreCajaDateTimeByPrimaryKey(CashJournalHdr record);
    
    BigDecimal contAcumuladoCaja(@Param("activityId") String activityId, @Param("cashJournalUid") String cashJournalUid);
}