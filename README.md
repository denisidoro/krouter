# Krouter
A lightweight Android activity router.

![crossroads](https://cloud.githubusercontent.com/assets/3226564/18612759/e81f369c-7d38-11e6-9a3d-b9da6fdc6944.png)

### Basic usage

```kotlin
// anywhere in the app, preferably on application creation
val krouter = Krouter.from(context, hashMapOf(
    "user/:id/likes" to UserLikesActivity::class.java
))

// anywhere that can access the val above
krouter.start("user/42/likes") // this will start UserLikesActivity

// inside UserLikesActivity, possibly inside onCreate()
val id: Int = intent.getIntExtra("id", 0) // 42
```

### Adding more extras

In order to add more extras to the intent, one can do the following:
```kotlin
krouter.getRouter("user/42/likes")!!
    .withIntent { it.putExtra("name", "John") }
    .start()
```

### Starting for result

In order to set a request code:
```kotlin
krouter.getRouter("user/42/likes")!!
    .startForResult(activity, 123) // 123 is the request code
```

### Advanced routing configuration

It is possible to instantiate Krouter establishing a regular expression that the parameter must specify:
```kotlin
val krouter = Krouter(context, hashMapOf(
    Route("user/:id/likes", hashMapOf(
        "id" to Schema("^[0-9]{2}$")
    )) to UserLikesActivity::class.java
)
```

There are default implementations for integer, float, double, long, char:
```kotlin
// the constants must be statically imported from Schema.Type
Route("user/:id/likes", hashMapOf(
    "id" to Schema(INT)
) to UserLikesActivity::class.java)

Route("my/balance/:value", hashMapOf(
    "value" to Schema(FLOAT)
) to BalanceActivity::class.java)
```

If no schema is defined, Krouter will try to infer the type accordingly.
If no schema is suitable, the param will be coerced to a String.

### Installation

Add the dependency:
```gradle
dependencies {
    compile 'com.github.denisidoro.github:krouter:0.0.2'
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
- [x] `startActivityForResult` implementation
- [x] Add helper constructors agnostic to implementation details