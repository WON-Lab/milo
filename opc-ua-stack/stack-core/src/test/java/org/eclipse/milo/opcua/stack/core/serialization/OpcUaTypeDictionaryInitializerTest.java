package org.eclipse.milo.opcua.stack.core.serialization;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

public class OpcUaTypeDictionaryInitializerTest {

    @Test
    public void testInitialize() throws Exception {
        OpcUaTypeDictionaryInitializer.initialize();

        ClassLoader classLoader = getClass().getClassLoader();
        ClassPath classPath = ClassPath.from(classLoader);

        ImmutableSet<ClassPath.ClassInfo> structures =
            classPath.getTopLevelClasses("org.eclipse.milo.opcua.stack.core.types.structured");

        ImmutableSet<ClassPath.ClassInfo> enumerations =
            classPath.getTopLevelClasses("org.eclipse.milo.opcua.stack.core.types.enumerated");

        assertNotEquals(structures.size(), 0);
        assertNotEquals(enumerations.size(), 0);

        for (ClassPath.ClassInfo classInfo : Sets.union(structures, enumerations)) {
            Class<?> clazz = classInfo.load();

            assertNotNull(OpcUaTypeDictionary.getInstance().getBinaryCodec(clazz.getSimpleName()));
            assertNotNull(OpcUaTypeDictionary.getInstance().getXmlCodec(clazz.getSimpleName()));
        }
    }

}
