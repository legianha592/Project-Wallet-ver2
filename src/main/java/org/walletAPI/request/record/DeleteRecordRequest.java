package org.walletAPI.request.record;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class DeleteRecordRequest {
    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private Long recordId;
}
