package org.walletAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.walletAPI.entity.TypeRecord;
import org.walletAPI.repository.TypeRecordRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TypeRecordService {
    @Autowired
    TypeRecordRepository typeRecordRepository;

    public List<TypeRecord> getListTypeRecord() {
        return typeRecordRepository.findAll();
    }

    public Optional<TypeRecord> findByTypeRecordName(String typeRecordName) {
        return typeRecordRepository.findByTypeRecordName(typeRecordName);
    }

    public void addTypeRecord(TypeRecord typeRecord) {
        typeRecordRepository.save(typeRecord);
    }
}
