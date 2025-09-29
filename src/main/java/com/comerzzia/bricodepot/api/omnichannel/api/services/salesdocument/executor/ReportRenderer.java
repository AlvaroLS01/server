package com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.executor;

import com.comerzzia.bricodepot.api.omnichannel.api.services.salesdocument.model.TrabajoInformeBean;

public interface ReportRenderer {

    byte[] render(TrabajoInformeBean trabajoInformeBean);
}

