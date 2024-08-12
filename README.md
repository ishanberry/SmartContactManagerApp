

# Welcome to the Smart Contact Manager

The **Smart Contact Manager** is a robust full-stack web application designed to efficiently manage your contacts with a suite of advanced features. Built with modern web technologies, this application ensures secure, user-friendly, and scalable contact management.

## Key Features
- **User Authentication via OAuth2**: Secure login using Google and GitHub, as well as database-based authentication.
- **Full CRUD Operations**: Create, read, update, and delete contacts with ease.
- **Secure Password Encryption**: Protect user credentials with strong encryption and validation mechanisms, using Spring Security.
- **Contact Image Storage**: Seamless integration with Cloudinary for storing and managing contact images.
- **AJAX-Powered Modal Views**: Enhance user experience with dynamic, AJAX-powered modal views for better interactivity without page reloads.
- **Separate Admin Portal**: A dedicated admin portal for managing users and performing administrative tasks.
- **Email Verification**: Implemented for secure new user registrations, ensuring that only verified users can access the application.
- **Search and Pagination**: Efficiently navigate and manage large sets of data with powerful search and pagination features.
- **Export Contacts to Excel**: Easily export contact lists to Excel for data handling, sharing, and backups.

## Upcoming Features
- **Voice-Based Contact Management**: AI-powered speech recognition for hands-free contact management.
- **Advanced Analytics Dashboard**: Gain insights with an analytics dashboard, providing detailed metrics and trends related to contact management.

## Technologies Used
- **Backend**: Java and Spring Boot for building a robust, scalable backend.
- **Frontend**: Thymeleaf for server-side templating, combined with Bootstrap for responsive design.
- **Styling**: Tailwind CSS for a modern, clean, and responsive UI.
- **Database**: MySQL for managing and storing contact data.
- **Image Management**: Cloudinary integration for efficient image storage and retrieval.
- **Authentication**: Spring Security for secure user authentication, including OAuth2 for Google and GitHub.
- **Deployment**: Heroku for cloud deployment, ensuring the application is accessible from anywhere.

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ishanberry87/smart-contact-manager.git
   cd smart-contact-manager
   ```

2. **Set up the database:**
   - Create a MySQL database named `smart_contact_manager`.
   - Update the `application.properties` file with your database credentials.

3. **Install dependencies:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

## Usage

1. **Access the application:**
   - Open your browser and navigate to `http://localhost:8080`.
   - Sign up or log in using Google or GitHub.

2. **Manage contacts:**
   - Add, view, update, or delete contacts.
   - Upload and manage contact images.
   - Use the admin portal for advanced management.

## API Endpoints

| Method | Endpoint           | Description                     |
|--------|--------------------|---------------------------------|
| GET    | `/contacts`        | Get a list of all contacts      |
| POST   | `/contacts`        | Add a new contact               |
| PUT    | `/contacts/{id}`   | Update an existing contact      |
| DELETE | `/contacts/{id}`   | Delete a contact                |

## Challenges Faced
- **OAuth Redirect URI Mismatch**: Resolved issues related to OAuth redirect URIs in production, ensuring smooth and secure authentication.
- **Email Verification**: Implemented email verification to enhance user account security.

## Future Enhancements
- **Improved UI/UX**: Further enhance the user interface and experience with more intuitive designs.
- **Additional OAuth Providers**: Support for more OAuth providers like Facebook, LinkedIn, etc.
- **User Roles & Permissions**: Implement role-based access control for better management.

## About the Developer
This application was developed by **Ishan Berry**, a passionate Java backend developer with a keen interest in creating efficient and user-friendly applications. For more projects and tutorials, check out my portfolio and YouTube channel:

- **Portfolio**: [Ishan's Portfolio](https://ishandevportfolio.vercel.app)
- **YouTube Channel**: [Coding with Ishan](https://www.youtube.com/channel/UCxyz)

## Contact
**Ishan Berry**  
[LinkedIn](https://www.linkedin.com/in/ishan-berry) | [GitHub](https://github.com/ishanberry87/smart-contact-manager) | Email: ishanberry87@gmail.com

