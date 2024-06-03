package xyz.astradev.objects;

import xyz.astradev.AstraApi;

public record Hash(long id, String sha256, String sha3_384, String classification, String mime_type, long addition_date, long detection_date, boolean sample_available) {
}
