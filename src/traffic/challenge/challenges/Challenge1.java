package traffic.challenge.challenges;

import static traffic.challenge.utils.ImageConstants.*;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.*;
import java.awt.Color;

import java.util.Optional;

import traffic.challenge.bean.Image;
import traffic.challenge.utils.ImageUtils;

/**
 * Classe para resolver o execício 1.
 * 
 * É solicitado que esta placa fique da largura de um pixel, sendo afinada ao máximo possível para ser 
 * usada em um sistema de que identifica e descreve a placa para cegos.
 * Para tanto, a rotina deve constar de uma transformação em tonalidades de cinza, uma inversão de cores, 
 * um threshold de 150 e na sequência utilizar o afinamento de Holt.
 */
public final class Challenge1 implements Challenge {

    /** Construtor do desafio 1 */
    public Challenge1() {}

    @Override
    public void solveChallenge() {
        Optional<Image> optImage = ImageUtils.getImageFromFile(CHALLENGE_1_PATH);
        if (optImage.isEmpty()) {
            return;
        }
        //
        // Transforma imagem em escala de cinza
        //
        Image image = optImage.get();
        int[][] matrix = image.getMatrix();
        int[][] auxMatrix = matrix.clone();
        BufferedImage processed = new BufferedImage(image.getWidth(), image.getHeight(), TYPE_BYTE_GRAY);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(matrix[i][j]);
                auxMatrix[i][j] = (byte) new Color(((c.getRed() + c.getGreen() + c.getBlue()) / 3)).getRGB(); 
            }
        }
        //
        // Inverte as cores
        //
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (new Color(auxMatrix[i][j]).getBlue() > 200) {
                    processed.setRGB(i, j, new Color(0, 0, 0).getRGB());
                } else {
                    processed.setRGB(i, j, new Color(255, 255, 255).getRGB());
                }
            }
        }
        ImageUtils.writeImage(processed, PROCESSED_IMAGE_PATH);
        ImageUtils.showProcessedImage();
    }

}
