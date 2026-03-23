package com.example.dto.transfer;

import com.example.constan.TransferStatus;
import com.example.constan.TransferType;
import com.example.entity.AccountsEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class TransferResponse {
    public float amount;
    public String description;
    public String referenceCode;
    public TransferStatus status;
    public TransferType type;
    public String originAccount;
    public String destinationAccount;
    public String destinatario;
    public String emisor;
    public LocalDateTime createAt;
    public LocalDateTime processedAt;
}
