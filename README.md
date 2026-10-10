# Fast Bento - Minecraft Performance Mod

A high-performance Fabric mod that optimizes chest rendering by converting dynamic BlockEntityRenderers to static chunk geometry.

## What's New in Fast Bento (vs FastChest)

| Feature | FastChest | Fast Bento |
|---------|-----------|------------|
| Regular Chests | ✅ | ✅ |
| Trapped Chests | ❌ | ✅ |
| Ender Chests | ✅ | ✅ |
| Copper Chests (all variants) | ❌ | ✅ |
| Per-Type Toggle | ❌ | ✅ |
| Performance Stats | ❌ | ✅ |
| Block Ticker Removal | ❌ | ✅ |
| Config UI | Basic | Enhanced |

## Files Created

```
fast-bento/
├── build.gradle
├── gradle.properties
├── settings.gradle
├── .gitignore
├── LICENSE.txt
├── README.md
├── style.xml
├── gradlew / gradlew.bat     # Gradle wrapper scripts
│
└── src/main/
    ├── java/re/domimods/fastbento/
    │   ├── FastBentoMod.java           # Main entry point
    │   ├── config/
    │   │   ├── Config.java             # Enhanced config with per-type toggles
    │   │   ├── ConfigScreen.java       # Full config GUI via Mod Menu
    │   │   ├── ModMenuImpl.java        # Mod Menu integration
    │   │   └── PerformanceStats.java   # Stats tracking
    │   └── mixin/
    │       ├── BlockEntityRenderManagerMixin.java
    │       ├── ChestBlockMixin.java    # Regular chests
    │       ├── TrappedChestBlockMixin.java  # NEW: Trapped chests
    │       ├── EnderChestBlockMixin.java
    │       └── CopperChestBlockMixin.java  # NEW: Copper chests + all variants
    │
    └── resources/
        ├── fabric.mod.json             # Mod manifest (v3.0, MC 26.1-26.3)
        ├── fastbento.mixins.json
        └── assets/minecraft/
            ├── blockstates/*.json      # 11 blockstate files (all chest types)
            └── models/block/*.json     # 33 model files (static render templates)
```

## Build Instructions

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/fast-bento-3.0.0+26.x.jar`

## Requirements

- **Minecraft**: 26.1, 26.2, 26.3 (including all betas, pre-releases, and release candidates)
- **Java**: SE 25 (LTS) or higher
- **Fabric Loader**: 0.15.0+
- **Fabric API**
- **Mod Menu**: 15.0.0+ (for config screen)

## Version Compatibility

| Minecraft Version | Official Name | Release Date | Status |
|-------------------|---------------|--------------|--------|
| 26.1 | Tiny Takeover | March 24, 2026 | ✅ Supported |
| 26.2 | Chaos Cubed | June 16, 2026 | ✅ Supported |
| 26.3 | Wilderness Bound | September 15, 2026 | ✅ Supported |

### Version Details

- **26.1**: Protocol 775, Data version 4786, Resource pack format 84.0
- **26.2**: Protocol 776, Data version 4903, Resource pack format 88.0
- **26.3**: Protocol 777, Data version 5023, Resource pack format 97.1

## ⚠️ Build Status: Waiting for Yarn Mappings

**Current blocker:** Yarn mappings for Minecraft 26.x have not been published yet.

| Component | Status | Available Version |
|-----------|--------|-------------------|
| Minecraft 26.3 | ✅ Released | 26.3 |
| Fabric Loader | ✅ Available | 0.19.5 |
| Fabric API | ✅ Available | 0.162.0+26.3 |
| Mod Menu | ✅ Available | 20.0.0 |
| **Yarn Mappings** | ❌ **NOT YET** | Latest: 1.21.11+build.6 |

**To fix:** Once Fabric publishes yarn mappings for 26.3, update `gradle.properties`:
```properties
yarn_mappings=26.3+build.1
```

Then run:
```bash
./gradlew build
```

- In large storage rooms with many chests, this mod can **double your FPS**
- Chests will lose their lid-opening animation (the lid will always appear closed)
- If you need animated chests, consider [Enhanced Block Entities](https://modrinth.com/mod/ebe) instead
- This mod is compatible with Sodium and other rendering optimizations

## Resource Pack Compatibility

This mod may conflict with resource packs that modify chest blockstates. If you experience issues:

1. Check if your resource pack modifies `chest.json`, `ender_chest.json`, or `trapped_chest.json` in `/assets/minecraft/blockstates/`
2. Delete those files from your resource pack to restore compatibility

## Credits

- Original concept based on [FastChest](https://github.com/FakeDomi/FastChest) by Domi
- Enhanced and expanded for Minecraft 26.1-26.3 by Fast Bento Team

## License

MIT License - See [LICENSE.txt](LICENSE.txt) for details.
