package com.exercicio.social.adapters;

import com.exercicio.social.core.UnifiedResponse;
import com.exercicio.social.models.Conteudo;
import com.exercicio.social.models.Publicacao;
import com.exercicio.social.models.Estatisticas;

import java.time.LocalDateTime;

public interface SocialPlatformAdapter {
    UnifiedResponse<Boolean> authenticate();
    UnifiedResponse<Publicacao> publishNow(Conteudo conteudo);
    UnifiedResponse<Publicacao> schedulePublish(Conteudo conteudo, LocalDateTime when);
    UnifiedResponse<Estatisticas> getStatistics(String publicationId);
    UnifiedResponse<Boolean> deletePublication(String publicationId);
    String platformName();
}
