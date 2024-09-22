package org.example.springcontext.renderer;

import org.example.springcontext.preprocessor.PreProcessor;
import org.example.springcontext.preprocessor.PreProcessorToLowerImpl;

public class RendererStandartImpl implements Renderer {

    private PreProcessor preProcessor;

    public RendererStandartImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void render(String text) {
        System.out.println(preProcessor.process(text));
    }

}
