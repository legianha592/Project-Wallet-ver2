package org.walletAPI.request.typeRecord;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class CreateTypeRecordRequest {
    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    @Length(max = 50, message = "{exception.length.of.type.record.name.is.invalid}")
    private String typeRecordName;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private String imageUrl;
}
