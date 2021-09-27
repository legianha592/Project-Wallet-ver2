package org.walletAPI.request.typeRecord;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class DeleteTypeRecordRequest {
    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private Long typeRecordId;
}
