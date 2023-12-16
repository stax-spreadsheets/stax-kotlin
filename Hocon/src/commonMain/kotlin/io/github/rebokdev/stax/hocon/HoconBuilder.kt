package io.github.rebokdev.stax.hocon

class HoconBuilder {
    val objects: MutableList<HoconObject> = mutableListOf()

    fun sectionObject(
        name: String,
        body: HoconBuilder.() -> Unit = {}
    ) {
        objects.add(
            HoconObject.SectionObject(
                name = name,
                contains = HoconBuilder().applyStructure(body)
            )
        )
    }

    fun value(key: String, value: Any) {
        objects.add(
            HoconObject.Value(
                key = key,
                value = hoconValue(value)
            )
        )
    }

    fun newLine() {
        objects.add(
            HoconObject.NewLine
        )
    }

    fun applyStructure(
        body: HoconBuilder.() -> Unit
    ): List<HoconObject> {
        body()
        return objects
    }

    fun generate(
        tabIndent: Int = 0
    ): String {
        var result = ""
        var indentResult = ""
        repeat(tabIndent) { indentResult += "\t" }
        objects.forEach {
            when (it) {
                is HoconObject.SectionObject -> {
                    val generatedHocon = HoconBuilder().let { builder ->
                        builder.applyStructure { this.objects.addAll(it.contains) }
                        builder.generate(tabIndent + 1)
                    }

                    result += if (it.contains.isEmpty()) {
                        "${indentResult}${it.name} { }\n"
                    } else {
                        "${indentResult}${it.name} {\n${
                            generatedHocon
                        }${indentResult}}\n"
                    }
                }

                is HoconObject.Value -> {
                    result += "${indentResult}${it.key} = ${it.value}\n"
                }

                is HoconObject.NewLine -> {
                    result += "\n"
                }
            }
        }

        return result
    }
}