package com.comerzzia.bricodepot.api.persistence.pagos.mediospago;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MediosPagosMapper {

        List<MediosPago> getMediosPago(@Param("uidActividad") String uidActividad, @Param("uidTicket") String uidTicket);

}
