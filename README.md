# Library Management System (Java)

## 📚 Overview
The **Library Management System** is a Java-based application designed to manage book cataloging, borrowing, and returning efficiently. It utilizes **data structures** such as **hash tables, linked lists, and double hashing** for optimized data retrieval and storage. This system allows users to add books, search for books, borrow and return books, and track borrowed books.

## 🚀 Features
- **Book Management**: Add, search, and delete books using their title, author, or ISBN.
- **Borrowing System**: Users can borrow books with a maximum limit of 3 books per person.
- **Returning System**: Users can return borrowed books, updating the library inventory.
- **Efficient Storage & Retrieval**: Implements **hash tables** for optimized book lookups.
- **User-Friendly Interface**: Interactive menu-driven console-based system.

## 🛠️ Technologies Used
- **Java** (Object-Oriented Programming)
- **Hash Tables** (Double Hashing for collision handling)
- **Linked Lists** (Managing borrowers list)
- **Arrays** (Book storage)
- **Scanner Class** (User input handling)

## 📂 Project Structure
```
📁 LibraryManagementSystem
│── 📄 Book.java       # Book class with attributes and methods
│── 📄 Borrower.java   # Borrower class for tracking users
│── 📄 LibraryManagementSystem.java # Main system logic (hash tables, borrowing logic, etc.)
│── 📄 LibraryManagementSystemTest.java # Main program (CLI-based menu)
│── 📄 README.md       # Project documentation
```

## 🚀 Getting Started
### Prerequisites
Ensure you have the following installed on your system:
- **Java JDK 8+**
- **Any Java IDE (Eclipse, IntelliJ, or VS Code)**

### Installation
1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/LibraryManagementSystem.git
   cd LibraryManagementSystem
   ```

2. **Compile the project**
   ```bash
   javac LibraryManagementSystemTest.java
   ```

3. **Run the application**
   ```bash
   java LibraryManagementSystemTest
   ```

## 🎮 Usage
1. Run the program to display the **Library Management System Menu**.
2. Choose an option by entering the corresponding number.
3. Follow the prompts to **add, search, borrow, return, or delete** books.
4. Enter **'11'** to exit the system.

## 🤝 Contributing
Contributions are welcome! Feel free to **fork** the repository and submit a **pull request** with improvements.

## 💡 Author
Developed by Clovis Mushagalusa CIRUBAKADERHA.

