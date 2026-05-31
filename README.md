This contact management system involve 3 data structure which is `LinkedList`, `ArrayList`, and `HashMap`. The program lets you add, remove, edit, search, and sort contacts, and measures side-by-side to see how their performance compares

# Project Structure
src/
├── model/
│   └── Contact.java                    
├── manager/
│   ├── ContactManager.java             
│   ├── AbstractContactStorage.java     
│   ├── LinkedListContactManager.java   
│   ├── ArrayListContactManager.java    
│   └── HashMapContactManager.java      
├── exception/
│   ├── ContactNotFoundException.java
│   ├── DuplicateContactException.java
│   └── InvalidPhoneNumberException.java
└── util/
    ├── Main.java                        
    └── BenchmarkUtility.java            

# How to run Project
1. Open terminal
2. Navigate to the src folder and compile all the project files
3. Run the Program

# Menu
 1.  Add Contact           — prompts for name, phone, email, address, category
 2.  Remove Contact        — removes by exact phone number
 3.  Edit Contact          — updates name and email for a given phone
 4.  Search Contact        — finds a contact by exact phone number
 5.  Display All Contacts  — lists every stored contact
 6.  Sort Alphabetically   — sorts by name (note: case-insensitive)
 7.  Run Benchmark         
 8.  Show Time Complexity  — prints the static Big-O table
 9.  Switch Data Structure — swaps between different data structure
10.  Search by Name        
11.  Remove by Name       
12.  Exit
```






