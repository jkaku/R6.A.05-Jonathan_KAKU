package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AnnonceCreateDTO {
    @NotBlank(message = "Le titre est requis")
    @Size(max = 64)
    private String title;

    @NotBlank(message = "La description est requise")
    @Size(max = 256)
    private String description;

    @NotBlank(message = "L'adresse est requise")
    @Size(max = 64)
    private String adress;

    @NotBlank(message = "L'email est requis")
    @Email
    private String mail;

    @NotNull(message = "La catégorie est obligatoire")
    private Long categoryId;

    private Long version;

    public String getTitle() { return title; } public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public String getAdress() { return adress; } public void setAdress(String adress) { this.adress = adress; }
    public String getMail() { return mail; } public void setMail(String mail) { this.mail = mail; }
    public Long getCategoryId() { return categoryId; } public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Long getVersion() { return version; } public void setVersion(Long version) { this.version = version; }
}