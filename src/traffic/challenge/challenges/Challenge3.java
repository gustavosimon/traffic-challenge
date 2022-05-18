package traffic.challenge.challenges;

import static traffic.challenge.utils.ImageConstants.*;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.*;
import java.awt.Color;

import java.util.Optional;

import traffic.challenge.bean.Image;
import traffic.challenge.utils.ImageUtils;

/**
 * Classe para resolver o execício 3.
 * 
 * Observe que nesta placa existe uma falha no pneu de trás da bicicleta. Use o fechamento para corrigir a falha, deixando a placa perfeita. 
 * Utilize 150 como threshold.
 */
public final class Challenge3 implements Challenge {

    /** Construtor do desafio 1 */
    public Challenge3() {}

    @Override
    public void solveChallenge() {
        Optional<Image> optImage = ImageUtils.getImageFromFile(CHALLENGE_3_PATH);
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
                if (new Color(auxMatrix[i][j]).getBlue() > 150) {
                    matrix[i][j] = new Color(0, 0, 0).getRGB();
                } else {
                    matrix[i][j] = new Color(255, 255, 255).getRGB();
                }
            }
        }
        //
        // Aplica a primeira dilatação na imagem
        // 
        for (int i = 2; i < image.getWidth() - 2; i++) {
            for (int j = 2; j < image.getHeight() - 2; j++) {
                if (new Color(matrix[i][j]).getRed() == 255 &&
                    new Color(matrix[i+1][j]).getRed() == 255 && 
                    new Color(matrix[i][j+1]).getRed() == 255 &&
                    new Color(matrix[i+1][j+1]).getRed() == 255) {
                    for (int x = i - 2; x < i + 2; x++) {
                        for (int y = j - 2; y < j + 2; y++) {
                            matrix[x][y] = new Color(255, 255, 255).getRGB();
                        }
                    }
                }
            }
        }
        //
        // Aplica a segunda dilatação na imagem
        // 
        for (int i = 2; i < image.getWidth() - 2; i++) {
            for (int j = 2; j < image.getHeight() - 2; j++) {
                if (new Color(matrix[i][j]).getRed() == 255 &&
                    new Color(matrix[i+1][j]).getRed() == 255 && 
                    new Color(matrix[i][j+1]).getRed() == 255 &&
                    new Color(matrix[i+1][j+1]).getRed() == 255) {
                    for (int x = i - 2; x < i + 2; x++) {
                        for (int y = j - 2; y < j + 2; y++) {
                            matrix[x][y] = new Color(255, 255, 255).getRGB();
                        }
                    }
                }
            }
        }
        //
        // Aplica a primeira erosão na imagem
        // 
        for (int i = 2; i < image.getWidth() - 2; i++) {
            for (int j = 2; j < image.getHeight() - 2; j++) {
                if (new Color(matrix[i][j]).getRed() != 255 ||
                    new Color(matrix[i+1][j]).getRed() != 255 || 
                    new Color(matrix[i][j+1]).getRed() != 255 ||
                    new Color(matrix[i+1][j+1]).getRed() != 255) {
                    for (int x = i - 2; x < i + 1; x++) {
                        for (int y = j - 2; y < j + 1; y++) {
                            matrix[x][y] = new Color(0, 0, 0).getRGB();
                        }
                    }
                }
            }
        }
        //
        // Aplica a segunda erosão na imagem
        // 
        for (int i = 2; i < image.getWidth() - 2; i++) {
            for (int j = 2; j < image.getHeight() - 2; j++) {
                if (new Color(matrix[i][j]).getRed() != 255 ||
                    new Color(matrix[i+1][j]).getRed() != 255 || 
                    new Color(matrix[i][j+1]).getRed() != 255 ||
                    new Color(matrix[i+1][j+1]).getRed() != 255) {
                    for (int x = i - 2; x < i + 1; x++) {
                        for (int y = j - 2; y < j + 1; y++) {
                            matrix[x][y] = new Color(0, 0, 0).getRGB();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                processed.setRGB(i, j, new Color(matrix[i][j]).getRGB());
            }
        }
        ImageUtils.writeImage(processed, PROCESSED_IMAGE_PATH);
        ImageUtils.showProcessedImage();
    }

}
