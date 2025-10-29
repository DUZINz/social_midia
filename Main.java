package com.exercicio.social;

import com.exercicio.social.core.EnvConfig;
import com.exercicio.social.models.Conteudo;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        EnvConfig config = new EnvConfig();
        config.set("twitter.apiKey", "TWITTER_KEY");
        config.set("instagram.token", "INST_TOKEN");
        config.set("linkedin.clientId", "LI_CLIENT");
        config.set("tiktok.secret", "TT_SECRET");

        SocialMediaFactory factory = new SocialMediaFactory(config);
        GerenciadorMidiaSocial manager = new GerenciadorMidiaSocial(factory);

        manager.registerPlatform("twitter");
        manager.registerPlatform("instagram");
        manager.registerPlatform("linkedin");
        manager.registerPlatform("tiktok");

        Conteudo conteudo = new Conteudo("Lançamento do produto", "Confira os detalhes em nosso site!");

        // Publicar imediatamente em todas
        manager.publishToAll(conteudo);

        // Agendar para daqui a 10 segundos (demo)
        LocalDateTime when = LocalDateTime.now().plusSeconds(10);
        manager.scheduleToAll(conteudo, when);

        // Aguarda para a demo ver as publicações agendadas
        Thread.sleep(15000);

        // Mostrar estatísticas simuladas
        manager.listPlatforms().forEach(platform -> {
            System.out.println("Stats for " + platform + ": " + manager.getStats(platform));
        });

        manager.shutdown();
    }
}