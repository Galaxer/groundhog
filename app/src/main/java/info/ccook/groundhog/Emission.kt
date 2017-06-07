package info.ccook.groundhog

data class Emission<out T>(val status: Status, val data: T? = null, val message: String = "") {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T) = Emission(Status.SUCCESS, data)
        fun <T> error(message: String): Emission<T> = Emission(Status.ERROR, message = message)
        fun <T> loading(): Emission<T> = Emission(Status.LOADING)
    }
}