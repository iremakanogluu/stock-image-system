package Entities;

public class Login {
    private int loginId;
    private String loginEmail;
    private String loginPassword;
    private int personId;

    public Login(int loginId, String loginEmail, String loginPassword, int personId) {
        this.loginId = loginId;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.personId = personId;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "\nLogin{" +
                "loginId=" + loginId +
                ", loginEmail='" + loginEmail + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", personId=" + personId +
                '}';
    }
}
