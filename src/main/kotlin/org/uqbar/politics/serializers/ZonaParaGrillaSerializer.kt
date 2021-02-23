package org.uqbar.politics.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.uqbar.politics.domain.Zona
import java.io.IOException

class ZonaParaGrillaSerializer : StdSerializer<Zona>(Zona::class.java) {

    @Throws(IOException::class)
    override fun serialize(zona: Zona, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()
        if (zona.id !== null) {
            gen.writeNumberField("id", zona.id!!)
        }
        gen.writeStringField("descripcion", zona.descripcion)
        val candidatosDTO = zona.candidates.map { CandidatoPlanoDTO(it) }
        gen.writeObjectField("candidatos", candidatosDTO.toList())
        gen.writeEndObject()
    }

}
