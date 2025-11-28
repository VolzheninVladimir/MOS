package com.example.contactsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.Contact

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val contacts = listOf(
            Contact("Ivan Ivanov", "+7 999 111-22-33"),
            Contact("Petr Petrov", "+7 999 222-33-44"),
            Contact("Sidor Sidorov", "+7 999 333-44-55"),
            Contact("Anna Smirnova", "+7 999 444-55-66"),
            Contact("Maria Kuznecova", "+7 999 555-66-77"),
            Contact("Lubimaya", "+7 999 666-77-88"),
            Contact("Vtoraya Lubimaya", "+7 999 777-88-99"),
            Contact("Sergey Sergeev", "+7 999 888-99-00"),
            Contact("Olga Orlova", "+7 999 000-11-22"),
            Contact("Nikolay Nikolaev", "+7 999 123-45-67"),
            Contact("Tatiana Tatarenko", "+7 999 234-56-78"),
            Contact("Dmitry Dmitriev", "+7 999 345-67-89"),
            Contact("Ekaterina Egorova", "+7 999 456-78-90"),
            Contact("Alexey Alekseev", "+7 999 567-89-01"),
            Contact("Svetlana Sokolova", "+7 999 678-90-12"),
            Contact("Roman Romanov", "+7 999 789-01-23"),
            Contact("Irina Ivanova", "+7 999 890-12-34"),
            Contact("Konstantin Kuzmin", "+7 999 901-23-45"),
            Contact("Natalia Nikolaeva", "+7 999 012-34-56"),
            Contact("Vladimir Vladimirov", "+7 999 135-79-24"),
            Contact("Yulia Yulieva", "+7 999 246-80-35"),
            Contact("Stepan Stepanov", "+7 999 357-91-46"),
            Contact("Polina Petrova", "+7 999 468-02-57"),
            Contact("Arkady Arkadiev", "+7 999 579-13-68"),
            Contact("Marina Makarova", "+7 999 680-24-79")
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ContactAdapter(contacts)
    }
}
