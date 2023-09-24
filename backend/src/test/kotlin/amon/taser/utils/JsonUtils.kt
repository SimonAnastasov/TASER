import com.fasterxml.jackson.databind.ObjectMapper

object JsonUtils {
    private val objectMapper = ObjectMapper()

    fun <T> toJson(obj: T): String {
        return objectMapper.writeValueAsString(obj)
    }

    fun <T> fromJson(json: String, valueType: Class<T>): T {
        return objectMapper.readValue(json, valueType)
    }
}