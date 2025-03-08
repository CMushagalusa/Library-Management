import java.util.*;

// Represents a book with title, author, ISBN, and edition.
class Book {
    String title; // book title
    String author; // book author
    String isbn;
    int edition;
    int copies;

    // Constructor to initialize a book object.
    public Book(String title, String author, String isbn, int edition) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.edition = edition;
        this.copies = 1;
    }

    // Returns a string representation of the book.
    public String toString() {
        return "Author: " + this.author + " Title: " + this.title + " ISBN: " + this.isbn + " Edition: " + this.edition
                + " Copies: " + this.copies;
    }

    // Returns an enumeration of a book.
    public String listAuthorBook() {
        return "- Title: " + this.title + " ISBN: " + this.isbn + " Edition: " + this.edition;
    }

    // Returns an enumeration of a book.
    public String listTitleBook() {
        return "- Author: " + this.author + " ISBN: " + this.isbn + " Edition: " + this.edition;
    }

    // Checks the equality of two books.
    public boolean equals(Book book) {
        if (this.title.equalsIgnoreCase(book.title) && this.author.equalsIgnoreCase(book.author)
                && this.isbn.equalsIgnoreCase(book.isbn) && this.edition == book.edition) {
            return true;
        }
        return false;
    }

    // Increments the number of copies.
    public void addOneMoreCopy() {
        this.copies++;
    }

    // Decrements the number of copies.
    public void removeOneCopy() {
        this.copies--;
    }

    // Returns the number of copies of a book.
    public int numberOfCopies() {
        return copies;
    }

    // Sets a number of copies.
    public void setBookCopy(int copy) {
        this.copies = copy;
    }

    // Checks if the book has no copy available.
    public boolean hasNoCopy() {
        if (copies == 0) {
            return true;
        }
        return false;
    }
}

// Represents a borrower with name, id, and borrowed book.
class Borrower {
    String name;
    int id;
    int nbooks = 0;
    static final int MAXIMUM = 3;
    Book borrowed;

    // Constructor to initialize a borrower object.
    public Borrower(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // Constructor to initialize a borrower object.
    public Borrower(String name, int id, Book book) {
        this.name = name;
        this.id = id;
        this.borrowed = book;
    }

    // Returns the number of books borrowed.
    public int getNumberOfBooks() {
        return nbooks;
    }

    // Displays details about a borrower.
    public String toString() {
        return " Name : " + this.name + "  ID : " + this.id + " Number of books borrowed : "
                + this.getNumberOfBooks();
    }
}

// Class used to manage the library system with all the functionalities.
class LibraryManagementSystem {
    private Book[] bookHashTableByTitles;
    private Book[] bookHashTableByAuthors;
    private Book[] bookHashTableByIsbns;
    private LinkedList<Borrower> borrowers;

    private int libraryCapacity;
    private int booksInTheLibrary;
    private int booksBorrowed;

    private static final int DEFAULT_CAPACITY = 41;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private static final int RESIZE = 2;
    private static final int COPY = 1;

    // Constructor to initialize a library.
    public LibraryManagementSystem() {
        this(DEFAULT_CAPACITY);
    }

    // Constructor to create a library.
    public LibraryManagementSystem(int libraryCapacity) {

        this.libraryCapacity = libraryCapacity;
        this.bookHashTableByTitles = new Book[libraryCapacity];
        this.bookHashTableByAuthors = new Book[libraryCapacity];
        this.bookHashTableByIsbns = new Book[libraryCapacity];
        this.borrowers = new LinkedList<>();
        this.booksInTheLibrary = 0;
        this.booksBorrowed = 0;

    }

    // Returns the capacity of the table.
    public int libraryStorageCapacity() {
        return libraryCapacity;
    }

    // Returns the number of elements in the table.
    public int numberOfBooksAvailableInTheLibrary() {
        return booksInTheLibrary;
    }

    // Returns the number of borrowed books
    public int numberOfBooksBorrowed() {
        return booksBorrowed;
    }

