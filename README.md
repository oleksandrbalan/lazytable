[![Maven Central](https://img.shields.io/maven-central/v/io.github.oleksandrbalan/lazytable.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.oleksandrbalan/lazytable)

<img align="right" width="128" src="https://github.com/oleksandrbalan/lazytable/assets/20944869/6f5eb021-78a7-4efe-84eb-5a79623d3185">

# Lazy Table

Lazy table library for Compose UI.

Lazy table allows to display columns and rows of data on the two directional plane. It is build on the [MinaBox](https://github.com/oleksandrbalan/minabox) (which is build on `LazyLayout`) and provides methods to register item(s) and handles scrolling on the plane.

## Multiplatform

Library supports [Android](https://developer.android.com/jetpack/compose), [iOS](https://github.com/JetBrains/compose-multiplatform-ios-android-template/#readme) and [Desktop](https://github.com/JetBrains/compose-multiplatform-desktop-template/#readme) (Windows, MacOS, Linux) targets.

## Usage

### Get a dependency

**Step 1.** Add the MavenCentral repository to your build file.
Add it in your root `build.gradle.kts` at the end of repositories:
```kotlin
allprojects {
    repositories {
        ...
        mavenCentral()
    }
}
```

Or in `settings.gradle.kts`:
```kotlin
pluginManagement {
    repositories {
        ...
        mavenCentral()
    }
}
```

**Step 2.** Add the dependency.
Check latest version on the [releases page](https://github.com/oleksandrbalan/lazytable/releases).
```kotlin
dependencies {
    implementation("io.github.oleksandrbalan:lazytable:$version")
}
```

### Use in Composable

The core element of the `LazyTable` layout is a `content` lambda, where items are registered in the similar manner as in `LazyColumn` or `LazyRow`. The main difference is that each item must provide its position and size in the `layoutInfo` lambda. Each item defines its position by specifying the `column` and `row` and size by specifying how much columns or rows it occupies (by default is set to 1).

The size of the cells are defined via `dimensions` parameter. There are multiple `lazyTableDimensions` methods which creates the dimensions for all or each column / row.

Lazy table also allows to specify how many columns / rows should be pinned and thus be visible when user navigates in the table. Check the `pinConfiguration` parameter.

It is also possible to observe on the scroll state and change it programmatically using an instance of the `LazyTableState`.

```kotlin
val columns = 10
val rows = 10
LazyTable(
    dimensions = lazyTableDimensions(48.dp, 32.dp)
) {
    items(
        count = columns * rows,
        layoutInfo = {
            LazyTableItem(
                column = it % columns,
                row = it / columns,
            )
        }
    ) { index ->
        Text(text = "#$index")
    }
}
```

See Demo application and [examples](demo/src/commonMain/kotlin/eu/wewox/lazytable/screens) for more usage examples.

## Examples

Simple table with items.

https://github.com/oleksandrbalan/lazytable/assets/20944869/0277522f-b21a-4c93-b71f-9a1af2d079cc

Advanced example with pinned columns and rows. 

https://github.com/oleksandrbalan/lazytable/assets/20944869/1a1c6bee-25cd-4cb5-b619-988fbe30554c

If you need further customization options, check [MinaBox](https://github.com/oleksandrbalan/minabox) library.
