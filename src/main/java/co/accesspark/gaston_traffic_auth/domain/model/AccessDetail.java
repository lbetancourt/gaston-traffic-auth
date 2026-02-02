package co.accesspark.gaston_traffic_auth.domain.model;

public class AccessDetail {
    private Integer code;
    private Integer accessCode;
    private String usucodigo;
    private Integer company;
    private String status;
    private String cardId;

    public AccessDetail() {
    }

    public AccessDetail(Integer code, Integer accessCode, String usucodigo, Integer company, String status, String cardId) {
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
