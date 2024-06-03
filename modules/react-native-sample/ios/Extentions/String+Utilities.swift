
func print(_ items: Any..., separator: String = " ", terminator: String = "\n") {
#if DEBUG
    Swift.print(items, separator: separator, terminator: terminator)
#endif
}

func debugPrint(_ items: Any..., separator: String = " ", terminator: String = "\n") {
#if DEBUG
    Swift.debugPrint(items, separator: separator, terminator: terminator)
#endif
}

@discardableResult
func dump<T>(_ value: T, name: String? = nil, indent: Int = 0, maxDepth: Int = .max, maxItems: Int = .max) -> T
{
#if DEBUG
    Swift.dump(value, name: name, indent: indent, maxDepth: maxDepth, maxItems: maxItems)
#endif
    return value
}
