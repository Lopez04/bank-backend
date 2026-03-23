package com.example.entity;

import com.example.constan.TransferStatus;
import com.example.constan.TransferType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transfer")
public class TransfersEntity {

    @Id
    @GeneratedValue
    private UUID idTransfer;
    private float amount;
    private String description;
    private String referenceCode;
    @Enumerated(EnumType.STRING)
    private TransferStatus status;
    @Enumerated(EnumType.STRING)
    private TransferType type;
    @JoinColumn(name = "originAccount_fk")
    @ManyToOne
    private AccountsEntity originAccount;
    @JoinColumn(name = "destinationAccount_fk")
    @ManyToOne
    private AccountsEntity destinationAccount;
    private LocalDateTime createAt;
    private LocalDateTime processedAt;

    @PrePersist
    public void prePersist(){
        this.createAt= LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        processedAt = LocalDateTime.now();
    }

    public TransfersEntity (){}
    public TransfersEntity (TransfersEntity te){
        this.amount = te.amount;
        this.description = te.description;
        this.referenceCode = te.referenceCode;
        this.status = te.status;
        this.type = te.type;
        this.originAccount = te.originAccount;
        this.destinationAccount = te.destinationAccount;
    }


    public UUID getIdTransfer() {
        return idTransfer;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    public TransferType getType() {
        return type;
    }

    public void setType(TransferType type) {
        this.type = type;
    }

    public AccountsEntity getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(AccountsEntity originAccount) {
        this.originAccount = originAccount;
    }

    public AccountsEntity getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(AccountsEntity destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}
