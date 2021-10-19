package org.walletAPI.response.typeRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.walletAPI.entity.TypeRecord;

@Data
@AllArgsConstructor
public class UpdateTypeRecordResponse {
    private TypeRecord typeRecord;
}
