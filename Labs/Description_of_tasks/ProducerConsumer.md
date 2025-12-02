# Producer–Consumer на Kotlin (JVM)

Минимальный пример реализации классической задачи Producer–Consumer с использованием `Thread`, `synchronized`, `wait()` и `notify()`

---

## Архитектура

- Общая очередь: `MutableList<Int>`
- Синхронизация: `Object`-lock
- Потоки:
  - `Producer`: добавляет числа в очередь
  - `Consumer`: извлекает и печатает

---

## Ограничения

- Если очередь **пустая** → `Consumer` ждёт
- Если очередь **переполнена (>5 элементов)** → `Producer` ждёт

---

## Основной код

```kotlin
# Main.kt

fun main() {
    val queue = mutableListOf<Int>()
    val lock = Object()

    val producer = Thread {
        var value = 1
        while (true) {
            synchronized(lock) {
                while (queue.size >= 5) {
                    lock.wait()
                }
                println("Producer: $value")
                queue.add(value++)
                lock.notify()
            }
            Thread.sleep(300)
        }
    }

    val consumer = Thread {
        while (true) {
            synchronized(lock) {
                while (queue.isEmpty()) {
                    lock.wait()
                }
                val item = queue.removeAt(0)
                println("Consumer: $item")
                lock.notify()
            }
            Thread.sleep(500)
        }
    }

    producer.start()
    consumer.start()
}
```
