package org.example.springcontext;

import org.example.springcontext.preprocessor.PrePcoessorToUpperImpl;
import org.example.springcontext.preprocessor.PreProcessor;
import org.example.springcontext.printer.Printer;

import org.example.springcontext.printer.PrinterWithDateTimeImpl;
import org.example.springcontext.printer.PrinterWithPrefixImpl;
import org.example.springcontext.renderer.Renderer;
import org.example.springcontext.renderer.RendererErrImpl;
import org.example.springcontext.renderer.RendererStandartImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");

		// PrinterWithDate RenderStandart ProcessorToLower
		Printer printer = applicationContext.getBean("printerWithDateTimeRendererStandartPreProcessorToLower", Printer.class); //Printer
		printer.print("DateTime RenderStandart ProcessorToLower");

		// PrinterWithPrefix RenderStandart ProcessorToUpper
		printer = applicationContext.getBean("printerWithPrefixRendererStandartPreProcessorToUpper", Printer.class); //Printer
		printer.print("Prefix RenderStandart ProcessorToUpper");

		// PrinterWithPrefix RenderError ProcessorToLower
		printer = applicationContext.getBean("printerWithPrefixRendererErrorPreProcessorToLower", Printer.class); //Printer
		printer.print("Prefix RenderError ProcessorToLower");

		// PrinterWithDate RenderError ProcessorToUpper
		printer = applicationContext.getBean("printerWithDateTimeRendererErrorPreProcessorToUpper", Printer.class); //Printer
		printer.print("DateTime RenderError ProcessorToUpper");

		// Without beans
		PreProcessor preProcessor = new PrePcoessorToUpperImpl();
		Renderer renderer = new RendererErrImpl(preProcessor);
		PrinterWithPrefixImpl printer2 = new PrinterWithPrefixImpl(renderer);
		printer2.print("Without beans");
	}

}
