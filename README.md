JDBC Forum is a simple Java console-based forum application using JDBC for database operations. It allows users to register, login, create, update, and delete posts. The application manages users and posts with basic validation and persistence to a relational database.

Key Features
- User Registration and Validation: Users can register with a username and password that meet specified criteria. 
- User Authentication: Users can log in with their username and password to access the system.
- Post Management: Authenticated users can create, update, delete, and view posts. Posts are associated with users and displayed in descending order by date.
- Database Interaction via JDBC: Uses raw JDBC for data persistence through reusable helper classes and repository patterns.
- Input Validation: Ensures username and password formats, preventing invalid data entry.
- Basic Console UI: Text-based user interface for interaction through the console.

Technologies Used:
- JDBC (Java Database Connectivity)
- SQL (for relational database operations)
- Console-based UI
