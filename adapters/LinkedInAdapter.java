package com.exercicio.social.adapters;

import com.exercicio.social.core.EnvConfig;
import com.exercicio.social.models.Conteudo;
import com.exercicio.social.core.UnifiedResponse;
import com.exercicio.social.models.Publicacao;

public class LinkedInAdapter extends BaseAdapter {
    public LinkedInAdapter(EnvConfig config) {
        super(config);
    }

    @Override
    public String platformName() {
        return "linkedin";
    }

    @Override
    public UnifiedResponse<Publicacao> publishNow(Conteudo conteudo) {
        Conteudo professional = new Conteudo(conteudo.getTitulo(), conteudo.getCorpo() + "\n\nConteúdo voltado ao público profissional.");
        return super.publishNow(professional);
    }
}
