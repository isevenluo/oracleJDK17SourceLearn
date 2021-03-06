/*
 * Copyright (c) 2021, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package jdk.incubator.foreign;

import jdk.internal.access.JavaLangAccess;
import jdk.internal.access.SharedSecrets;
import jdk.internal.reflect.CallerSensitive;
import jdk.internal.reflect.Reflection;

import java.util.Objects;
import java.util.Optional;

/**
 * A symbol lookup. Exposes a lookup operation for searching symbol addresses by name, see {@link SymbolLookup#lookup(String)}.
 * A symbol lookup can be used to lookup a symbol in a loaded library. Clients can obtain a {@linkplain #loaderLookup() loader lookup},
 * which can be used to search symbols in libraries loaded by the current classloader (e.g. using {@link System#load(String)},
 * or {@link System#loadLibrary(String)}).
 * Alternatively, clients can obtain a {@linkplain CLinker#systemLookup() platform-dependent lookup}, to search symbols
 * in the standard C library.
 * <p> Unless otherwise specified, passing a {@code null} argument, or an array argument containing one or more {@code null}
 * elements to a method in this class causes a {@link NullPointerException NullPointerException} to be thrown. </p>
 */
@FunctionalInterface
public interface SymbolLookup {

    /**
     * Looks up a symbol with given name in this lookup.
     *
     * @param name the symbol name.
     * @return the memory address associated with the symbol (if any).
     */
    Optional<MemoryAddress> lookup(String name);

    /**
     * Obtains a symbol lookup suitable to find symbols in native libraries associated with the caller's classloader
     * (that is, libraries loaded using {@link System#loadLibrary} or {@link System#load}).
     * <p>
     * This method is <a href="package-summary.html#restricted"><em>restricted</em></a>.
     * Restricted methods are unsafe, and, if used incorrectly, their use might crash
     * the JVM or, worse, silently result in memory corruption. Thus, clients should refrain from depending on
     * restricted methods, and use safe and supported functionalities, where possible.
     *
     * @return a symbol lookup suitable to find symbols in libraries loaded by the caller's classloader.
     * @throws IllegalCallerException if access to this method occurs from a module {@code M} and the command line option
     * {@code --enable-native-access} is either absent, or does not mention the module name {@code M}, or
     * {@code ALL-UNNAMED} in case {@code M} is an unnamed module.
     */
    @CallerSensitive
    static SymbolLookup loaderLookup() {
        Class<?> caller = Reflection.getCallerClass();
        Reflection.ensureNativeAccess(caller);
        ClassLoader loader = Objects.requireNonNull(caller.getClassLoader());
        return name -> {
            Objects.requireNonNull(name);
            JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
            MemoryAddress addr = MemoryAddress.ofLong(javaLangAccess.findNative(loader, name));
            return addr == MemoryAddress.NULL? Optional.empty() : Optional.of(addr);
        };
    }
}
