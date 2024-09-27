package main;

import books.Book;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Book[] books = createBooksFromUserInput(); // Створення масиву об'єктів
        Scanner scanner = new Scanner(System.in);

        // Запитуємо дані для фільтрації
        System.out.print("Введіть автора для виведення: ");
        String author = scanner.nextLine();

        System.out.print("Введіть видавництво для виведення: ");
        String publisher = scanner.nextLine();

        System.out.print("Виведення книг, які видані після (введіть рік): ");
        int year = scanner.nextInt();

        // Виведення результатів
        System.out.println("Книги заданого автора:");
        printBooksByAuthor(books, author);

        System.out.println("\nКниги заданого видавництва:");
        printBooksByPublisher(books, publisher);

        System.out.println("\nКниги, видані після зазначеного року:");
        printBooksByYear(books, year);
    }

    // Метод для введення книг від користувача
    public static Book[] createBooksFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Скільки книг ви хочете додати? ");
        int numBooks = scanner.nextInt();
        scanner.nextLine(); // Зчитати символ нового рядка після числа

        Book[] books = new Book[numBooks]; // Створення масиву об'єктів Book

        for (int i = 0; i < numBooks; i++) {
            System.out.println("Введіть дані для книги " + (i + 1) + ":");

            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Зчитати символ нового рядка

            System.out.print("Назва: ");
            String name = scanner.nextLine();

            System.out.print("Автор: ");
            String author = scanner.nextLine();

            System.out.print("Видавництво: ");
            String publisher = scanner.nextLine();

            System.out.print("Рік видання: ");
            int year = scanner.nextInt();

            System.out.print("Кількість сторінок: ");
            int pages = scanner.nextInt();

            System.out.print("Ціна: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Зчитати символ нового рядка після числа

            // Додаємо нову книгу до масиву
            books[i] = new Book(id, name, author, publisher, year, pages, price);
        }

        return books;
    }

    // Метод для фільтрації книг за автором
    public static void printBooksByAuthor(Book[] books, String author) {
        boolean found = false;
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Книг від зазначеного автора не знайдено.");
        }
    }

    // Метод для фільтрації книг за видавництвом
    public static void printBooksByPublisher(Book[] books, String publisher) {
        boolean found = false;
        for (Book book : books) {
            if (book.getPublisher().equalsIgnoreCase(publisher)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Книг від зазначеного видавництва не знайдено.");
        }
    }

    // Метод для фільтрації книг за роком
    public static void printBooksByYear(Book[] books, int year) {
        boolean found = false;
        for (Book book : books) {
            if (book.getYear() > year) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Книг, виданих після зазначеного року, не знайдено.");
        }
    }
}
