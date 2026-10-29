
package jogo.reciclagem;

import javax.sound.sampled.*;

public final class AudioPlayer {

    private static Clip musicaDeFundo;

    private AudioPlayer() {
        // Impede a criação de instâncias desta classe.
    }

    public static void tocarMusicaDeFundo() {
        if (musicaDeFundo != null && musicaDeFundo.isRunning()) {
            return;
        }

        try {
            if (musicaDeFundo == null || !musicaDeFundo.isOpen()) {
                java.net.URL recurso =
                    AudioPlayer.class.getResource("/musica_fundo.wav");

                if (recurso == null) {
                    System.err.println(
                        "Arquivo musica_fundo.wav não encontrado."
                    );
                    return;
                }

                AudioInputStream audioIn =
                    AudioSystem.getAudioInputStream(recurso);

                musicaDeFundo = AudioSystem.getClip();
                musicaDeFundo.open(audioIn);
                audioIn.close();
            }

            musicaDeFundo.setFramePosition(0);
            musicaDeFundo.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            System.err.println(
                "Erro ao iniciar a música: " + e.getMessage()
            );
        }
    }

    public static void pararMusica() {
        if (musicaDeFundo != null && musicaDeFundo.isOpen()) {
            musicaDeFundo.stop();
            musicaDeFundo.setFramePosition(0);
        }
    }
}
