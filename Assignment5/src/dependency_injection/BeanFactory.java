package dependency_injection;

import java.io.File;
import java.io.FileNotFoundException;

public interface BeanFactory {
    void loadInjectProperties(File file) throws FileNotFoundException;

    void loadValueProperties(File file);

    <T> T createInstance(Class<T> clazz);
}
