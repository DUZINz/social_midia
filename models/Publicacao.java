package com.exercicio.social.models;

import java.time.LocalDateTime;

public class Publicacao {
    private final String id;
    private final String plataforma;
    private final Conteudo conteudo;
    private final LocalDateTime horario;

    public Publicacao(String id, String plataforma, Conteudo conteudo, LocalDateTime horario) {
        this.id = id;
        this.plataforma = plataforma;
        this.conteudo = conteudo;
        this.horario = horario;
    }

    public String getId() { return id; }
    public String getPlataforma() { return plataforma; }
    public Conteudo getConteudo() { return conteudo; }
    public LocalDateTime getHorario() { return horario; }

    @Override
    public String toString() {
        return "Publicacao{id='" + id + "', plataforma='" + plataforma + "', horario=" + horario + "}";
    }
}
