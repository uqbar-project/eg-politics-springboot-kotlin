package org.uqbar.politics.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.uqbar.politics.domain.Zona
import java.io.IOException

//class ZonaDetalleSerializer : StdSerializer<Zona>(Zona::class.java) {
//
//    @Throws(IOException::class)
//    override fun serialize(zona: Zona, generator: JsonGenerator, provider: SerializerProvider) {
//        with (generator) {
//            writeStartObject()
//            if (zona.id !== null) {
//                writeNumberField("id", zona.id!!)
//            }
//            writeStringField("descripcion", zona.descripcion)
//            val candidatosDTO = zona.candidates.map { CandidatoPlanoDTO(it) }
//            writeObjectField("candidates", candidatosDTO.toList())
//            writeEndObject()
//        }
//    }
//
//}
