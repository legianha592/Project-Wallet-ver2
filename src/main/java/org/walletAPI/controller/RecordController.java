package org.walletAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.walletAPI.entity.Message;
import org.walletAPI.entity.Record;
import org.walletAPI.entity.TypeRecord;
import org.walletAPI.entity.User;
import org.walletAPI.entity.Wallet;
import org.walletAPI.message.FinalMessage;
import org.walletAPI.request.record.CreateRecordRequest;
import org.walletAPI.request.record.DeleteRecordRequest;
import org.walletAPI.request.record.UpdateRecordRequest;
import org.walletAPI.response.record.CreateRecordResponse;
import org.walletAPI.response.record.DeleteRecordResponse;
import org.walletAPI.response.record.GetListRecordResponse;
import org.walletAPI.response.record.UpdateRecordResponse;
import org.walletAPI.service.RecordService;
import org.walletAPI.service.TypeRecordService;
import org.walletAPI.service.UserService;
import org.walletAPI.service.WalletService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordService recordService;

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @Autowired
    TypeRecordService typeRecordService;

    @GetMapping("/listByWalletId")
    public ResponseEntity getListRecordByWalletId(@RequestParam(name = "walletId") long walletId) {
        try {
            Optional<Wallet> findWallet = walletService.findByWalletId(walletId);
            Message<GetListRecordResponse> message;

            if (findWallet.isPresent()) {
                List<Record> listRecord = recordService.findByWalletId(findWallet.get());
                GetListRecordResponse response = new GetListRecordResponse(listRecord);
                message = new Message<>(FinalMessage.GET_LIST_RECORD_SUCCESS, response);
            } else {
                message = new Message<>(FinalMessage.NO_WALLET, null);
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listByUserId")
    public ResponseEntity getListRecordByUserId(@RequestParam(name = "userId") long userId) {
        try {
            Optional<User> findUser = userService.findByUserId(userId);
            Message<GetListRecordResponse> message;

            if (findUser.isPresent()) {
                List<Record> listRecord = recordService.getListRecordByUser(findUser.get());
                GetListRecordResponse response = new GetListRecordResponse(listRecord);
                message = new Message<>(FinalMessage.GET_LIST_RECORD_SUCCESS, response);
            } else {
                message = new Message<>(FinalMessage.NO_USER, null);
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity createRecord(@RequestBody CreateRecordRequest request) {
        try {
            Optional<Wallet> findWallet = walletService.findByWalletId(request.getWalletId());
            Optional<TypeRecord> findTypeRecord = typeRecordService.findByTypeRecordId(request.getTypeRecordId());
            Message<CreateRecordResponse> message;

            if (!findWallet.isPresent()) {
                message = new Message<>(FinalMessage.NO_WALLET, null);
            } else if (!findTypeRecord.isPresent()) {
                message = new Message<>(FinalMessage.NO_TYPE_RECORD, null);
            } else {
                //Tạo đối tượng record mới + lấy type record từ db
                TypeRecord typeRecord = findTypeRecord.get();

                //setup thông tin phía record
                Record record = recordService.initRecordFromRequest(request, typeRecord);

                //setup phía wallet: 1 wallet gồm nhiều record, nhiều type record
                Wallet wallet = findWallet.get();
                walletService.addRecordToWallet(wallet, record);

                //sau khi add record cần cập nhật ngay total amount của ví tại db
                recordService.addRecord(record);
                walletService.updateWallet(wallet, record.getAmount());

                message = new Message<>(FinalMessage.CREATE_RECORD_SUCCESS, new CreateRecordResponse(record.getId(), wallet.getId(), typeRecord.getId()));
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateRecord(@RequestBody UpdateRecordRequest request) {
        try {
            Optional<Record> findRecord = recordService.findByRecordId(request.getRecordId());
            Optional<TypeRecord> findTypeRecord = typeRecordService.findByTypeRecordId(request.getTypeRecordId());
            Message<UpdateRecordResponse> message;

            if (!findRecord.isPresent()) {
                message = new Message<>(FinalMessage.NO_RECORD, null);
            } else if (!findTypeRecord.isPresent()) {
                message = new Message<>(FinalMessage.NO_TYPE_RECORD, null);
            } else {
                //lấy record cần sửa + ví chủ quản để sửa amount của ví
                Record record = findRecord.get();
                Wallet wallet = record.getWallet();
                TypeRecord typeRecord = record.getTypeRecord();

                //Các thông số quan trọng có thể update: total_amount của ví chủ + mối liên hệ wallet - typeRecord
                Double delta = request.getAmount() - record.getAmount();
                Record newRecord = recordService.updateRecordFromRequest(request, record, typeRecord);

                //B1: Vi co record moi
                recordService.addRecord(newRecord);
                //B2: Vi cap nhat lai total_amount
                walletService.updateWallet(wallet, delta);

                message = new Message<>(FinalMessage.UPDATE_RECORD_SUCCESS, new UpdateRecordResponse(newRecord));
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteRecord(@RequestBody DeleteRecordRequest request) {
        try {
            Optional<Record> findRecord = recordService.findByRecordId(request.getRecordId());
            Message<DeleteRecordResponse> message;

            if (!findRecord.isPresent()) {
                message = new Message<>(FinalMessage.NO_RECORD, null);
            } else {
                Record record = findRecord.get();
                Wallet wallet = record.getWallet();

                Double delta = record.getAmount();

                recordService.deleteRecord(record);
                walletService.updateWallet(wallet, -delta);

                message = new Message<>(FinalMessage.DELETE_RECORD_SUCCESS, new DeleteRecordResponse(
                        request.getRecordId()));
            }
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
