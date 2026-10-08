package dev.chungjungsoo.gptmobile.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.chungjungsoo.gptmobile.data.model.ClientType
import dev.chungjungsoo.gptmobile.data.model.GeminiSafetySettings
import java.util.*

@Entity(tableName = "platform_v2")
data class PlatformV2(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("platform_id")
    val id: Int = 0,

    @ColumnInfo("uid")
    val uid: String = UUID.randomUUID().toString(),

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("compatible_type")
    val compatibleType: ClientType,

    @ColumnInfo(name = "enabled")
    val enabled: Boolean = false,

    @ColumnInfo(name = "api_url")
    val apiUrl: String,

    @ColumnInfo(name = "token")
    val token: String? = null,

    @ColumnInfo(name = "secret_ref")
    val secretRef: String? = null,

    @ColumnInfo(name = "model")
    val model: String,

    /**
     * Additional model identifiers the profile can switch between, excluding [model] itself.
     * Empty for the common single-model profile.
     */
    @ColumnInfo(name = "model_options", defaultValue = "'[]'")
    val modelOptions: List<String> = emptyList(),

    @ColumnInfo(name = "temperature")
    val temperature: Float? = null,

    @ColumnInfo(name = "top_p")
    val topP: Float? = null,

    @ColumnInfo(name = "top_k")
    val topK: Int? = null,

    /** Minimum probability for a token to be considered, in [0, 1]. 0 disables the filter. */
    @ColumnInfo(name = "min_p")
    val minP: Float? = null,

    /** Multiplicative penalty for already-emitted tokens, in [0.01, 5]. 1 disables the penalty. */
    @ColumnInfo(name = "repetition_penalty")
    val repetitionPenalty: Float? = null,

    /** Additive penalty on tokens that already appeared, in [-2, 2]. */
    @ColumnInfo(name = "presence_penalty")
    val presencePenalty: Float? = null,

    /** Additive penalty scaled by how often a token already appeared, in [-2, 2]. */
    @ColumnInfo(name = "frequency_penalty")
    val frequencyPenalty: Float? = null,

    @ColumnInfo(name = "max_tokens")
    val maxTokens: Int? = null,

    @ColumnInfo(name = "accelerator")
    val accelerator: String? = null,

    @ColumnInfo(name = "system_prompt")
    val systemPrompt: String? = null,

    @ColumnInfo(name = "stream")
    val stream: Boolean = true,

    @ColumnInfo(name = "reasoning")
    val reasoning: Boolean = false,

    @ColumnInfo(name = "timeout")
    val timeout: Int = 30,

    @ColumnInfo(name = "harassment_safety_threshold", defaultValue = "'BLOCK_NONE'")
    val harassmentSafetyThreshold: String = GeminiSafetySettings.BLOCK_NONE,

    @ColumnInfo(name = "hate_speech_safety_threshold", defaultValue = "'BLOCK_NONE'")
    val hateSpeechSafetyThreshold: String = GeminiSafetySettings.BLOCK_NONE,

    @ColumnInfo(name = "sexually_explicit_safety_threshold", defaultValue = "'BLOCK_NONE'")
    val sexuallyExplicitSafetyThreshold: String = GeminiSafetySettings.BLOCK_NONE,

    @ColumnInfo(name = "dangerous_content_safety_threshold", defaultValue = "'BLOCK_NONE'")
    val dangerousContentSafetyThreshold: String = GeminiSafetySettings.BLOCK_NONE,

    @ColumnInfo(name = "resumable_replies", defaultValue = "0")
    val resumableReplies: Boolean = false,

    /**
     * Raw-output mode: when true this profile sends the conversation verbatim to the
     * provider. Every provider-injected instruction is dropped (no system/developer
     * message, no assistant instructions) and no agent tools are advertised, so the
     * model answers exactly as its own API would.
     */
    @ColumnInfo(name = "ephemeral_mode", defaultValue = "0")
    val ephemeralMode: Boolean = false
)
