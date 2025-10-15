package com.comerzzia.pos.persistence.cajas.movimientos;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CashJournalLineMapper {
    int insert(CashJournalLine record);

    List<CashJournalLine> selectByExample(CashJournalLineExample example);

    CashJournalLine selectByPrimaryKey(CashJournalLineKey key);

    Integer selectMaxLineId(CashJournalLineExample example);
    
    List<CashJournalSummaryDTO> selectCashJournalSummary(@Param("activityId")String activityId, @Param("cashJournalUid") String cashJournalUid);    
}