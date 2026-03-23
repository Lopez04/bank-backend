package com.example.dto.transfer;

import com.example.constan.TransferStatus;
import com.example.constan.TransferType;
import com.example.entity.AccountsEntity;

import java.util.UUID;

public class TransferRequest {
    public float amount;
    public String description;
    public String referenceCode;
    public TransferStatus status;
    public TransferType type;
    public String originAccount;
    public String destinationAccount;
}
