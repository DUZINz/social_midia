package com.exercicio.social.adapters;

import com.exercicio.social.core.EnvConfig;
import com.exercicio.social.models.Conteudo;
import com.exercicio.social.core.UnifiedResponse;
import com.exercicio.social.models.Publicacao;

public class InstagramAdapter extends BaseAdapter {
    public InstagramAdapter(EnvConfig config) {
        super(config);
    }

    @Override
    public String platformName() {
        return "instagram";
    }

    @Override
    public UnifiedResponse<Publicacao> publishNow(Conteudo conteudo) {
        // Simula enriquecimento (hashtags, imagem obrigatória em API real)
        Conteudo enriched = new Conteudo(conteudo.getTitulo(), conteudo.getCorpo() + "\n\n#instagram #promo");
        return super.publishNow(enriched);
    }
}
