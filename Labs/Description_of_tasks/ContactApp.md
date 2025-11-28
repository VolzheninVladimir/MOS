# ContactApp

Простое Android‑приложение для отображения списка контактов с помощью **RecyclerView**.

---

## Возможности
- Отображение списка контактов (имя + номер телефона).
- Использование `RecyclerView` с `LinearLayoutManager`.
- Переиспользование элементов списка через `ViewHolder`.
- Минимальная архитектура: `MainActivity`, `Adapter`, `ViewHolder`, `Data class`.

---

## Архитектура проекта

```
ContactsApp/
├── MainActivity.kt          # Точка входа, инициализация RecyclerView
├── Contact.kt               # Data-класс для контакта
├── ContactViewHolder.kt     # ViewHolder для элемента списка
├── ContactAdapter.kt        # Adapter для управления списком
├── res/
│   ├── layout/
│   │   ├── activity_main.xml  # Экран с RecyclerView
│   │   └── item_contact.xml   # Разметка одного контакта
│   └── values/
│       └── themes.xml         # Тема приложения
└── AndroidManifest.xml      # Конфигурация приложения
```

---


## Теория
В проекте используется `RecyclerView`, который состоит из:
- **ViewHolder** — описывает один элемент списка.
- **Adapter** — управляет данными и связывает их с ViewHolder.
- **LayoutManager** — отвечает за расположение элементов (в нашем случае `LinearLayoutManager`).

---

## Пример кода

```kotlin
// MainActivity.kt
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val contacts = listOf(
            Contact("Ivan Ivanov", "+7 999 111-22-33"),
            Contact("Petr Petrov", "+7 999 222-33-44"),
            Contact("Anna Smirnova", "+7 999 444-55-66")
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ContactAdapter(contacts)
    }
}
```

---