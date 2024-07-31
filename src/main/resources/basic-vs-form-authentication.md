# Basic Authentication vs. Form Login

## Basic Authentication

**Overview:**

- Basic Authentication is a simple authentication scheme built into the HTTP protocol.
- It involves sending the username and password in the `Authorization` header of the HTTP request.

**How It Works:**

- The client sends a request to the server with an `Authorization` header that includes the word "Basic" followed by a
  base64-encoded string of the username and password.
- Example Header: `Authorization: Basic dXNlcjpwYXNzd29yZA==` (where `dXNlcjpwYXNzd29yZA==` is the base64-encoded string
  of `user:password`).

**Security Considerations:**

- **Transport Security:** Basic Authentication credentials are only as secure as the transport layer. Without HTTPS, the
  credentials are sent in plain text (base64 encoding is not encryption), which can be intercepted.
- **No Session Management:** Basic Authentication does not manage sessions. Each request requires authentication
  information.

**Use Cases:**

- Basic Authentication is often used for quick testing or in cases where the application is not exposed to public
  networks.
- It's also used in some APIs and services where simplicity is preferred and security is handled separately (e.g., via
  HTTPS).

## Form Login

**Overview:**

- Form-based authentication involves presenting a login form to the user where they enter their username and password.
- After submission, the credentials are validated by the server, and if successful, the user is granted access.

**How It Works:**

- The user accesses a login page served by the application, fills out a form with their credentials, and submits it.
- The server processes the form submission, checks the credentials, and if valid, creates a session for the user.

**Security Considerations:**

- **Session Management:** Form-based authentication typically uses sessions to manage authenticated users. After logging
  in, the user receives a session cookie, which is used for subsequent requests.
- **Flexibility:** It allows for custom login pages, error messages, and user experiences. You can also implement
  additional security features like CAPTCHA, multi-factor authentication, etc.
- **Transport Security:** As with Basic Authentication, HTTPS is crucial to protect credentials during transmission.

**Use Cases:**

- Form-based authentication is common in web applications where a user interface is provided, and a more user-friendly
  login experience is needed.
- It’s used in applications where session management, custom login pages, and user-specific data are important.

## Key Differences

| Aspect                    | Basic Authentication                               | Form Login                                                     |
|---------------------------|----------------------------------------------------|----------------------------------------------------------------|
| **Authentication Method** | Sends credentials in HTTP headers (Base64 encoded) | Uses a login form where credentials are sent via HTTP POST     |
| **Session Management**    | No built-in session management                     | Typically uses HTTP sessions for managing authenticated users  |
| **User Experience**       | Simple and less user-friendly                      | Customizable with user-friendly login forms                    |
| **Security**              | Depends on HTTPS for secure transmission           | Secure with HTTPS and can have additional security features    |
| **Use Case**              | Suitable for APIs or simple services               | Suitable for web applications with interactive user interfaces |

## Session Creation Policy

**`SessionCreationPolicy.STATELESS`:**

- **Overview:** When using `SessionCreationPolicy.STATELESS`, the server does not create or maintain HTTP sessions.
- **Impact:** This setting is particularly important for stateless APIs where each request is independent, and no
  session state is stored on the server.
- **Use Case:** Ideal for RESTful APIs and applications where session management is handled via tokens (like JWTs)
  instead of server-side sessions.

**Code Example:**

```java
http.sessionManagement(session -> 
    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
);