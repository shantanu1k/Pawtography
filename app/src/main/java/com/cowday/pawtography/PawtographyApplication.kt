package com.cowday.pawtography

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.request.CachePolicy
import com.cowday.pawtography.database.DogDatabase

class PawtographyApplication: Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir)
                    .build()
            }
            .build()
    }

    val dogDatabase: DogDatabase by lazy {
        DogDatabase.getDatabase(this)
    }
}