package dev.chungjungsoo.gptmobile.data.repository

import dev.chungjungsoo.gptmobile.data.database.dao.PlatformV2Dao
import dev.chungjungsoo.gptmobile.data.database.entity.PlatformV2

/**
 * In-memory platform profile store shared by the repository tests.
 *
 * The list is positional, so a test can also assert on the rows the repository wrote. Set
 * [failEdits] to make an update blow up and exercise the failure paths.
 */
internal class FakePlatformV2Dao(
    val platforms: MutableList<PlatformV2> = mutableListOf()
) : PlatformV2Dao {
    var failEdits = false

    override suspend fun getPlatforms(): List<PlatformV2> = platforms.toList()

    override suspend fun getPlatform(id: Int): PlatformV2? = platforms.firstOrNull { it.id == id }

    override suspend fun getPlatformByUid(uid: String): PlatformV2? = platforms.firstOrNull { it.uid == uid }

    override suspend fun addPlatform(platform: PlatformV2): Long {
        val persisted = if (platform.id == 0) platform.copy(id = (platforms.maxOfOrNull { it.id } ?: 0) + 1) else platform
        platforms += persisted
        return persisted.id.toLong()
    }

    override suspend fun editPlatform(platform: PlatformV2) {
        check(!failEdits) { "Database update failed." }
        val index = platforms.indexOfFirst { it.id == platform.id }
        if (index >= 0) platforms[index] = platform
    }

    override suspend fun deleteBindingsByProfileUid(profileUid: String) = Unit

    override suspend fun deletePlatformRow(platform: PlatformV2) {
        platforms.removeAll { it.id == platform.id }
    }
}
