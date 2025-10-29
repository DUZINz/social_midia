package com.exercicio.social.strategies;

import com.exercicio.social.adapters.SocialPlatformAdapter;
import com.exercicio.social.models.Conteudo;
import com.exercicio.social.core.UnifiedResponse;
import com.exercicio.social.models.Publicacao;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

public interface PostingStrategy {
    void execute(SocialPlatformAdapter adapter,
                 Conteudo conteudo,
                 LocalDateTime when,
                 ScheduledExecutorService scheduler,
                 Consumer<UnifiedResponse<Publicacao>> callback);
}
