
package jogo.reciclagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class TelaJogo extends JFrame {

    private JPanel painel;
    private JLabel labelPontos;
    private JLabel labelCronometro;
    private JButton btnEncerrar;

    private int pontos = 0;
    private int tempoRestante = 120;

    private Random random = new Random();

    private java.util.List<JLabel> lixosNaTela = new ArrayList<>();
    private java.util.List<Timer> timersMovimento = new ArrayList<>();

    private Timer timerCronometro;
    private Timer timerGerador;

    private boolean partidaEncerrada = false;

    private String[] tiposLixo = {
        "papel", "plastico", "metal", "vidro", "comida"
    };

    private Map<String, String> corLixeiraMap = Map.of(
        "papel", "azul",
        "plastico", "vermelha",
        "metal", "amarela",
        "vidro", "verde",
        "comida", "marrom"
    );

    public TelaJogo() {
        setTitle("Jogo da Reciclagem - Fase");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        painel = new JPanel(null) {
            Image imagemFundo = new ImageIcon(
                getClass().getResource("/fundo_parque.jpeg")
            ).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(
                    imagemFundo, 0, 0,
                    getWidth(), getHeight(), this
                );
            }
        };

        setContentPane(painel);

        // Pontuação
        labelPontos = new JLabel("Acertos: 0");
        labelPontos.setFont(new Font("Arial", Font.BOLD, 30));
        labelPontos.setForeground(Color.WHITE);
        labelPontos.setBounds(0, 20, 300, 40);
        labelPontos.setHorizontalAlignment(SwingConstants.CENTER);
        painel.add(labelPontos);

        // Cronômetro
        labelCronometro = new JLabel("Tempo: 2:00");
        labelCronometro.setFont(new Font("Arial", Font.BOLD, 30));
        labelCronometro.setForeground(Color.WHITE);
        labelCronometro.setBounds(0, 20, 200, 40);
        painel.add(labelCronometro);

        // Botão para encerrar a partida
        btnEncerrar = new JButton("Encerrar partida");
        btnEncerrar.setFont(new Font("Arial", Font.BOLD, 18));
        btnEncerrar.setBackground(new Color(180, 40, 40));
        btnEncerrar.setForeground(Color.WHITE);
        btnEncerrar.setBounds(20, 20, 220, 40);
        btnEncerrar.addActionListener(e -> finalizarPartida());
        painel.add(btnEncerrar);

        // Atualiza as posições ao redimensionar a janela
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int largura = painel.getWidth();

                labelPontos.setBounds(
                    largura / 2 - 150, 20, 300, 40
                );

                labelCronometro.setBounds(
                    largura - 250, 20, 220, 40
                );

                posicionarLixeiras();
            }
        });

        posicionarLixeiras();

        setVisible(true);

        iniciarCronometro();
        iniciarGeradorLixos();
    }

    private void posicionarLixeiras() {
        String[] cores = {
            "azul", "vermelha", "amarela", "verde", "marrom"
        };

        // Remove lixeiras antigas
        Component[] componentes = painel.getComponents();

        for (int i = componentes.length - 1; i >= 0; i--) {
            if (componentes[i] instanceof JLabel) {
                JLabel label = (JLabel) componentes[i];

                if (label.getName() != null
                        && label.getName().startsWith("lixeira")) {
                    painel.remove(label);
                }
            }
        }

        int larguraTotal = painel.getWidth();
        int espaco = larguraTotal / (cores.length + 1);
        int y = painel.getHeight() - 200;
        int tamanho = 150;

        for (int i = 0; i < cores.length; i++) {
            String cor = cores[i];

            java.net.URL recurso = getClass().getResource(
                "/lixeira_" + cor + ".png"
            );

            if (recurso == null) {
                System.err.println(
                    "Imagem da lixeira não encontrada: " + cor
                );
                continue;
            }

            Image imagem = new ImageIcon(recurso)
                .getImage()
                .getScaledInstance(
                    tamanho, tamanho, Image.SCALE_SMOOTH
                );

            JLabel lixeira = new JLabel(new ImageIcon(imagem));

            lixeira.setBounds(
                espaco * (i + 1) - tamanho / 2,
                y,
                tamanho,
                tamanho
            );

            lixeira.setName("lixeira_" + cor);
            painel.add(lixeira);
        }

        painel.repaint();
    }

    private void iniciarCronometro() {
        timerCronometro = new Timer(1000, e -> {
            if (partidaEncerrada) {
                timerCronometro.stop();
                return;
            }

            tempoRestante--;

            int minutos = tempoRestante / 60;
            int segundos = tempoRestante % 60;

            labelCronometro.setText(
                String.format(
                    "Tempo: %d:%02d", minutos, segundos
                )
            );

            if (tempoRestante <= 0) {
                finalizarPartida();
            }
        });

        timerCronometro.start();
    }

    private void iniciarGeradorLixos() {
        timerGerador = new Timer(2000, e -> {
            if (!partidaEncerrada) {
                criarLixoAleatorio();
            }
        });

        timerGerador.start();
    }

    private void criarLixoAleatorio() {
        if (partidaEncerrada) {
            return;
        }

        String tipo = tiposLixo[
            random.nextInt(tiposLixo.length)
        ];

        java.net.URL recurso = getClass().getResource(
            "/" + tipo + ".png"
        );

        if (recurso == null) {
            System.err.println(
                "Imagem do resíduo não encontrada: " + tipo
            );
            return;
        }

        Image imagem = new ImageIcon(recurso)
            .getImage()
            .getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        JLabel lixo = new JLabel(new ImageIcon(imagem));
        lixo.setSize(80, 80);

        int alturaDisponivel = Math.max(
            1, painel.getHeight() - 300
        );

        lixo.setLocation(
            -80,
            100 + random.nextInt(alturaDisponivel)
        );

        painel.add(lixo);
        lixosNaTela.add(lixo);

        // Mantém o resíduo acima dos demais componentes
        painel.setComponentZOrder(lixo, 0);
        painel.repaint();

        moverLixo(lixo, tipo);
    }

    private void tocarSomAcerto() {
        try {
            java.net.URL recurso = getClass().getResource(
                "/acerto.wav"
            );

            if (recurso == null) {
                return;
            }

            AudioInputStream audioIn =
                AudioSystem.getAudioInputStream(recurso);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

        } catch (Exception e) {
            System.err.println(
                "Não foi possível reproduzir o som: "
                + e.getMessage()
            );
        }
    }

    private void moverLixo(JLabel lixo, String tipo) {
        final int[] offsetX = new int[1];
        final int[] offsetY = new int[1];

        final Timer[] timerMovimento = new Timer[1];

        lixo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offsetX[0] = e.getX();
                offsetY[0] = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (partidaEncerrada) {
                    return;
                }

                Component[] componentes = painel.getComponents();

                for (Component comp : componentes) {
                    if (comp instanceof JLabel) {
                        JLabel lixeira = (JLabel) comp;

                        if (lixeira.getName() != null
                                && lixeira.getName()
                                    .startsWith("lixeira_")) {

                            Rectangle areaLixo = lixo.getBounds();
                            Rectangle areaLixeira =
                                lixeira.getBounds();

                            if (areaLixo.intersects(areaLixeira)) {
                                String cor = lixeira.getName()
                                    .replace("lixeira_", "");

                                if (cor.equals(
                                        corLixeiraMap.get(tipo))) {
                                    pontos++;

                                    labelPontos.setText(
                                        "Acertos: " + pontos
                                    );

                                    tocarSomAcerto();
                                }

                                removerLixo(lixo, timerMovimento[0]);
                                return;
                            }
                        }
                    }
                }
            }
        });

        lixo.addMouseMotionListener(
            new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (partidaEncerrada) {
                        return;
                    }

                    int x = lixo.getX()
                        + e.getX() - offsetX[0];

                    int y = lixo.getY()
                        + e.getY() - offsetY[0];

                    lixo.setLocation(x, y);
                }
            }
        );

        Timer mover = new Timer(50, null);
        timerMovimento[0] = mover;

        mover.addActionListener(e -> {
            if (partidaEncerrada) {
                mover.stop();
                return;
            }

            // Não movimenta o resíduo enquanto ele é arrastado
            if (!lixo.getMouseListeners()[0].equals(null)
                    && !estaSendoArrastado(lixo)) {
                lixo.setLocation(
                    lixo.getX() + 2,
                    lixo.getY()
                );
            }

            if (lixo.getX() > painel.getWidth()) {
                removerLixo(lixo, mover);
            }
        });

        timersMovimento.add(mover);

        AudioPlayer.tocarMusicaDeFundo();

        mover.start();
    }

    private boolean estaSendoArrastado(JLabel lixo) {
        // O estado é controlado pelos eventos de mouse
        return Boolean.TRUE.equals(
            lixo.getClientProperty("arrastando")
        );
    }

    private void removerLixo(JLabel lixo, Timer timer) {
        if (timer != null) {
            timer.stop();
            timersMovimento.remove(timer);
        }

        lixosNaTela.remove(lixo);
        painel.remove(lixo);
        painel.repaint();
    }

    private void finalizarPartida() {
        if (partidaEncerrada) {
            return;
        }

        partidaEncerrada = true;

        if (timerCronometro != null) {
            timerCronometro.stop();
        }

        if (timerGerador != null) {
            timerGerador.stop();
        }

        for (Timer timer : new ArrayList<>(timersMovimento)) {
            timer.stop();
        }

        timersMovimento.clear();

        dispose();
new TelaFinal(pontos);
    }
}
