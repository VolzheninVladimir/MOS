import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class ProducerConsumerWithCoroutines {

    private val channel = Channel<Int>(5)

    suspend fun start() {
        coroutineScope {
            val producers = List(3) { i ->
                launch {
                    producer(i + 1)
                }
            }

            val consumers = List(2) { i ->
                launch {
                    consumer(i + 1)
                }
            }

            producers.joinAll()
            channel.close()
            consumers.joinAll()
        }
    }

    private suspend fun producer(id: Int) {
        println("Producer $id запущен.")
        for (i in 1..10) {
            println("Producer $id → отправляет $i")
            channel.send(i)
            delay(500)
        }
        println("Producer $id завершил создание чисел.")
    }

    private suspend fun consumer(id: Int) {
        println("Consumer $id запущен.")
        for (item in channel) {
            println("Consumer $id ← получил $item")
            delay(1000)
        }
        println("Consumer $id завершил обработку чисел.")
    }
}

fun main() = runBlocking {
    ProducerConsumerWithCoroutines().start()
}