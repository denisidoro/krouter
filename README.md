# Krouter
A lightweight Android activity router.

### Basic usage

```kotlin
// anywhere in the app, preferably on application creation
val krouter = Krouter(context, hashMapOf(
    Route("user/:id/likes"), UserLikesActivity::class.java,
    Route("settings"), SettingsActivity::class.java
))

// anywhere that can access the val above
krouter.start("user/42/likes") // this will start UserLikesActivity

// inside UserLikesActivity, possibly inside onCreate()
val id: Int = intent.getIntExtra("id", 0) // 42
```

### Configuration

It is possible to establish a regular expression that the parameter must specify:
```kotlin
Route("user/:id/likes", hashmapOf(
    Pair("id", Schema("^[0-9]{2}$"))
)), UserLikesActivity::class.java
```

Currently, there are default implementations for integer and floating point numbers:
```kotlin
// the constants must be statically imported from Schema.Type
Route("user/:id/likes", hashmapOf(
    Pair("id", Schema(INT))
), UserLikesActivity::class.java)

Route("my/balance/:value", hashmapOf(
    Pair("value", Schema(FLOAT))
), BalanceActivity::class.java)
```

If no schema is defined, Krouter will try to infer the type accordingly.

### Installation

Add the dependency:
```gradle
dependencies {
    compile 'com.github.denisidoro.github:krouter:0.0.1'
}
```

And add the repository:

```groovy
allprojects {
    repositories {
        jcenter()
        maven { url 'https://dl.bintray.com/denisidoro/maven' }
    }
}
```

### To do
- [ ] Add package to jcenter so that it won't be necessary to add the repository manually
- [ ] `startActivityForResult` implementation
- [ ] Add helper constructors agnostic to entity definitions
- [ ] Add schemas for Long, Double, Serializable and Parcelable
