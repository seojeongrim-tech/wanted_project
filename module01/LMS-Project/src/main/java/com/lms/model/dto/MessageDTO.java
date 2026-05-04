package com.lms.model.dto;

public class MessageDTO {
    private int id;
    private String receiverId;
    private String content;
    private String userId;

    public MessageDTO() {
    }

    public MessageDTO(int id, String receiverId, String content, String userId) {
        this.id = id;
        this.receiverId = receiverId;
        this.content = content;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