    // Checks if the library is empty.
    public boolean isLibraryEmpty() {
        if (booksInTheLibrary == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if a book has been borrowed from the library.
    public boolean isBorrowersEmpty() {
        if (booksBorrowed == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Method used to add a new book on the library catalogue.
    public boolean addABookInTheLibrary() {

        System.out.println("===== Enter the details of the book you want to add. =====");
        System.out.println();
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Type the book title here: ");
        String title = keyboard.nextLine();

        System.out.print("Type the book author here: ");
        String author = keyboard.nextLine();

        System.out.print("Type the book isbn here: ");
        String isbn = keyboard.nextLine();

        System.out.print("Type the book edition here: ");
        int edition = keyboard.nextInt();

        keyboard.nextLine();

        Book book = new Book(title, author, isbn, edition);

        if (loadFactor() >= LOAD_FACTOR_THRESHOLD) {
            resizeTheLibrary();
        }
        ;
        if (isTheBookInTheLibrary(book)) {
            addOneMoreCopyInHashTables(book);
            return true;
        } else {
            int key0 = Math.abs(book.title.toLowerCase().hashCode());
            int key1 = Math.abs(book.author.toLowerCase().hashCode());
            int key2 = Math.abs(book.isbn.toLowerCase().hashCode());

            int index0 = primaryHash(key0); // Hashing the title.
            int index1 = primaryHash(key1); // Hashing the author.
            int index2 = primaryHash(key2); // Hashing the isbn.

            int addition0 = 0;
            int addition1 = 0;
            int addition2 = 0;

            // Adding to the titles hash table.
            while (bookHashTableByTitles[index0] != null) {
                index0 = doubleHashing(key0, addition0);
                addition0++;
            }
            bookHashTableByTitles[index0] = book;

            // Adding to the authors hash table.
            while (bookHashTableByAuthors[index1] != null) {
                index1 = doubleHashing(key1, addition1);
                addition1++;
            }
            bookHashTableByAuthors[index1] = book;

            // Adding to the isbns hash table.
            while (bookHashTableByIsbns[index2] != null) {
                index2 = doubleHashing(key2, addition2);
                addition2++;
            }
            bookHashTableByIsbns[index2] = book;

            booksInTheLibrary++;
            return true;
        }
    }

    // Method used to add the book after returned by the borrower.
    private boolean addABookInTheLibrary(Book book) {
        if (loadFactor() >= LOAD_FACTOR_THRESHOLD) {
            resizeTheLibrary();
        }

        if (isTheBookInTheLibrary(book)) {
            addOneMoreCopyInHashTables(book);
            return true;
        } else {
            int key0 = Math.abs(book.title.toLowerCase().hashCode());
            int key1 = Math.abs(book.author.toLowerCase().hashCode());
            int key2 = Math.abs(book.isbn.toLowerCase().hashCode());

            int index0 = primaryHash(key0); // Hashing the title.
            int index1 = primaryHash(key1); // Hashing the author.
            int index2 = primaryHash(key2); // Hashing the isbn.

            int addition0 = 0;
            int addition1 = 0;
            int addition2 = 0;

            // Adding to the titles hash table.
            while (bookHashTableByTitles[index0] != null) {
                index0 = doubleHashing(key0, addition0);
                addition0++;
            }
            bookHashTableByTitles[index0] = book;

            // Adding to the authors hash table.
            while (bookHashTableByAuthors[index1] != null) {
                index1 = doubleHashing(key1, addition1);
                addition1++;
            }
            bookHashTableByAuthors[index1] = book;

            // Adding to the isbns hash table.
            while (bookHashTableByIsbns[index2] != null) {
                index2 = doubleHashing(key2, addition2);
                addition2++;
            }
            bookHashTableByIsbns[index2] = book;

            booksInTheLibrary++;
            return true;
        }
    }

    // Add one copy of a book in the library.
    private void addOneMoreCopyInHashTables(Book book) {
        for (Book copy : bookHashTableByTitles) {
            if (copy != null && copy.equals(book)) {
                copy.addOneMoreCopy();
                booksInTheLibrary++;
            }
        }
    }

    // Remove a copy of a book in a library when borrowed.
    private void removeOneCopyFromHashTables(Book book) {
        for (Book copy : bookHashTableByIsbns) {
            if (copy != null && copy.equals(book)) {
                copy.removeOneCopy();
                booksInTheLibrary--;
            }
        }
    }

    // Searches books using either the title, the author or the isbn, then displays
    // them.
    public void searchABookInTheLibrary() {

        System.out.println("===== Which book attribute do want to use for searching? =====");
        Scanner scanner = new Scanner(System.in);

        System.out.println();
        System.out.println("1. Book title.");
        System.out.println("2. Book author.");
        System.out.println("3. Book isbn.");
        System.out.println();

        System.out.print("Type your choice here: ");

        int userFeedback = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println();

        // Searching the book using the title.
        if (userFeedback == 1) {
            System.out.print("Enter the book title here: ");
            String bookTitle = scanner.nextLine();

            int key = Math.abs(bookTitle.toLowerCase().hashCode());
            int primaryIndex = primaryHash(key);
            int secondaryIndex = secondaryHash(key);
            int retrieve = 0;

            while (retrieve < libraryStorageCapacity()) {
                int index = (primaryIndex + retrieve * secondaryIndex) % libraryStorageCapacity();

                if (bookHashTableByTitles[index] != null
                        && bookHashTableByTitles[index].title.equalsIgnoreCase(bookTitle)) {
                    getBooksByATitle(bookTitle);
                    break;
                }
                retrieve++;
                System.out.println("We do not have that book titled '" + bookTitle + "' in our lirary!!!!");
                break;
            }
        }

        // Searching a book using the author.
        else if (userFeedback == 2) {
            System.out.print("Enter the book author here: ");
            String bookAuthor = scanner.nextLine();

            int key = Math.abs(bookAuthor.toLowerCase().hashCode());
            int primaryIndex = primaryHash(key);
            int secondaryIndex = secondaryHash(key);
            int retrieve = 0;

            while (retrieve < libraryStorageCapacity()) {
                int index = (primaryIndex + retrieve * secondaryIndex) % libraryStorageCapacity();

                if (bookHashTableByAuthors[index] != null
                        && bookHashTableByAuthors[index].author.equalsIgnoreCase(bookAuthor)) {
                    getBooksByAnAuthor(bookAuthor);
                    break;
                }
                retrieve++;
                System.out.println("We do not have books by '" + bookAuthor + "' in our library!!!!");
                break;
            }
        }

        // Searching a book using the isbn.
        else if (userFeedback == 3) {
            System.out.print("Enter the book isbn here: ");
            String bookIsbn = scanner.nextLine();

            int key = Math.abs(bookIsbn.toLowerCase().hashCode());
            int primaryIndex = primaryHash(key);
            int secondaryIndex = secondaryHash(key);
            int retrieve = 0;

            while (retrieve < libraryStorageCapacity()) {
                int index = (primaryIndex + retrieve * secondaryIndex) % libraryStorageCapacity();

                if (bookHashTableByIsbns[index] != null
                        && bookHashTableByIsbns[index].isbn.equalsIgnoreCase(bookIsbn)) {
                    getBooksByAIsbn(bookIsbn);
                    break;
                }
                retrieve++;
                System.out.println("We do not have books whose isbn is '" + bookIsbn + "' in our library!!!!");
                break;
            }
        }
    }

    // Searches a book for the user to borrow.
    private Book searchABookToBeBorrowed() {

        if (!isLibraryEmpty()) {

            System.out.println("========== Which book are you looking for? ==========");
            Scanner scanner = new Scanner(System.in);

            System.out.println();
            System.out.print("Type the book title here: ");
            String bookTitle = scanner.nextLine();

            // Searching the book using the title.
            int key = Math.abs(bookTitle.toLowerCase().hashCode());
            int primaryIndex = primaryHash(key);
            int secondaryIndex = secondaryHash(key);
            int retrieve = 0;

            while (retrieve < libraryStorageCapacity()) {
                int index = (primaryIndex + retrieve * secondaryIndex) % libraryStorageCapacity();

                if (bookHashTableByTitles[index].title.equalsIgnoreCase(bookTitle)) {
                    getBooksByATitle(bookTitle);
                    System.out.println();
                    System.out.println("Check the author whose book you want to borrow.");
                    System.out.println();
                    System.out.print("Type the book author here: ");
                    String bookAuthor = scanner.nextLine();

                    if (bookHashTableByTitles[index].author.equalsIgnoreCase(bookAuthor)) {
                        return bookHashTableByTitles[index];
                    }
                }
                retrieve++;
            }
            return null;
        }
        return null;
    }

    // Displays all books written by a particular author.
    private void getBooksByAnAuthor(String newAuthor) {
        System.out.println();
        System.out.println("===== Authors and their books =====");
        System.out.println("Here are the books written by '" + newAuthor + "' in our library: ");
        System.out.println();
        for (Book book : bookHashTableByAuthors) {
            if (book != null && book.author.equalsIgnoreCase(newAuthor)) {
                System.out.println(book.listAuthorBook());
            }
        }
    }

    // Displays all books with a particular title.
    private void getBooksByATitle(String newTitle) {
        System.out.println();
        System.out.println("===== Books with a common title. =====");
        System.out.println("Here are the books with the title '" + newTitle + "' in our library: ");
        System.out.println();
        for (Book book : bookHashTableByTitles) {
            if (book != null && book.title.equalsIgnoreCase(newTitle)) {
                System.out.println(book.listTitleBook());
            }
        }
    }

    // Displays all books with a particular isbn.
    private void getBooksByAIsbn(String newIsbn) {
        System.out.println();
        System.out.println("===== Books with a common isbn. =====");
        System.out.println("Here are the books with the isbn '" + newIsbn + "' in our library: ");
        System.out.println();
        for (Book book : bookHashTableByIsbns) {
            if (book != null && book.isbn.equalsIgnoreCase(newIsbn)) {
                System.out.println(book.listAuthorBook());
            }
        }
    }

    // Checks a book in the titles hash table.
    private boolean isInTitlesHashTable(Book book) {
        if (!isLibraryEmpty()) {
            for (Book bookIn : bookHashTableByTitles) {
                if (bookIn != null && bookIn.equals(book)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks a book in the author hash table.
    private boolean isInAuthorsHashTable(Book book) {
        if (!isLibraryEmpty()) {
            for (Book bookIn : bookHashTableByAuthors) {
                if (bookIn != null && bookIn.equals(book)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks a book in the isbns hash table.
    private boolean isInIsbnsHashTable(Book book) {
        if (!isLibraryEmpty()) {
            for (Book bookIn : bookHashTableByIsbns) {
                if (bookIn != null && bookIn.equals(book)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks if a book is already in the library.
    private boolean isTheBookInTheLibrary(Book book) {
        if (isInTitlesHashTable(book) && isInAuthorsHashTable(book) && isInIsbnsHashTable(book)) {
            return true;
        }
        return false;
    }

    // Method used to borrow a book.
    public boolean checkABookOutOfTheLibrary() {
        System.out.println();
        System.out.println("===== You want to get a book? We are here to help. =====");

        Scanner checker = new Scanner(System.in);
        Book book = searchABookToBeBorrowed();

        if (book != null) {
            if (!book.hasNoCopy()) {
                System.out.println();
                System.out.println("===== We would like to get your identification. =====");
                System.out.println();

                System.out.print("Type your name here: ");
                String name = checker.nextLine();
                System.out.print("Type your id here: ");
                int id = checker.nextInt();
                checker.nextLine();

                String title = book.title;
                String author = book.author;
                String isbn = book.isbn;
                int edition = book.edition;
                Book bbook = new Book(title, author, isbn, edition);

                Borrower user = new Borrower(name, id, bbook);
                borrowers.add(user);
                booksBorrowed++;
                removeOneCopyFromHashTables(book);
                return true;
            }
            System.out.println("Sorry, the book is not currently available.");
            return false;
        }
        System.out.println("Sorry, we do not currently have a book to lend in our library.");
        return false;
    }

    // Displays all the books in the library.
    public void viewAllBooksInTheLibrary() {
        if (!isLibraryEmpty()) {
            System.out.println("The books registered in our library are: ");
            System.out.println();
            for (Book book : bookHashTableByTitles) {
                if (book != null) {
                    System.out.println(book.toString());
                }
            }
        } else {
            System.out.println("There is currently no book in our library.");
        }
    }

    // Displays all the books available in the library.
    public void viewAllBooksAvailableInTheLibrary() {
        if (!isLibraryEmpty()) {
            System.out.println("The books currently available in our library are: ");
            System.out.println();
            for (Book book : bookHashTableByTitles) {
                if (book != null && !book.hasNoCopy()) {
                    System.out.println(book.toString());
                }
            }
        } else {
            System.out.println("There is currently no available book in our library.");
        }
    }

    // Displays all the books currently borrowed.
    public void viewAllBorrowedBooks() {
        if (!isBorrowersEmpty()) {
            System.out.println("The books borrowed from our library are: ");
            System.out.println();
            for (Borrower borrower : borrowers) {
                System.out.println(borrower.borrowed.toString());
            }
        } else {
            System.out.println("No book from our library has been borrowed.");
        }
    }

    // Method used to return a book.
    public boolean checkABookInTheLibrary() {
        if (!isBorrowersEmpty()) {
            System.out.println("========== We are happy that you are returning the book. ==========");
            System.out.println("=================== Input your identification. ====================");
            System.out.println();

            Scanner checker = new Scanner(System.in);

            System.out.print("Type your name here: ");
            String name = checker.nextLine();
            System.out.print("Type your id here: ");
            int id = checker.nextInt();

            checker.nextLine();

            System.out.println();
            System.out.println("===== Provide information about the book you are returning. =====");
            System.out.println();

            System.out.print("Type the book title here: ");
            String title = checker.nextLine();

            System.out.print("Type the book author here: ");
            String author = checker.nextLine();

            System.out.print("Type the book isbn here: ");
            String isbn = checker.nextLine();

            System.out.print("Type the book edition here: ");
            int edition = checker.nextInt();

            Book book = new Book(title, author, isbn, edition);
            Borrower user = new Borrower(name, id, book);

            checker.nextLine();

            if (isTheBookInTheLibrary(book)) {
                // Takes the book from the borrower and add it back to the library.
                for (Borrower bookUser : borrowers) {
                    if (bookUser.id == id) {
                        if (bookUser.borrowed.equals(book)) {
                            addABookInTheLibrary(bookUser.borrowed);
                            borrowers.remove(bookUser);
                            System.out.println("Thank you!! The book is succefully returned.");
                            System.out.println("===== We would happy to see you again!!! =====");
                            return true;
                        } else {
                            System.out.println();
                            System.out
                                    .print("Sorry, you might have made a mistake. This is not the book you borrowed.");
                            System.out.println();
                            return false;
                        }
                    }
                }
            } else {
                System.out.println();
                System.out.println("Sorry, you might have made a mistake. We have not had this book in our library.");
                return false;
            }
        }
        System.out.println("Sorry, you might have made a mistake. We have not lended any of our books.");
        return false;
    }

    // Removes a book from the hash table (library).
    public boolean deleteABookFromTheLibrary() {

        System.out.println("===== Enter the details of the book you want to remove. =====");
        System.out.println();
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Type the book title here: ");
        String title = keyboard.nextLine();
        System.out.print("Type the book author here: ");
        String author = keyboard.nextLine();
        System.out.print("Type the book isbn here: ");
        String isbn = keyboard.nextLine();
        System.out.print("Type the book edition here: ");
        int edition = keyboard.nextInt();

        keyboard.nextLine();
        Book book = new Book(title, author, isbn, edition);

        if (isTheBookInTheLibrary(book)) {
            int bookCopies = book.numberOfCopies();

            int key0 = Math.abs(book.title.toLowerCase().hashCode());
            int key1 = Math.abs(book.author.toLowerCase().hashCode());
            int key2 = Math.abs(book.isbn.toLowerCase().hashCode());

            int index0 = primaryHash(key0);
            int index1 = primaryHash(key1);
            int index2 = primaryHash(key2);

            int deletion0 = 0;
            int deletion1 = 0;
            int deletion2 = 0;

            // Remove the book from the titles hash table.
            while (bookHashTableByTitles[index0] != null) {
                if (bookHashTableByTitles[index0].equals(book)) {

                    bookHashTableByTitles[index0] = null;
                }
                index0 = doubleHashing(key0, deletion0);
                deletion0++;
            }

            // Remove the book from the authors hash table.
            while (bookHashTableByAuthors[index1] != null) {
                if (bookHashTableByAuthors[index1].equals(book)) {

                    bookHashTableByAuthors[index1] = null;
                }
                index1 = doubleHashing(key1, deletion1);
                deletion1++;
            }

            // Remove the book from the isbns hash table.
            while (bookHashTableByIsbns[index2] != null) {
                if (bookHashTableByIsbns[index2].equals(book)) {

                    bookHashTableByIsbns[index2] = null;
                }
                index2 = doubleHashing(key2, deletion2);
                deletion2++;
            }
            booksInTheLibrary = booksInTheLibrary - bookCopies;
            System.out.println("The book has been succeffully removed.");
            return true;
        }
        System.out.println("Sorry, we do not have that book in our library.");
        return false;
    }

    // Primary hash function.
    private int primaryHash(int primary) {
        return (primary % libraryStorageCapacity());
    }

    // Secondary hash function for double hashing.
    private int secondaryHash(int secondary) {
        int prime = 0;

        for (int s = 0; s < libraryStorageCapacity(); s++) {

            if (isPrime((libraryStorageCapacity() - s))) {
                prime = (libraryStorageCapacity() - s);
            }
        }
        return (prime - (secondary % prime));
    }

    // Double hashing for collision resolution.
    private int doubleHashing(int resolution, int s) {
        return ((primaryHash(resolution) + (s * secondaryHash(resolution))) % libraryStorageCapacity());
    }

    // Checks if a number is prime.
    private boolean isPrime(int number) {

        if (number <= 1) {
            return false;
        }

        for (int n = 2; n <= Math.sqrt(number); n++) {
            if (number % n == 0) {
                return false;
            }
        }
        return true;
    }

    // Calculates the load factor of the hash table.
    private double loadFactor() {
        return (double) (numberOfBooksAvailableInTheLibrary() / libraryStorageCapacity());
    }

    // Gets the next prime number for resizing
    private int getNextPrime(int number) {
        while (!isPrime(number)) {
            number++;
        }
        return number;
    }

    // Resizes the hash table and rehash entries.
    private void resizeTheLibrary() {
        int newCapacity = getNextPrime(libraryCapacity * RESIZE);
        Book[] newTitles = new Book[newCapacity];
        Book[] newAuthors = new Book[newCapacity];
        Book[] newIsbns = new Book[newCapacity];
        Arrays.fill(newTitles, null);
        Arrays.fill(newAuthors, null);
        Arrays.fill(newIsbns, null);

        // Adding to the titles hash table after resizing.
        for (Book book : bookHashTableByTitles) {
            if (book != null) {
                int key = Math.abs(book.title.toLowerCase().hashCode());
                int newIndex = primaryHash(key);
                int sizer = 0;

                while (newTitles[newIndex] != null) {
                    newIndex = doubleHashing(key, sizer);
                    sizer++;
                }
                newTitles[newIndex] = book;
            }
        }

        // Adding to the authors hash table after resizing.
        for (Book book : bookHashTableByAuthors) {
            if (book != null) {
                int key = Math.abs(book.author.toLowerCase().hashCode());
                int newIndex = primaryHash(key);
                int sizer = 0;

                while (newAuthors[newIndex] != null) {
                    newIndex = doubleHashing(key, sizer);
                    sizer++;
                }
                newAuthors[newIndex] = book;
            }
        }

        // Adding to the isbns hash table after resizing.
        for (Book book : bookHashTableByIsbns) {
            if (book != null) {
                int key = Math.abs(book.isbn.toLowerCase().hashCode());
                int newIndex = primaryHash(key);
                int sizer = 0;

                while (newIsbns[newIndex] != null) {
                    newIndex = doubleHashing(key, sizer);
                    sizer++;
                }
                newIsbns[newIndex] = book;
            }
        }
        bookHashTableByTitles = newTitles;
        bookHashTableByAuthors = newAuthors;
        bookHashTableByIsbns = newIsbns;
        libraryCapacity = newCapacity;
    }

}

public class LibraryManagementSystemTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryManagementSystem library = new LibraryManagementSystem();

        // Menu-driven interface for the library management system
        while (true) {
            System.out.println();
            System.out.println("===== Library Management System Menu =====");
            System.out.println();
            System.out.println("1. Add a book in the library.");
            System.out.println("2. Search a book.");
            System.out.println("3. Borrow a book.");
            System.out.println("4. Return a book.");
            System.out.println("5. View all the books in the library.");
            System.out.println("6. View all the books available in the library.");
            System.out.println("7. View all borrowed books.");
            System.out.println("8. Find the number of books in the library.");
            System.out.println("9. Find the number of books borrowed.");
            System.out.println("10. Remove a book from the library.");
            System.out.println("11. Quit!");
            System.out.println();
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.println();

            // Switch case to handle user input and perform corresponding actions
            switch (choice) {
                case 1:
                    library.addABookInTheLibrary();
                    break;
                case 2:
                    library.searchABookInTheLibrary();
                    break;
                case 3:
                    library.checkABookOutOfTheLibrary();
                    break;
                case 4:
                    library.checkABookInTheLibrary();
                    break;
                case 5:
                    library.viewAllBooksInTheLibrary();
                    break;
                case 6:
                    library.viewAllBooksAvailableInTheLibrary();
                    break;
                case 7:
                    library.viewAllBorrowedBooks();
                    break;
                case 8:
                    System.out.println(
                            "There are " + library.numberOfBooksAvailableInTheLibrary() + " books in the library.");
                    break;
                case 9:
                    System.out.println(
                            "From our library, " + library.numberOfBooksBorrowed() + " are currently borrowed.");
                    break;
                case 10:
                    library.deleteABookFromTheLibrary();
                    break;
                case 11:
                    System.out.println("===== It was a pleasure having you on our platform. =====");
                    System.out.println("============== We hope to see you again!! ===============");
                    System.out.println("===================== Goodbye!! =========================");
                    System.exit(0); // Exits the program
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }
}