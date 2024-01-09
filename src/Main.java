public class Main {
    public static void main(String[] args) {
        // Создаем хеш-таблицу с начальной емкостью 16
        HashTable<String, Integer> myHashTable = new HashTable<>();

        // Добавляем элементы
        myHashTable.add("one", 1);
        myHashTable.add("two", 2);
        myHashTable.add("three", 3);

        // Выводим информацию о размере и емкости
        System.out.println("Size: " + myHashTable.size());
        System.out.println("Capacity: " + myHashTable.capacity());

        // Получаем и выводим значение по ключу
        System.out.println("Value for key 'two': " + myHashTable.get("two"));

        // Заменяем значение по ключу
        myHashTable.addOrReplace("two", 22);
        System.out.println("Updated value for key 'two': " + myHashTable.get("two"));

        // Выводим все ключи и значения
        System.out.println("Keys: " + myHashTable.keys());
        System.out.println("Values: " + myHashTable.values());

        // Удаляем элемент по ключу
        myHashTable.remove("one");
        System.out.println("Size after removing 'one': " + myHashTable.size());

        // Очищаем хеш-таблицу
        myHashTable.clear();
        System.out.println("Size after clearing: " + myHashTable.size());
    }
}
