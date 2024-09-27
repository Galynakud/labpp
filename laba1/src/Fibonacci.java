import java.util.Scanner;

/**
 * Клас для обчислення та зберігання чисел Фібоначчі.
 *
 * @author (Ваше ім'я)
 * @version (Версія)
 */
public class Fibonacci {

    private int number;  // номер числа (N)
    private int fibon;   // значення числа Фібоначчі

    /**
     * Конструктор класу Fibonacci.
     * Обчислює число Фібоначчі для заданого N.
     *
     * @param number Номер числа Фібоначчі (N).
     */
    public Fibonacci(int number) {
        this.number = number;
        this.fibon = fibonacci(number);  // Викликає метод обчислення числа Фібоначчі
    }

    /**
     * Метод для обчислення N-го числа Фібоначчі.
     *
     * @param n Ціле число, для якого потрібно обчислити число Фібоначчі.
     * @return N-е число Фібоначчі.
     */
    private int fibonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1 || n == -1) {
            return 1;
        } else if (n > 0) {
            int a = 0, b = 1;
            for (int i = 2; i <= n; i++) {
                int temp = a + b;
                a = b;
                b = temp;
            }
            return b;
        } else {
            int fibmin = fibonacci(-n);
            return (n % 2 == 0) ? -fibmin : fibmin;
        }
    }

    /**
     * Метод для отримання номера числа Фібоначчі.
     *
     * @return Номер числа Фібоначчі.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Метод для отримання значення числа Фібоначчі.
     *
     * @return Значення числа Фібоначчі.
     */
    public int getfibon() {
        return fibon;
    }

    /**
     * Головна функція для запуску програми.
     *
     * @param args Аргументи командного рядка (не використовуються).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Введення користувачем значення N
        System.out.print("Введіть N: ");
        int N = scanner.nextInt();

        // Створення об'єкта класу Fibonacci для обчислення числа Фібоначчі
        Fibonacci fibonacciNumber = new Fibonacci(N);

        // Виведення результату
        System.out.println("Число Фібоначчі для N = " + fibonacciNumber.getNumber() + " : " + fibonacciNumber.getfibon());
    }
}
