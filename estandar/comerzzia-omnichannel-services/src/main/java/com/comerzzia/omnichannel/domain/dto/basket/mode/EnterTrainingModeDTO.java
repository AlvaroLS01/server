package com.comerzzia.omnichannel.domain.dto.basket.mode;

import com.comerzzia.omnichannel.domain.dto.basket.PermissionOperation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnterTrainingModeDTO implements PermissionOperation  {
   protected final String operationCode = "TRAINING_MODE";

}
