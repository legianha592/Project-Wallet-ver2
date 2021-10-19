package org.walletAPI.response.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.walletAPI.entity.Record;

@Data
@AllArgsConstructor
public class UpdateRecordResponse {
    private Record record;
}
