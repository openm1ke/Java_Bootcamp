package org.example.processor;

import org.example.annotations.HtmlForm;
import org.example.annotations.HtmlInput;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("org.example.annotations.HtmlForm")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append(String.format("<form action=\"%s\" method=\"%s\">%n", htmlForm.action(), htmlForm.method()));

            for (Element enclosedElement : element.getEnclosedElements()) {
                HtmlInput htmlInput = enclosedElement.getAnnotation(HtmlInput.class);
                if (htmlInput != null) {
                    htmlContent.append(String.format("\t<input type=\"%s\" name=\"%s\" placeholder=\"%s\">%n",
                            htmlInput.type(), htmlInput.name(), htmlInput.placeholder()));
                }
            }

            htmlContent.append("\t<input type=\"submit\" value=\"Submit\"></form>");

            try {
                // Создаем файл в директории ресурсов
                FileObject file = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", htmlForm.fileName());
                try (Writer writer = file.openWriter()) {
                    writer.write(htmlContent.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
