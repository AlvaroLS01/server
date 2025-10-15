/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */
package com.comerzzia.pos.persistence.tickets;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

public interface POSTicketMapper {
      int insert(TicketBean record);

      List<TicketBean> selectByExampleWithBLOBsWithRowbounds(TicketExample example, RowBounds rowBounds);

      List<TicketBean> selectByExampleWithBLOBs(TicketExample example);

      List<TicketBean> selectByExampleWithRowbounds(TicketExample example, RowBounds rowBounds);

      List<TicketBean> selectByExample(TicketExample example);

      TicketBean selectByPrimaryKey(TicketKey key);
    
      TicketBean selectByLocatorKey(TicketLocatorKey key);

      int updateProcesado(TicketBean record);
}