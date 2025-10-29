package com.exercicio.social.adapters;

import com.exercicio.social.core.EnvConfig;
import com.exercicio.social.models.Conteudo;
import com.exercicio.social.core.UnifiedResponse;
import com.exercicio.social.models.Publicacao;

public class TikTokAdapter extends BaseAdapter {
    public TikTokAdapter(EnvConfig config) {
        super(config);
    }

    @Override
    public String platformName() {
        return "tiktok";
    }

    @Override
    public UnifiedResponse<Publicacao> publishNow(Conteudo conteudo) {
        String corpo = conteudo.getCorpo();
        if (corpo.length() > 150) corpo = corpo.substring(0, 147) + "...";
        Conteudo shortForm = new Conteudo(conteudo.getTitulo(), corpo);
        return super.publishNow(shortForm);
    }
}
