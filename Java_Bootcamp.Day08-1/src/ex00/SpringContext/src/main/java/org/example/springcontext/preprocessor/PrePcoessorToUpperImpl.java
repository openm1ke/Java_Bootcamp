package org.example.springcontext.preprocessor;

public class PrePcoessorToUpperImpl implements PreProcessor {
    @Override
    public String process(String text) {
        return text.toUpperCase();
    }
}
