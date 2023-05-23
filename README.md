[![Maven Central](https://img.shields.io/maven-central/v/io.github.oleksandrbalan/lazytable.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.oleksandrbalan/lazytable)

<img align="right" width="128" src="https://github.com/oleksandrbalan/lazytable/assets/20944869/829052fd-4fa8-4599-8980-9dcd7189e1c5">

# Lazy Table

Lazy table library for Jetpack Compose.

Lazy table allows to display columns and rows of data on the two directional plane. It is build on the [MinaBox](https://github.com/oleksandrbalan/minabox) (which is build on `LazyLayout`) and provides methods to register item(s) and handles scrolling on the plane.

## Usage

### Get a dependency

**Step 1.** Add the MavenCentral repository to your build file.
Add it in your root `build.gradle` at the end of repositories:
```
allprojects {
    repositories {
        ...
        mavenCentral()
    }
}
```

Or in `settings.gradle`:
```
pluginManagement {
    repositories {
        ...
        mavenCentral()
    }
}
```

**Step 2.** Add the dependency.
Check latest version on the [releases page](https://github.com/oleksandrbalan/lazytable/releases).
```
dependencies {
    implementation 'io.github.oleksandrbalan:lazytable:$version'
}
```

### Use in Composable

The core element of the `LazyTable` layout is a `content` lambda, where items are registered in the similar manner as in `LazyColumn` or `LazyRow`. The main difference is that each item must provide its position and size in the `layoutInfo` lambda. Each item defines its position by specifying the `column` and `row` and size by specifying how much columns or rows it occupies (by default is set to 1).

The size of the cells are defined via `dimensions` parameter. There are multiple `lazyTableDimensions` methods which creates the dimensions for all or each column / row.

Lazy table also allows to specify how many columns / rows should be pinned and thus be visible when user navigates in the table. Check the `pinConfiguration` parameter.

It is also possible to observe on the scroll state and change it programmatically using an instance of the `LazyTableState`.

```
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

See Demo application and [examples](demo/src/main/kotlin/eu/wewox/lazytable/screens) for more usage examples.

## Examples

Simple table with items.

https://github.com/oleksandrbalan/lazytable/assets/20944869/cb7831a5-fe3d-47b5-943b-8ffe442e00b0

Advanced example with pinned columns and rows. 

https://github.com/oleksandrbalan/lazytable/assets/20944869/4b25177c-7900-4934-a436-2771e3f49169

If you need further customization options, check [MinaBox](https://github.com/oleksandrbalan/minabox) library.



