package com.exercicio.social.strategies;

import com.exercicio.social.adapters.SocialPlatformAdapter;
import com.exercicio.social.models.Conteudo;
import com.exercicio.social.core.UnifiedResponse;
import com.exercicio.social.models.Publicacao;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ScheduledStrategy implements PostingStrategy {
    @Override
    public void execute(SocialPlatformAdapter adapter, Conteudo conteudo, LocalDateTime when, ScheduledExecutorService scheduler, Consumer<UnifiedResponse<Publicacao>> callback) {
        long delay = Math.max(0, java.time.Duration.between(LocalDateTime.now(), when).toMillis());
        scheduler.schedule(() -> {
            UnifiedResponse<Publicacao> resp = adapter.publishNow(conteudo);
            callback.accept(resp);
        }, delay, TimeUnit.MILLISECONDS);
    }
}
