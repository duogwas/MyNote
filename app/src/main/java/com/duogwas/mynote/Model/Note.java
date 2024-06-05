package com.duogwas.mynote.Model;

public class Note {
    public Long id;
    public String title;
    public String content;
    public int pinned;
    public String created_at;

    public Note(Long id, String title, String content, int pinned, String created_at) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pinned = pinned;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPinned() {
        return pinned;
    }

    public void setPinned(int pinned) {
        this.pinned = pinned;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
