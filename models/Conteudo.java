package com.exercicio.social.models;

public class Conteudo {
    private final String titulo;
    private final String corpo;

    public Conteudo(String titulo, String corpo) {
        this.titulo = titulo;
        this.corpo = corpo;
    }

    public String getTitulo() { return titulo; }
    public String getCorpo() { return corpo; }

    @Override
    public String toString() {
        return "Conteudo{titulo='" + titulo + "', corpo='" + corpo + "'}";
    }
}
