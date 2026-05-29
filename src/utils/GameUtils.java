package utils;

public class GameUtils {
    public static void slowPrint(String text, int delay) {
        for (char ch : text.toCharArray()) {
            System.out.print(ch);
            try { // ловит потенциально опасный участок кода
                Thread.sleep(delay); // Задержка между символами в мс
            } catch (InterruptedException e) { // обрабатывает исключительность, не ложит программу просто пропускает ошибку
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static void drawLine(int length, int delay) {
        for (int i = 0; i < length; i++) {
            System.out.print("="); // Можно использовать "=", "_" или "~"
            pause(delay);
        }
        System.out.println();
    }
}
