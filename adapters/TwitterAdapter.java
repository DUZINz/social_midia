package com.exercicio.social.adapters;

import com.exercicio.social.core.EnvConfig;
import com.exercicio.social.models.Conteudo;
import com.exercicio.social.core.UnifiedResponse;
import com.exercicio.social.models.Publicacao;

public class TwitterAdapter extends BaseAdapter {
    public TwitterAdapter(EnvConfig config) {
        super(config);
    }

    @Override
    public String platformName() {
        return "twitter";
    }

    @Override
    public UnifiedResponse<Publicacao> publishNow(Conteudo conteudo) {
        String corpo = conteudo.getCorpo();
        if (corpo.length() > 280) corpo = corpo.substring(0, 277) + "...";
        Conteudo adaptado = new Conteudo(conteudo.getTitulo(), corpo);
        return super.publishNow(adaptado);
    }
}
