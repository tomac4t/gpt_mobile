package dev.chungjungsoo.gptmobile.data.dto.openai.request

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class ChatCompletionRequest(
    @SerialName("model")
    val model: String,

    @SerialName("messages")
    val messages: List<ChatMessage>,

    @SerialName("stream")
    val stream: Boolean = true,

    @SerialName("stream_options")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val streamOptions: StreamOptions? = null,

    @SerialName("temperature")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val temperature: Float? = null,

    @SerialName("top_p")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val topP: Float? = null,

    @SerialName("top_k")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val topK: Int? = null,

    @SerialName("min_p")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val minP: Float? = null,

    @SerialName("repetition_penalty")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val repetitionPenalty: Float? = null,

    @SerialName("chat_template_kwargs")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val chatTemplateKwargs: JsonObject? = null,

    @SerialName("max_tokens")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val maxTokens: Int? = null,

    @SerialName("max_completion_tokens")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val maxCompletionTokens: Int? = null,

    @SerialName("reasoning_effort")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val reasoningEffort: String? = null,

    @SerialName("presence_penalty")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val presencePenalty: Float? = null,

    @SerialName("frequency_penalty")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val frequencyPenalty: Float? = null,

    @SerialName("stop")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val stop: List<String>? = null,

    @SerialName("tools")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val tools: List<ChatFunctionTool>? = null
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class StreamOptions(
    @SerialName("include_usage")
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    val includeUsage: Boolean = true,

    /** Opt-in: only present when the endpoint accepts per-chunk usage counters. */
    @SerialName("continuous_usage_stats")
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val continuousUsageStats: Boolean? = null
)

@Serializable
data class ChatFunctionTool(
    @SerialName("type")
    val type: String = "function",

    @SerialName("function")
    val function: ChatFunctionDefinition
) {
    constructor(name: String, description: String, parameters: JsonObject) : this(
        function = ChatFunctionDefinition(name, description, parameters)
    )
}

@Serializable
data class ChatFunctionDefinition(
    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String,

    @SerialName("parameters")
    val parameters: JsonObject
)
