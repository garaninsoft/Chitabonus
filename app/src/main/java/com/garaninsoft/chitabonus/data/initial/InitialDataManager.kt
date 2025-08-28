package com.garaninsoft.chitabonus.data.initial

import com.garaninsoft.chitabonus.domain.model.Book
import com.garaninsoft.chitabonus.domain.model.Question
import com.garaninsoft.chitabonus.domain.model.SettingKeys.CURRENCY_LABEL
import com.garaninsoft.chitabonus.domain.model.SettingKeys.POINTS_PER_UNIT
import com.garaninsoft.chitabonus.domain.repository.BookRepository
import com.garaninsoft.chitabonus.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class InitialDataManager @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val bookRepository: BookRepository
) {

    suspend fun initializeIfNeeded() {
        initializeSettings()
        initializeDemoBooks()
    }

    private suspend fun initializeSettings() {
        // Проверяем, есть ли уже настройки
        val currentPointsSetting = settingsRepository.getSetting(POINTS_PER_UNIT).first()
        val currentCurrencySetting = settingsRepository.getSetting(CURRENCY_LABEL).first()

        if (currentPointsSetting == null) {
            settingsRepository.setPointsPerUnit(1.0) // 1 балл = 1 условная единица
        }

        if (currentCurrencySetting == null) {
            settingsRepository.setCurrencyLabel("₽") // Рубль по умолчанию
        }
    }

    private suspend fun initializeDemoBooks() {
        val existingBooks = bookRepository.getAllBooks().first()

        if (existingBooks.isEmpty()) {
            // Создаём и сохраняем книги с вопросами
            createAndSaveDemoBooks()
        }
    }

    private suspend fun createAndSaveDemoBooks() {
        // Книга 1: Маленький принц
        val littlePrinceId = bookRepository.upsertBook(
            Book(
                title = "Маленький принц",
                author = "Антуан де Сент-Экзюпери",
                pages = 96,
                ageFrom = 7,
                ageTo = 12
            )
        )

        // Вопросы для Маленького принца
        listOf(
            Question(
                bookId = littlePrinceId,
                text = "Кто нарисовал барашка для Маленького принца?",
                correctAnswer = "Лётчик",
                isMain = true,
                difficulty = 3
            ),
            Question(
                bookId = littlePrinceId,
                text = "Какая планета была у Маленького принца?",
                correctAnswer = "Астероид Б-612",
                isMain = true,
                difficulty = 4
            ),
            Question(
                bookId = littlePrinceId,
                text = "Кого Маленький принц попросил нарисовать в первый раз?",
                correctAnswer = "Барашка",
                isMain = false,
                difficulty = 2
            )
        ).forEach { question ->
            bookRepository.upsertQuestion(question)
        }

        // Книга 2: Гарри Поттер
        val harryPotterId = bookRepository.upsertBook(
            Book(
                title = "Гарри Поттер и философский камень",
                author = "Джоан Роулинг",
                pages = 432,
                ageFrom = 10,
                ageTo = 14
            )
        )

        // Вопросы для Гарри Поттера
        listOf(
            Question(
                bookId = harryPotterId,
                text = "Как зовут мальчика, который выжил?",
                correctAnswer = "Гарри Потter",
                isMain = true,
                difficulty = 1
            ),
            Question(
                bookId = harryPotterId,
                text = "На каком факультете оказался Гарри?",
                correctAnswer = "Гриффиндор",
                isMain = true,
                difficulty = 3
            )
        ).forEach { question ->
            bookRepository.upsertQuestion(question)
        }

        // Книга 3: Волшебник Изумрудного города
        val wizardOfOzId = bookRepository.upsertBook(
            Book(
                title = "Волшебник Изумрудного города",
                author = "Александр Волков",
                pages = 256,
                ageFrom = 6,
                ageTo = 10
            )
        )

        // Вопросы для Волшебника Изумрудного города
        listOf(
            Question(
                bookId = wizardOfOzId,
                text = "Как звали девочку, которую унесло ураганом?",
                correctAnswer = "Элли",
                isMain = true,
                difficulty = 2
            ),
            Question(
                bookId = wizardOfOzId,
                text = "Кто искал храбрость?",
                correctAnswer = "Лев",
                isMain = true,
                difficulty = 3
            )
        ).forEach { question ->
            bookRepository.upsertQuestion(question)
        }
    }
}