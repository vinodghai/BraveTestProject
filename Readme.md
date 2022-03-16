Dependencies:
Retrofit - to consume REST API and convert JSON to Kotlin object
NDK - Requires NDK and CMake to run native code
Minimum API 21
package structure
com.example.bravetestproject
    btsassetslist
        //contains sub-packages relevant to this feature
    // contains app specific files that can be used in any feature

The project fulfills all the given requirements.

Important:
It uses CoinGecko API to retrieve cryptocurrencies.
The project uses Kotlin, is built on MVVM architecture, and complies with SOLID principles.
The response from the API is saved into a text file(BraveLogs.txt) located in app's internal storage.
BraveApp is the Application class
MainActivity is the entry activity
main/cpp contains the native code
main/java contains the Android code
Vector drawables are used as cryptocurrencies' icons
LiveData is used to update UI on API response
LifeCycleAware component from Jetpack is used to implement the timer
Util class contains the globally helper functions
Constants file contains global constants
RecyclerView is used to implement the list of BTC assets