package sj.jsr269;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)//支持java版本
@SupportedAnnotationTypes("sj.jsr269.MyGetter")//支持哪些注解
public class MyGetterProcess extends AbstractProcessor {

    //类初始化时会先调用init方法
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        System.err.println("MyGetterProcess init");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"支持idea输出");
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }
}
