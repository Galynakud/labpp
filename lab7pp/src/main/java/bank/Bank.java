package bank;
import java.util.ArrayList;
import java.util.List;
public class Bank {
    private final List<Credit> credits;
    private final List<Client> clients;
    public Bank() {
        this.credits = new ArrayList<>();
        this.clients = new ArrayList<>();

        credits.add(new Credit("Споживчий кредит", 5000, 5.5, 24, true));
        credits.add(new Credit("Авто кредит", 10000, 6.0, 36, true));
        credits.add(new Credit("Іпотечний кредит", 20000, 7.5, 120, false));
        credits.add(new Credit("Кредит на навчання", 3000, 4.5, 12, true));
        credits.add(new Credit("Кредит на бізнес", 15000, 8.0, 60, false));
        credits.add(new Credit("Кредит під заставу", 25000, 6.5, 48, true));
        credits.add(new Credit("Кредит на ремонт", 7000, 5.0, 18, true));
        credits.add(new Credit("Кредит на подорожі", 4000, 6.2, 24, true));
        credits.add(new Credit("Кредит на електроніку", 2000, 5.8, 6, true));
        credits.add(new Credit("Кредит на відпустку", 8000, 7.0, 36, true));
        credits.add(new Credit("Кредит для стартапу", 12000, 6.5, 48, false));
        credits.add(new Credit("Кредит на ремонт квартири", 10000, 5.5, 60, true));
        credits.add(new Credit("Кредит на освіту за кордоном", 25000, 4.0, 120, false));
        credits.add(new Credit("Кредит на купівлю комп'ютера", 1500, 5.5, 12, true));
        credits.add(new Credit("Кредит на медичні послуги", 3000, 5.0, 6, true));

        clients.add(new Client("Іван Петренко", "1234", 5000));
        clients.add(new Client("Олена Коваленко", "5678", 7000));
        clients.add(new Client("Сергій Іванов", "abcd", 10000));
        clients.add(new Client("Марія Сидоренко", "efgh", 15000));
        clients.add(new Client("Андрій Бондар", "pass123", 12000));
        clients.add(new Client("Тетяна Мельник", "pass456", 8000));
        clients.add(new Client("Володимир Гнатюк", "pass789", 9000));
        clients.add(new Client("Оксана Крамар", "pass101", 11000));
        clients.add(new Client("Дмитро Стеценко", "pass202", 13000));
        clients.add(new Client("Аліна Шевченко", "pass303", 14000));
        clients.add(new Client("Олег Яременко", "pass404", 16000));
        clients.add(new Client("Ірина Кузьменко", "pass505", 17000));
        clients.add(new Client("Анастасія Бондаренко", "pass606", 18000));
        clients.add(new Client("Євгеній Поліщук", "pass707", 19000));
        clients.add(new Client("Віра Петренко", "pass808", 20000));
    }
    public List<Credit> getCredits() {
        return credits;
    }
    public List<Client> getClients() {
        return clients;
    }
    public void addCredit(Credit credit) {
        credits.add(credit);
    }

    public Credit getCreditByName(String name) {
        for (Credit credit : credits) {
            if (credit.getName().equalsIgnoreCase(name)) {
                return credit;
            }
        }
        return null; // Повертає null, якщо кредит не знайдено
    }

    public void editCredit(int i, Credit updatedCredit) {
    }
}