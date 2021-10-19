package org.walletAPI.response.record;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteRecordResponse {
    private long recordId;
}
