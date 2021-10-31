package org.walletAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.walletAPI.entity.Record;
import org.walletAPI.entity.TypeRecord;
import org.walletAPI.entity.User;
import org.walletAPI.entity.Wallet;
import org.walletAPI.repository.RecordRepository;
import org.walletAPI.request.record.CreateRecordRequest;
import org.walletAPI.request.record.UpdateRecordRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {
    @Autowired
    RecordRepository recordRepository;

    public void addRecord(Record record) {
        recordRepository.save(record);
    }

    public void deleteRecord(Record record) {
        recordRepository.delete(record);
    }

    public Optional<Record> findByRecordId (Long recordId) {
        return recordRepository.findById(recordId);
    }

    public List<Record> findByWalletId(Wallet wallet) {
        List<Record> listRecord = wallet.getListRecord();
        return listRecord;
    }

    public List<Record> getListRecordByUser(User user) {
        List<Wallet> listWallet = user.getListWallet();
        List<Record> listRecord = new ArrayList<>();
        listWallet.stream().forEach(wallet -> {
            List<Record> listRecordByWallet = wallet.getListRecord();
            listRecordByWallet.stream().forEach(record -> listRecord.add(record));
        });
        return listRecord;
    }

    public Record initRecordFromRequest(CreateRecordRequest request, TypeRecord typeRecord) {
        Record record = new Record();
        record.setRecordDate(request.getRecordDate());
        record.setTitle(request.getTitle());
        record.setNote(request.getNote());
        record.setAmount(request.getAmount());
        record.setTypeRecord(typeRecord);
        return record;
    }

    public Record updateRecordFromRequest(UpdateRecordRequest request, Record record, TypeRecord typeRecord) {
        record.setTitle(request.getTitle());
        record.setNote(request.getNote());
        record.setAmount(request.getAmount());
        record.setRecordDate(request.getRecordDate());
        record.setTypeRecord(typeRecord);
        return record;
    }
}
