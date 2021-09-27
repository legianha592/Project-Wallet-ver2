package org.walletAPI.request.record;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class UpdateRecordRequest {
    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private Long recordId;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    @Length(max = 50, message = "{exception.length.of.title.is.invalid}")
    private String title;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    @Length(max = 50, message = "{exception.length.of.note.is.invalid}")
    private String note;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private Double amount;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private Date recordDate;

    @NotBlank(message = "{exception.mandatory.field.is.empty}")
    private Long typeRecordId;
}
