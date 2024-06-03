package xyz.astradev.objects;

public record Quiz(long id, String question, String answer, String wrong_a, String wrong_b, String wrong_c, String category, String keywords) {
}
