package org.walletAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.walletAPI.entity.Message;
import org.walletAPI.entity.TypeRecord;
import org.walletAPI.message.FinalMessage;
import org.walletAPI.request.typeRecord.CreateTypeRecordRequest;
import org.walletAPI.request.typeRecord.DeleteTypeRecordRequest;
import org.walletAPI.request.typeRecord.UpdateTypeRecordRequest;
import org.walletAPI.response.typeRecord.CreateTypeRecordResponse;
import org.walletAPI.response.typeRecord.DeleteTypeRecordResponse;
import org.walletAPI.response.typeRecord.GetListTypeRecordResponse;
import org.walletAPI.response.typeRecord.UpdateTypeRecordResponse;
import org.walletAPI.service.TypeRecordService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/typeRecord")
public class TypeRecordController {
    @Autowired
    TypeRecordService typeRecordService;

    @GetMapping("/getList")
    public ResponseEntity getListTypeRecord() {
        try {
            Message<GetListTypeRecordResponse> message;
            List<TypeRecord> listTypeRecord = typeRecordService.getListTypeRecord();

            if (listTypeRecord.isEmpty()) {
                message = new Message<>(FinalMessage.NO_TYPE_RECORD, null);
            } else {
                GetListTypeRecordResponse response = new GetListTypeRecordResponse(listTypeRecord);
                message = new Message<>(FinalMessage.GET_LIST_TYPE_RECORD_SUCCESS, response);
            }

            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity createTypeRecord(@RequestBody @Valid CreateTypeRecordRequest createTypeRecordRequest) {
        try {
            Optional<TypeRecord> findTypeRecord = typeRecordService.findByTypeRecordName(createTypeRecordRequest.getTypeRecordName());
            Message<CreateTypeRecordResponse> message;

            if (findTypeRecord.isPresent()) {
                message = new Message<>(FinalMessage.TYPE_RECORD_EXISTED, null);
            } else {
                TypeRecord typeRecord = new TypeRecord();
                typeRecord.setTypeRecordName(createTypeRecordRequest.getTypeRecordName());
                typeRecord.setImageUrl(createTypeRecordRequest.getImageUrl());

                typeRecordService.addTypeRecord(typeRecord);

                message = new Message<>(FinalMessage.CREATE_TYPE_RECORD_SUCCESS, new CreateTypeRecordResponse(typeRecord.getId()));
            }

            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateTypeRecord(@RequestBody @Valid UpdateTypeRecordRequest updateTypeRecordRequest) {
        try {
            Optional<TypeRecord> findTypeRecord = typeRecordService.findByTypeRecordId(updateTypeRecordRequest.getTypeRecordId());
            Message<UpdateTypeRecordResponse> message;

            if (findTypeRecord.isPresent()) {
                TypeRecord typeRecord = findTypeRecord.get();
                typeRecord.setTypeRecordName(updateTypeRecordRequest.getTypeRecordName());
                typeRecord.setImageUrl(updateTypeRecordRequest.getImageUrl());

                typeRecordService.addTypeRecord(typeRecord);

                message = new Message<>(FinalMessage.UPDATE_TYPE_RECORD_SUCCESS, new UpdateTypeRecordResponse(typeRecord));
            } else {
                message = new Message<>(FinalMessage.NO_TYPE_RECORD, null);
            }

            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTypeRecord(@RequestBody @Valid DeleteTypeRecordRequest deleteTypeRecordRequest) {
        try {
            Optional<TypeRecord> findTypeRecord = typeRecordService.findByTypeRecordId(deleteTypeRecordRequest.getTypeRecordId());
            Message<DeleteTypeRecordResponse> message;

            if (findTypeRecord.isPresent()) {
                TypeRecord typeRecord = findTypeRecord.get();
                if (typeRecord.getListRecord().size() != 0) {
                    message = new Message<>(FinalMessage.UNABLE_TO_DELETE_TYPE_RECORD, null);
                } else {
                    typeRecordService.deleteTypeRecord(typeRecord);

                    message = new Message<>(FinalMessage.DELETE_TYPE_RECORD_SUCCESS, new DeleteTypeRecordResponse(deleteTypeRecordRequest.getTypeRecordId()));
                }
            } else {
                message = new Message<>(FinalMessage.NO_TYPE_RECORD, null);
            }

            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
