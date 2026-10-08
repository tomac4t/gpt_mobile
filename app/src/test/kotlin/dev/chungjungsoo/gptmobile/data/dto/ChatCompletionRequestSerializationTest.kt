package dev.chungjungsoo.gptmobile.data.dto

import dev.chungjungsoo.gptmobile.data.dto.openai.common.Role
import dev.chungjungsoo.gptmobile.data.dto.openai.common.TextContent
import dev.chungjungsoo.gptmobile.data.dto.openai.request.ChatCompletionRequest
import dev.chungjungsoo.gptmobile.data.dto.openai.request.ChatMessage
import dev.chungjungsoo.gptmobile.data.dto.openai.request.ReasoningConfig
import dev.chungjungsoo.gptmobile.data.dto.openai.request.ResponsesRequest
import dev.chungjungsoo.gptmobile.data.dto.openai.request.StreamOptions
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ChatCompletionRequestSerializationTest {
    private val json = Json {
        encodeDefaults = false
        explicitNulls = false
    }

    @Test
    fun `unset sampling knobs stay out of the payload`() {
        val payload = json.encodeToString(
            ChatCompletionRequest(
                model = "deepseek-ai/DeepSeek-V4.1-Flash",
                messages = listOf(ChatMessage(Role.USER, listOf(TextContent("你是谁？"))))
            )
        )

        assertTrue(payload.contains("\"role\":\"user\""))
        listOf("temperature", "top_p", "min_p", "top_k", "repetition_penalty", "presence_penalty", "frequency_penalty", "tools")
            .forEach { field -> assertFalse("$field should be omitted", payload.contains("\"$field\"")) }
    }

    @Test
    fun `advanced sampling knobs serialize with their api names`() {
        val payload = json.encodeToString(
            ChatCompletionRequest(
                model = "m",
                messages = emptyList(),
                temperature = 0.7f,
                topP = 0.9f,
                minP = 0.05f,
                topK = 40,
                repetitionPenalty = 1.1f,
                presencePenalty = 0.5f,
                frequencyPenalty = -0.25f
            )
        )

        assertTrue(payload.contains("\"min_p\":0.05"))
        assertTrue(payload.contains("\"top_k\":40"))
        assertTrue(payload.contains("\"repetition_penalty\":1.1"))
        assertTrue(payload.contains("\"presence_penalty\":0.5"))
        assertTrue(payload.contains("\"frequency_penalty\":-0.25"))
    }

    @Test
    fun `stream options carry usage counters`() {
        val payload = json.encodeToString(
            ChatCompletionRequest(
                model = "m",
                messages = emptyList(),
                streamOptions = StreamOptions(continuousUsageStats = true)
            )
        )

        assertTrue(payload.contains("\"stream_options\""))
        assertTrue(payload.contains("\"include_usage\":true"))
        assertTrue(payload.contains("\"continuous_usage_stats\":true"))
    }

    @Test
    fun `stream options leave continuous usage stats out unless asked`() {
        val payload = json.encodeToString(
            ChatCompletionRequest(
                model = "m",
                messages = emptyList(),
                streamOptions = StreamOptions()
            )
        )

        assertTrue(payload.contains("\"include_usage\":true"))
        assertFalse(payload.contains("continuous_usage_stats"))
    }

    @Test
    fun `raw thinking override rides the chat template kwargs`() {
        val payload = json.encodeToString(
            ChatCompletionRequest(
                model = "m",
                messages = emptyList(),
                chatTemplateKwargs = buildJsonObject { put("thinking", false) }
            )
        )

        assertTrue(payload.contains("\"chat_template_kwargs\":{\"thinking\":false}"))
    }

    @Test
    fun `responses request can disable reasoning explicitly`() {
        val payload = json.encodeToString(
            ResponsesRequest(
                model = "deepseek-ai/DeepSeek-V4.1-Flash",
                input = emptyList(),
                reasoning = ReasoningConfig(enabled = false)
            )
        )

        assertTrue(payload.contains("\"reasoning\":{\"enabled\":false}"))
    }

    @Test
    fun `responses request keeps the reasoning flag off when unset`() {
        val payload = json.encodeToString(
            ResponsesRequest(model = "m", input = emptyList())
        )

        assertFalse(payload.contains("reasoning"))
    }

    @Test
    fun `custom system roles can be addressed as developer`() {
        assertEquals("\"developer\"", json.encodeToString(Role.serializer(), Role.DEVELOPER))
    }
}
