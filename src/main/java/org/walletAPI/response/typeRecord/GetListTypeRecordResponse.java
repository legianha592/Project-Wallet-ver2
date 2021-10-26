package org.walletAPI.response.typeRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.walletAPI.entity.TypeRecord;

import java.util.List;

@Data
@AllArgsConstructor
public class GetListTypeRecordResponse {
    List<TypeRecord> listTypeRecord;
}
