package org.walletAPI.response.record;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRecordResponse {
    private long recordId;

    private long walletId;

    private long typeRecordId;
}
