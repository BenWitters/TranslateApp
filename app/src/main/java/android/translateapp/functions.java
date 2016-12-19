package android.translateapp;

/**
 * Created by yaron on 18/12/16.
 */

public class functions {
    public static boolean empty( final String s ) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }
}
