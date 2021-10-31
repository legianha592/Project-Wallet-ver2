package org.walletAPI.response.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.walletAPI.entity.Record;

import java.util.List;

@Data
@AllArgsConstructor
public class GetListRecordResponse {
    List<Record> listRecord;
}
