package io.alkemy.internals

inline fun <reified T> sysProp(name: String, default: T): T {
    val sysProp = System.getProperty(name) ?: return default
    return when (T::class) {
        String::class -> sysProp as T
        Boolean::class -> sysProp.toBoolean() as T
        Int::class -> sysProp.toInt() as T
        Long::class -> sysProp.toLong() as T
        else -> throw IllegalArgumentException("No convertor for class ${T::class}")
    }
}

inline fun <reified T : Enum<T>> enumSysProp(name: String, default: T): T {
    val sysProp = System.getProperty(name) ?: return default
    return enumValueOf<T>(sysProp.trim().uppercase())
}
