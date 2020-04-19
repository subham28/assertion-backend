package com.assertion.assignment.response;

public class RequestVO
{
    private String websiteName;
    private String passString;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getPassString() {
        return passString;
    }

    public void setPassString(String passString) {
        this.passString = passString;
    }

    @Override
    public String toString() {
        return "RequestVO{" +
                "websiteName='" + websiteName + '\'' +
                ", passString='" + passString + '\'' +
                '}';
    }
}
