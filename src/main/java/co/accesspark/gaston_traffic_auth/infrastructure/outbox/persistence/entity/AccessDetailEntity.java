package co.accesspark.gaston_traffic_auth.infrastructure.outbox.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "access_detail", schema = "core")
public class AccessDetailEntity {
    @Id
    private Integer code;
    @Column("access_code")
    private Integer accessCode;
    private String usucodigo;
    private Integer company;
    private String status;
    @Column("card_id")
    private String cardId;

    public AccessDetailEntity() {
    }

    public AccessDetailEntity(Integer code, Integer accessCode, String usucodigo, Integer company, String status, String cardId) {
        this.code = code;
        this.accessCode = accessCode;
        this.usucodigo = usucodigo;
        this.company = company;
        this.status = status;
        this.cardId = cardId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(Integer accessCode) {
        this.accessCode = accessCode;
    }

    public String getUsucodigo() {
        return usucodigo;
    }

    public void setUsucodigo(String usucodigo) {
        this.usucodigo = usucodigo;
    }

    public Integer getCompany() {
        return company;
    }

    public void setCompany(Integer company) {
        this.company = company;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
