package io.github.rebokdev.stax.hocon

fun hocon(body: HoconBuilder.() -> Unit): String = HoconBuilder().let {
    it.applyStructure(body)
    it.generate()
}