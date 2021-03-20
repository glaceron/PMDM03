package com.example.PMDM.model;

public class Web {

    private String nombre;
    private String link;
    private String email;
    private String categoria;
    private String foto;

    private long id; // El ID de la BD

    public Web(String nombre, String link, String email, String categoria, String foto, long id) {
        this.nombre = nombre;
        this.link = link;
        this.email = email;
        this.categoria = categoria;
        this.foto = foto;
        this.id = id;
    }

    public Web(String nombre, String link, String email, String categoria, String foto) {
        this.nombre = nombre;
        this.link = link;
        this.email = email;
        this.categoria = categoria;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Web{" +
                "nombre='" + nombre + '\'' +
                ", link='" + link + '\'' +
                ", email='" + email + '\'' +
                ", categoria='" + categoria + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
