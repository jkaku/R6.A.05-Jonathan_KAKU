package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Status;
import java.sql.Timestamp;

public class AnnonceDTO {
    private Long id;
    private String title;
    private String description;
    private String adress;
    private String mail;
    private Timestamp date;
    private Status status;
    private String authorName;
    private Long categoryId;
    private String categoryLabel;

    public AnnonceDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryLabel() { return categoryLabel; }
    public void setCategoryLabel(String categoryLabel) { this.categoryLabel = categoryLabel; }
}